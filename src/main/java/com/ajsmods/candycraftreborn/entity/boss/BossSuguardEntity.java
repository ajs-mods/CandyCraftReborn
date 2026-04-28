package com.ajsmods.candycraftreborn.entity.boss;

import com.ajsmods.candycraftreborn.entity.CandyArrowEntity;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BossSuguardEntity extends Monster implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> AWAKE =
            SynchedEntityData.defineId(BossSuguardEntity.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.YELLOW, BossEvent.BossBarOverlay.PROGRESS);

    private int attackCycleTick = 0;

    public BossSuguardEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 60;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AWAKE, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 20, 20.0F));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 400.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 6.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
    }

    public boolean isAwake() { return this.entityData.get(AWAKE); }
    public void setAwake(boolean awake) { this.entityData.set(AWAKE, awake); }

    /** Gets current attack cycle: 1, 2, or 3 (300 ticks each) */
    private int getCycle() {
        int cyclePos = attackCycleTick % 900;
        if (cyclePos < 300) return 1;
        if (cyclePos < 600) return 2;
        return 3;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isIndirect()) return false; // Immune to projectiles, also won't wake from ranged
        if (!isAwake()) setAwake(true);
        return super.hurt(source, amount);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        if (!isAwake()) return;

        int cycle = getCycle();
        double dist = this.distanceTo(target);

        // Close range burst
        if (dist < 5.0) {
            for (int i = 0; i < 5; i++) {
                shootArrow(target, cycle == 3);
            }
            return;
        }

        switch (cycle) {
            case 1 -> shootArrow(target, false);
            case 2 -> {
                for (int i = 0; i < 4; i++) {
                    shootArrowSpread(target, i, false);
                }
            }
            case 3 -> shootArrow(target, true);
        }
    }

    private void shootArrow(LivingEntity target, boolean fire) {
        CandyArrowEntity arrow = new CandyArrowEntity(ModEntities.CANDY_ARROW.get(), this, this.level());
        Vec3 dir = target.getEyePosition().subtract(this.getEyePosition());
        arrow.shoot(dir.x, dir.y, dir.z, 1.6F, 2.0F);
        if (fire) arrow.setSecondsOnFire(5);
        arrow.setBaseDamage(6.0);
        this.level().addFreshEntity(arrow);
    }

    private void shootArrowSpread(LivingEntity target, int index, boolean fire) {
        CandyArrowEntity arrow = new CandyArrowEntity(ModEntities.CANDY_ARROW.get(), this, this.level());
        Vec3 dir = target.getEyePosition().subtract(this.getEyePosition());
        double spread = (index - 1.5) * 0.15;
        arrow.shoot(dir.x + spread, dir.y, dir.z + spread, 1.6F, 4.0F);
        if (fire) arrow.setSecondsOnFire(5);
        arrow.setBaseDamage(5.0);
        this.level().addFreshEntity(arrow);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        if (!isAwake()) {
            this.heal(2.0F);
            this.getNavigation().stop();
            return;
        }
        attackCycleTick++;
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
        tag.putInt("AttackCycle", attackCycleTick);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAwake(tag.getBoolean("Awake"));
        attackCycleTick = tag.getInt("AttackCycle");
        if (this.hasCustomName()) bossEvent.setName(this.getDisplayName());
    }
}
