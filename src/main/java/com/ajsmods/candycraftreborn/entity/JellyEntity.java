package com.ajsmods.candycraftreborn.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class JellyEntity extends Mob {
    private static final EntityDataAccessor<Integer> SIZE =
            SynchedEntityData.defineId(JellyEntity.class, EntityDataSerializers.INT);

    public float squishAmount;
    public float squishFactor;
    public float prevSquishFactor;
    public int slimeJumpDelay;

    public JellyEntity(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.moveControl = new JellyMoveControl(this);
        this.setPersistenceRequired();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SIZE, 1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new JellyFloatGoal(this));
        this.goalSelector.addGoal(2, new JellyAttackGoal(this));
        this.goalSelector.addGoal(3, new JellyRandomDirectionGoal(this));
        this.goalSelector.addGoal(5, new JellyHopGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 48.0);
    }

    public int getJellySize() { return this.entityData.get(SIZE); }

    public void setJellySize(int size) {
        this.entityData.set(SIZE, size);
        this.reapplyPosition();
        this.refreshDimensions();
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(size * size * 4.0);
        this.setHealth(this.getMaxHealth());
        this.xpReward = size;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        int size = this.getJellySize();
        float w = 0.51F * size + 0.1F;
        float h = 0.51F * size;
        return EntityDimensions.scalable(w, h);
    }

    @Override
    public void tick() {
        super.tick();
        this.prevSquishFactor = this.squishFactor;
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
        this.squishAmount *= 0.6F;
        if (this.onGround() && !this.wasOnGround) {
            int size = this.getJellySize();
            for (int i = 0; i < size * 8; i++) {
                double ox = this.random.nextFloat() * 2 - 1;
                double oz = this.random.nextFloat() * 2 - 1;
                this.level().addParticle(ParticleTypes.CRIT, this.getX() + ox * size * 0.5,
                        this.getY(), this.getZ() + oz * size * 0.5, 0, 0, 0);
            }
            if (size > 2) {
                this.playSound(SoundEvents.SLIME_SQUISH, this.getSoundVolume(),
                        ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }
            this.squishAmount = -0.5F;
        }
        this.wasOnGround = this.onGround();
    }

    private boolean wasOnGround;

    protected int getJumpDelay() { return this.random.nextInt(20) + 10; }

    public boolean isAwake() { return true; }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Size", this.getJellySize() - 1);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setJellySize(tag.getInt("Size") + 1);
    }

    @Override
    public boolean removeWhenFarAway(double distance) { return false; }

    // ── Inner AI Goals ───────────────────────────────────────────────────

    static class JellyMoveControl extends MoveControl {
        private float yRot;
        private int jumpDelay;
        private final JellyEntity jelly;
        private boolean isAggressive;

        JellyMoveControl(JellyEntity jelly) {
            super(jelly);
            this.jelly = jelly;
            this.yRot = 180.0F * jelly.getYRot() / (float) Math.PI;
        }

        public void setDirection(float yRot, boolean aggressive) {
            this.yRot = yRot;
            this.isAggressive = aggressive;
        }

        public void setSpeed(double speed) {
            this.speedModifier = speed;
            this.operation = Operation.MOVE_TO;
        }

        @Override
        public void tick() {
            this.mob.setYRot(rotlerp(this.mob.getYRot(), this.yRot, 90.0F));
            this.mob.yHeadRot = this.mob.getYRot();
            this.mob.yBodyRot = this.mob.getYRot();

            if (this.operation != Operation.MOVE_TO) {
                this.mob.setZza(0.0F);
                return;
            }
            this.operation = Operation.WAIT;
            if (this.mob.onGround()) {
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
                if (this.jumpDelay-- <= 0) {
                    this.jumpDelay = jelly.getJumpDelay();
                    if (this.isAggressive) this.jumpDelay /= 3;
                    jelly.getJumpControl().jump();
                    jelly.playSound(SoundEvents.SLIME_JUMP, jelly.getSoundVolume(), 1.0F);
                }
                else { this.mob.setZza(0.0F); }
            } else {
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }
    }

    static class JellyFloatGoal extends Goal {
        private final JellyEntity jelly;
        JellyFloatGoal(JellyEntity jelly) { this.jelly = jelly; this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE)); }
        @Override public boolean canUse() { return jelly.isInWater() || jelly.isInLava(); }
        @Override public void tick() { ((JellyMoveControl)jelly.getMoveControl()).setSpeed(1.2); jelly.getJumpControl().jump(); }
    }

    static class JellyAttackGoal extends Goal {
        private final JellyEntity jelly;
        private int growTired;
        JellyAttackGoal(JellyEntity jelly) { this.jelly = jelly; this.setFlags(EnumSet.of(Flag.LOOK)); }
        @Override public boolean canUse() { return jelly.getTarget() instanceof Player; }
        @Override public void start() { this.growTired = 300; }
        @Override public boolean canContinueToUse() { return --growTired > 0 && jelly.getTarget() != null && jelly.getTarget().isAlive(); }
        @Override public void tick() {
            jelly.lookAt(jelly.getTarget(), 10.0F, 10.0F);
            ((JellyMoveControl)jelly.getMoveControl()).setDirection(jelly.getYRot(), true);
        }
    }

    static class JellyRandomDirectionGoal extends Goal {
        private final JellyEntity jelly;
        private float chosenDeg;
        private int nextRandomize;
        JellyRandomDirectionGoal(JellyEntity jelly) { this.jelly = jelly; this.setFlags(EnumSet.of(Flag.LOOK)); }
        @Override public boolean canUse() { return jelly.getTarget() == null && (jelly.onGround() || jelly.isInWater() || jelly.isInLava()); }
        @Override public void tick() {
            if (--nextRandomize <= 0) {
                nextRandomize = 40 + jelly.getRandom().nextInt(60);
                chosenDeg = jelly.getRandom().nextFloat() * 360.0F;
            }
            ((JellyMoveControl)jelly.getMoveControl()).setDirection(chosenDeg, false);
        }
    }

    static class JellyHopGoal extends Goal {
        private final JellyEntity jelly;
        JellyHopGoal(JellyEntity jelly) { this.jelly = jelly; this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE)); }
        @Override public boolean canUse() { return true; }
        @Override public void tick() { ((JellyMoveControl)jelly.getMoveControl()).setSpeed(1.0); }
    }
}
