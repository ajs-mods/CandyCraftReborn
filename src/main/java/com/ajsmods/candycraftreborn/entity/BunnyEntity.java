package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class BunnyEntity extends Animal {
    private static final EntityDataAccessor<Integer> RED =
            SynchedEntityData.defineId(BunnyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> GREEN =
            SynchedEntityData.defineId(BunnyEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BLUE =
            SynchedEntityData.defineId(BunnyEntity.class, EntityDataSerializers.INT);

    private int jumpDelay = 0;
    private float lastRotationYaw;

    public BunnyEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RED, 128);
        this.entityData.define(GREEN, 128);
        this.entityData.define(BLUE, 128);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ModItems.LICORICE.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.isInWater()) {
            if (this.onGround()) {
                if (this.jumpDelay > 0) {
                    this.jumpDelay--;
                    this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                } else {
                    this.jumpFromGround();
                    this.jumpDelay = 10;
                    this.lastRotationYaw = this.getYRot();
                }
            } else {
                this.setYRot(this.lastRotationYaw);
            }
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    public int getRed() { return this.entityData.get(RED); }
    public int getGreen() { return this.entityData.get(GREEN); }
    public int getBlue() { return this.entityData.get(BLUE); }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        this.entityData.set(RED, 20 + this.random.nextInt(230));
        this.entityData.set(GREEN, 20 + this.random.nextInt(230));
        this.entityData.set(BLUE, 20 + this.random.nextInt(230));
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.LICORICE.get());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("red", getRed());
        tag.putInt("green", getGreen());
        tag.putInt("blue", getBlue());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(RED, tag.getInt("red"));
        this.entityData.set(GREEN, tag.getInt("green"));
        this.entityData.set(BLUE, tag.getInt("blue"));
    }

    @Override
    public BunnyEntity getBreedOffspring(ServerLevel level, AgeableMob other) {
        return com.ajsmods.candycraftreborn.registry.ModEntities.BUNNY.get().create(level);
    }
}
