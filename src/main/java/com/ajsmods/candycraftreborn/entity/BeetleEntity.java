package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class BeetleEntity extends Monster {
    private static final EntityDataAccessor<Boolean> IS_ANGRY =
            SynchedEntityData.defineId(BeetleEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_CHILD =
            SynchedEntityData.defineId(BeetleEntity.class, EntityDataSerializers.BOOLEAN);

    private static final UUID ENRAGE_SPEED_UUID = UUID.fromString("b3f53a1e-7c4d-4e2f-9a1b-5c8d6f7e0a12");

    public BeetleEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_ANGRY, false);
        this.entityData.define(IS_CHILD, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.3, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 0.3));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 1.2)
                .add(Attributes.ATTACK_DAMAGE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0);
    }

    public boolean isAngry() { return this.entityData.get(IS_ANGRY); }
    public void setAngry(boolean angry) {
        this.entityData.set(IS_ANGRY, angry);
        if (angry) {
            // Use transient modifier for speed buff
            var speedAttr = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttr != null && speedAttr.getModifier(ENRAGE_SPEED_UUID) == null) {
                speedAttr.addTransientModifier(new AttributeModifier(
                        ENRAGE_SPEED_UUID, "Enrage speed boost", 0.5, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(isBeetleChild() ? 7.0 : 15.0);
        }
    }

    public boolean isBeetleChild() { return this.entityData.get(IS_CHILD); }
    public void setBeetleChild(boolean child) { this.entityData.set(IS_CHILD, child); }

    @Override
    public void die(DamageSource source) {
        if (isBeetleChild() && source.getEntity() instanceof Player) {
            AABB area = this.getBoundingBox().inflate(32.0);
            List<BeetleEntity> nearby = this.level().getEntitiesOfClass(BeetleEntity.class, area);
            for (BeetleEntity beetle : nearby) {
                if (!beetle.isBeetleChild()) {
                    beetle.setAngry(true);
                }
            }
        }
        super.die(source);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        if (this.random.nextInt(80) == 0) {
            this.spawnAtLocation(new ItemStack(ModBlocks.BEETLE_EGG_BLOCK.get()));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Child", isBeetleChild());
        tag.putBoolean("Angry", isAngry());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setBeetleChild(tag.getBoolean("Child"));
        setAngry(tag.getBoolean("Angry"));
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        if (this.random.nextFloat() < 0.1F) {
            BeetleEntity child = com.ajsmods.candycraftreborn.registry.ModEntities.BEETLE.get().create(this.level());
            if (child != null) {
                child.setBeetleChild(true);
                child.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                child.startRiding(this);
                level.addFreshEntityWithPassengers(child);
            }
        }
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }
}
