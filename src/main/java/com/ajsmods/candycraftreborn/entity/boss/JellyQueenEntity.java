package com.ajsmods.candycraftreborn.entity.boss;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class JellyQueenEntity extends Monster {
    private static final EntityDataAccessor<Boolean> AWAKE =
            SynchedEntityData.defineId(JellyQueenEntity.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    private int jumpTimer = 0;

    public JellyQueenEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 50;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AWAKE, false);
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
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 10.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.8);
    }

    public boolean isAwake() { return this.entityData.get(AWAKE); }
    public void setAwake(boolean awake) { this.entityData.set(AWAKE, awake); }

    private int getPhase() {
        float hp = this.getHealth();
        if (hp > 150) return 1;
        if (hp > 75) return 2;
        return 3;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isIndirect()) return false; // Immune to projectiles
        if (!isAwake()) setAwake(true);

        boolean result = super.hurt(source, amount);
        // Knockback attacker
        if (result && source.getEntity() instanceof LivingEntity attacker) {
            Vec3 push = attacker.position().subtract(this.position()).normalize().scale(1.5);
            attacker.push(push.x, 0.4, push.z);
            attacker.hurtMarked = true;
        }
        return result;
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        if (!isAwake()) {
            // Dormant: heal 5/tick, no movement
            this.heal(5.0F);
            this.getNavigation().stop();
            return;
        }

        int phase = getPhase();

        // Adjust speed based on phase
        var speedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttr != null) {
            switch (phase) {
                case 1 -> speedAttr.setBaseValue(0.2);
                case 2 -> speedAttr.setBaseValue(0.4);
                case 3 -> speedAttr.setBaseValue(0.3);
            }
        }

        // Phase 2: Jump towards player every 40 ticks
        if (phase == 2) {
            jumpTimer++;
            if (jumpTimer >= 40 && this.getTarget() != null && this.onGround()) {
                jumpTimer = 0;
                Vec3 dir = this.getTarget().position().subtract(this.position()).normalize();
                this.setDeltaMovement(dir.x * 0.8, 0.6, dir.z * 0.8);
                this.hasImpulse = true;
            }
        }

        // Phase 3: Explosion on landing
        if (phase == 3 && this.onGround() && this.getDeltaMovement().y == 0 && jumpTimer > 0) {
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 2.0F,
                    Level.ExplosionInteraction.NONE);
            jumpTimer = 0;
        }
        if (phase == 3) jumpTimer++;
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
