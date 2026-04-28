package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class DragonEntity extends Animal implements PowerMount, Lockable {

    private static final EntityDataAccessor<Integer> POWER =
            SynchedEntityData.defineId(DragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FALLING =
            SynchedEntityData.defineId(DragonEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SHOOT =
            SynchedEntityData.defineId(DragonEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LOCKED =
            SynchedEntityData.defineId(DragonEntity.class, EntityDataSerializers.BOOLEAN);

    public DragonEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POWER, 0);
        this.entityData.define(FALLING, false);
        this.entityData.define(SHOOT, 0);
        this.entityData.define(LOCKED, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    // ── PowerMount ───────────────────────────────────────────────────────────

    @Override
    public int getMaxPower() { return 1500; }

    @Override
    public int getPower() { return this.entityData.get(POWER); }

    @Override
    public void setPower(int power) { this.entityData.set(POWER, Math.max(0, Math.min(power, getMaxPower()))); }

    @Override
    public void unleashPower() {
        if (getPower() >= 300) {
            setPower(getPower() - 300);
            this.entityData.set(SHOOT, 10 + this.random.nextInt(9));
        }
    }

    public boolean isFalling() { return this.entityData.get(FALLING); }
    public int getShootTimer() { return this.entityData.get(SHOOT); }

    // ── Lockable ─────────────────────────────────────────────────────────────

    @Override
    public boolean isLocked() { return this.entityData.get(LOCKED); }

    @Override
    public void setLocked(boolean locked) { this.entityData.set(LOCKED, locked); }

    // ── Interaction ──────────────────────────────────────────────────────────

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.isVehicle()) {
            if (!this.level().isClientSide) {
                player.startRiding(this);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    // ── Riding ───────────────────────────────────────────────────────────────

    @Nullable
    @Override
    public LivingEntity getControllingPassenger() {
        if (this.getFirstPassenger() instanceof LivingEntity living) {
            return living;
        }
        return null;
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() && this.isVehicle();
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.isAlive() && this.isVehicle() && getControllingPassenger() instanceof Player rider) {
            this.setYRot(rider.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(rider.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.yBodyRot;

            float forward = rider.zza;
            // No strafing for dragon

            double yawRad = Math.toRadians(this.getYRot());
            double motionX = -Math.sin(yawRad) * 4.0 * forward * 0.05;
            double motionZ = Math.cos(yawRad) * 4.0 * forward * 0.05;

            // Pitch controls vertical: negative pitch = looking up = go up
            double motionY = 0;
            if (!isFalling()) {
                motionY = -rider.getXRot() / 1000.0;
            }

            Vec3 current = this.getDeltaMovement();
            this.setDeltaMovement(current.x + motionX, motionY, current.z + motionZ);

            // Air drag
            if (!this.onGround()) {
                Vec3 m = this.getDeltaMovement();
                this.setDeltaMovement(m.x / 1.25, m.y, m.z / 1.25);
            }

            super.travel(Vec3.ZERO);
        } else {
            super.travel(travelVector);
        }
    }

    // ── Tick ─────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            // Power recharge/drain
            if (this.onGround()) {
                if (getPower() < getMaxPower()) setPower(getPower() + 1);
                this.entityData.set(FALLING, false);
            } else if (this.isVehicle()) {
                setPower(getPower() - 2);
                if (getPower() <= 0) {
                    this.entityData.set(FALLING, true);
                }
            }

            // Shooting
            int shoot = this.entityData.get(SHOOT);
            if (shoot > 0) {
                if (shoot % 2 == 0) {
                    // Fire gummy ball in facing direction
                    GummyBallEntity ball = new GummyBallEntity(
                            ModEntities.GUMMY_BALL.get(), this, this.level());
                    double yawRad = Math.toRadians(this.getYRot());
                    double pitchRad = Math.toRadians(this.getXRot());
                    ball.shoot(-Math.sin(yawRad) * Math.cos(pitchRad),
                            -Math.sin(pitchRad),
                            Math.cos(yawRad) * Math.cos(pitchRad),
                            1.5F, 1.0F);
                    this.level().addFreshEntity(ball);
                }
                this.entityData.set(SHOOT, shoot - 1);
            }
        }
    }

    // ── Save / Load ──────────────────────────────────────────────────────────

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Power", getPower());
        tag.putBoolean("Falling", isFalling());
        tag.putBoolean("Locked", isLocked());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setPower(tag.getInt("Power"));
        this.entityData.set(FALLING, tag.getBoolean("Falling"));
        setLocked(tag.getBoolean("Locked"));
    }
}
