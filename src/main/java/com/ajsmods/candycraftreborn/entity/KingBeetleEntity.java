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

public class KingBeetleEntity extends Animal implements PowerMount {

    private static final EntityDataAccessor<Integer> POWER =
            SynchedEntityData.defineId(KingBeetleEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> EXPLOSION_COUNT =
            SynchedEntityData.defineId(KingBeetleEntity.class, EntityDataSerializers.INT);

    public KingBeetleEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POWER, 0);
        this.entityData.define(EXPLOSION_COUNT, 0);
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
                .add(Attributes.MOVEMENT_SPEED, 0.3);
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

    // ── PowerMount ───────────────────────────────────────────────────────────

    @Override
    public int getMaxPower() { return 1200; }

    @Override
    public int getPower() { return this.entityData.get(POWER); }

    @Override
    public void setPower(int power) { this.entityData.set(POWER, Math.max(0, Math.min(power, getMaxPower()))); }

    @Override
    public void unleashPower() {
        if (getPower() >= getMaxPower()) {
            setPower(0);
            this.entityData.set(EXPLOSION_COUNT, 8 + this.random.nextInt(9));
        }
    }

    public int getExplosionCount() { return this.entityData.get(EXPLOSION_COUNT); }

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
    protected void jumpFromGround() {
        super.jumpFromGround();
        // Enhanced jump
        Vec3 m = this.getDeltaMovement();
        this.setDeltaMovement(m.x, m.y * 4.0, m.z);
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

            float strafe = rider.xxa * 0.5F;
            float forward = rider.zza;

            if (forward <= 0.0F) {
                forward *= 0.25F;
            }

            // Air drag
            if (!this.onGround()) {
                Vec3 m = this.getDeltaMovement();
                this.setDeltaMovement(m.x / 1.5, m.y, m.z / 1.5);
            }

            super.travel(new Vec3(strafe, travelVector.y, forward));
        } else {
            super.travel(travelVector);
        }
    }

    // ── Tick ─────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            // Power recharge
            if (getPower() < getMaxPower()) {
                setPower(getPower() + 1);
            }

            // Explosions
            int count = this.entityData.get(EXPLOSION_COUNT);
            if (count > 0 && this.tickCount % 5 == 0) {
                double ox = this.getX() + (this.random.nextDouble() - 0.5) * 6.0;
                double oy = this.getY();
                double oz = this.getZ() + (this.random.nextDouble() - 0.5) * 6.0;
                this.level().explode(this, ox, oy, oz, 2.0F, Level.ExplosionInteraction.MOB);
                this.entityData.set(EXPLOSION_COUNT, count - 1);
            }
        }
    }

    // ── Save / Load ──────────────────────────────────────────────────────────

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Power", getPower());
        tag.putInt("ExplosionCount", getExplosionCount());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setPower(tag.getInt("Power"));
        this.entityData.set(EXPLOSION_COUNT, tag.getInt("ExplosionCount"));
    }
}
