package com.ajsmods.candycraftreborn.entity.boss;

import com.ajsmods.candycraftreborn.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class KingSlimeEntity extends Monster {
    private static final EntityDataAccessor<Boolean> AWAKE =
            SynchedEntityData.defineId(KingSlimeEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> RENDER_SCALE =
            SynchedEntityData.defineId(KingSlimeEntity.class, EntityDataSerializers.FLOAT);

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS);

    private int jumpTimer = 0;
    private boolean wasInAir = false;

    public KingSlimeEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 100;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AWAKE, false);
        this.entityData.define(RENDER_SCALE, 1.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 800.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 15.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public boolean isAwake() { return this.entityData.get(AWAKE); }
    public void setAwake(boolean awake) { this.entityData.set(AWAKE, awake); }

    public float getRenderScale() { return this.entityData.get(RENDER_SCALE); }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isIndirect()) return false;
        if (!isAwake()) setAwake(true);
        boolean result = super.hurt(source, amount);
        // Knockback attacker
        if (result && source.getEntity() instanceof LivingEntity attacker) {
            Vec3 push = attacker.position().subtract(this.position()).normalize().scale(2.0);
            attacker.push(push.x, 0.5, push.z);
            attacker.hurtMarked = true;
        }
        return result;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        // Visual scale shrinks with HP
        float scale = 0.5F + 0.5F * (this.getHealth() / this.getMaxHealth());
        this.entityData.set(RENDER_SCALE, scale);

        if (!isAwake()) {
            this.heal(5.0F);
            this.getNavigation().stop();
            return;
        }

        // Jump attack
        jumpTimer++;
        if (jumpTimer >= 30 && this.onGround() && this.getTarget() != null) {
            jumpTimer = 0;
            Vec3 dir = this.getTarget().position().subtract(this.position()).normalize();
            this.setDeltaMovement(dir.x * 0.7, 0.6, dir.z * 0.7);
            this.hasImpulse = true;
            wasInAir = true;
        }

        // On landing effects
        if (wasInAir && this.onGround()) {
            wasInAir = false;
            float roll = this.random.nextFloat();

            // 10% slowness to nearby players
            if (roll < 0.10F) {
                AABB area = this.getBoundingBox().inflate(8.0);
                List<Player> players = this.level().getEntitiesOfClass(Player.class, area);
                for (Player p : players) {
                    p.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1));
                }
            }
            // 20% explosion
            else if (roll < 0.30F) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F,
                        Level.ExplosionInteraction.NONE);
            }
            // 33% spawn yellow jelly minion
            else if (roll < 0.63F) {
                var minion = ModEntities.YELLOW_JELLY.get().create(this.level());
                if (minion != null) {
                    double ox = (this.random.nextDouble() - 0.5) * 4;
                    double oz = (this.random.nextDouble() - 0.5) * 4;
                    minion.moveTo(this.getX() + ox, this.getY(), this.getZ() + oz, 0, 0);
                    this.level().addFreshEntity(minion);
                }
            }
        }
        if (!this.onGround()) wasInAir = true;
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public boolean removeWhenFarAway(double distance) { return false; }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Awake", isAwake());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAwake(tag.getBoolean("Awake"));
        if (this.hasCustomName()) bossEvent.setName(this.getDisplayName());
    }
}
