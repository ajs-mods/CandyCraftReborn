package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class NessieEntity extends Animal implements PowerMount, Lockable {

    private static final EntityDataAccessor<Boolean> SADDLED =
            SynchedEntityData.defineId(NessieEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> POWER =
            SynchedEntityData.defineId(NessieEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(NessieEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LOCKED =
            SynchedEntityData.defineId(NessieEntity.class, EntityDataSerializers.BOOLEAN);

    public NessieEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLED, false);
        this.entityData.define(POWER, 0);
        this.entityData.define(VARIANT, 0);
        this.entityData.define(LOCKED, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.1, Ingredient.of(ModItems.CRANBERRY_FISH.get()), false));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.6));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.CRANBERRY_FISH.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.NESSIE.get().create(level);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    // ── Synced data accessors ────────────────────────────────────────────────

    public boolean isSaddled() { return this.entityData.get(SADDLED); }
    public void setSaddled(boolean saddled) { this.entityData.set(SADDLED, saddled); }

    public int getVariant() { return this.entityData.get(VARIANT); }
    public void setVariant(int variant) { this.entityData.set(VARIANT, variant % 8); }

    // ── PowerMount ───────────────────────────────────────────────────────────

    @Override
    public int getMaxPower() { return 1200; }

    @Override
    public int getPower() { return this.entityData.get(POWER); }

    @Override
    public void setPower(int power) { this.entityData.set(POWER, Math.min(power, getMaxPower())); }

    @Override
    public void unleashPower() {
        if (getPower() >= getMaxPower() && getControllingPassenger() instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0));
            setPower(0);
        }
    }

    // ── Lockable ─────────────────────────────────────────────────────────────

    @Override
    public boolean isLocked() { return this.entityData.get(LOCKED); }

    @Override
    public void setLocked(boolean locked) { this.entityData.set(LOCKED, locked); }

    // ── Interaction ──────────────────────────────────────────────────────────

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Saddle the nessie
        if (stack.is(Items.SADDLE) && !isSaddled()) {
            setSaddled(true);
            if (!player.getAbilities().instabuild) stack.shrink(1);
            this.playSound(SoundEvents.HORSE_SADDLE, 1.0F, 1.0F);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Remove saddle (sneak + click)
        if (player.isShiftKeyDown() && isSaddled() && !this.isVehicle()) {
            this.spawnAtLocation(Items.SADDLE);
            setSaddled(false);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Mount
        if (isSaddled() && !this.isVehicle()) {
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
        if (this.getFirstPassenger() instanceof LivingEntity living && isSaddled()) {
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

            float strafe = rider.xxa * 0.5F;
            float forward = rider.zza;

            if (this.isInWater()) {
                // Water movement: use rider's pitch for Y control
                double yMotion = -Math.sin(Math.toRadians(rider.getXRot())) * forward * 0.05;
                this.setDeltaMovement(
                        this.getDeltaMovement().x + (-Math.sin(Math.toRadians(this.getYRot())) * forward * 0.05),
                        yMotion,
                        this.getDeltaMovement().z + (Math.cos(Math.toRadians(this.getYRot())) * forward * 0.05)
                );
                super.travel(new Vec3(strafe, 0, forward));
            } else {
                super.travel(new Vec3(strafe, travelVector.y, forward));
            }
        } else {
            super.travel(travelVector);
        }
    }

    // ── Tick ─────────────────────────────────────────────────────────────────

    @Override
    public void tick() {
        super.tick();
        // Recharge power
        if (!this.level().isClientSide && getPower() < getMaxPower()) {
            setPower(getPower() + 1);
        }
    }

    // ── Save / Load ──────────────────────────────────────────────────────────

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Saddled", isSaddled());
        tag.putInt("Power", getPower());
        tag.putInt("Variant", getVariant());
        tag.putBoolean("Locked", isLocked());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSaddled(tag.getBoolean("Saddled"));
        setPower(tag.getInt("Power"));
        setVariant(tag.getInt("Variant"));
        setLocked(tag.getBoolean("Locked"));
    }
}
