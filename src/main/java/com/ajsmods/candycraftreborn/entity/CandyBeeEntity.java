package com.ajsmods.candycraftreborn.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

import java.util.EnumSet;

public class CandyBeeEntity extends Monster {
    private boolean isAngry = true;
    public float wings = 0;
    private BlockPos flightTarget;
    private int attackCooldown = 0;

    public CandyBeeEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 10, true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        return nav;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BeeRandomFlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 15.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.FLYING_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.wings += 1.8F;
        if (!this.onGround() && this.getDeltaMovement().y < 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }
        if (this.getTarget() != null && this.distanceToSqr(this.getTarget()) < 4.0 && this.attackCooldown <= 0) {
            this.doHurtTarget(this.getTarget());
            if (this.random.nextInt(15) == 0) {
                this.getTarget().addEffect(new MobEffectInstance(MobEffects.POISON, 400));
            }
            this.attackCooldown = 20;
        }
        if (this.attackCooldown > 0) this.attackCooldown--;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier,
                                    net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    @Override
    protected boolean isAffectedByFluids() { return false; }

    @Override
    public boolean hurt(net.minecraft.world.damagesource.DamageSource source, float amount) {
        this.isAngry = true;
        return super.hurt(source, amount);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Angry", isAngry);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        isAngry = tag.getBoolean("Angry");
    }

    static class BeeRandomFlyGoal extends Goal {
        private final CandyBeeEntity bee;

        BeeRandomFlyGoal(CandyBeeEntity bee) {
            this.bee = bee;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        @Override
        public boolean canUse() { return true; }

        @Override
        public void tick() {
            if (bee.getTarget() != null) {
                bee.getMoveControl().setWantedPosition(
                        bee.getTarget().getX(), bee.getTarget().getEyeY(), bee.getTarget().getZ(), 1.0);
            } else if (bee.navigation.isDone()) {
                double x = bee.getX() + (bee.getRandom().nextDouble() - 0.5) * 14;
                double y = bee.getY() + (bee.getRandom().nextDouble() - 0.5) * 8;
                double z = bee.getZ() + (bee.getRandom().nextDouble() - 0.5) * 14;
                bee.getMoveControl().setWantedPosition(x, y, z, 0.6);
            }
        }
    }
}
