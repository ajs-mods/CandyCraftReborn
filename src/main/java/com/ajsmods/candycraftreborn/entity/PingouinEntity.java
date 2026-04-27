package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class PingouinEntity extends Animal {
    private static final EntityDataAccessor<Integer> COLOR =
            SynchedEntityData.defineId(PingouinEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SUPER =
            SynchedEntityData.defineId(PingouinEntity.class, EntityDataSerializers.BOOLEAN);

    public PingouinEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR, 0);
        this.entityData.define(IS_SUPER, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.38));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.5));
        this.goalSelector.addGoal(3, new TemptGoal(this, 0.5, Ingredient.of(ModItems.CRANBERRY_FISH.get()), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 0.28));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FOLLOW_RANGE, 16.0);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.onGround() && this.getDeltaMovement().y < 0) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.6, 1.0));
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier,
                                    net.minecraft.world.damagesource.DamageSource source) {
        return false;
    }

    public int getColor() { return this.entityData.get(COLOR) & 3; }
    public boolean isSuper() { return this.entityData.get(IS_SUPER); }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        this.entityData.set(COLOR, this.random.nextInt(3));
        this.entityData.set(IS_SUPER, this.random.nextInt(30) == 0);
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.CRANBERRY_FISH.get());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Color", getColor());
        tag.putBoolean("Super", isSuper());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(COLOR, tag.getInt("Color"));
        this.entityData.set(IS_SUPER, tag.getBoolean("Super"));
    }

    @Override
    public PingouinEntity getBreedOffspring(ServerLevel level, AgeableMob other) {
        return com.ajsmods.candycraftreborn.registry.ModEntities.PINGOUIN.get().create(level);
    }
}
