package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class MageSuguardEntity extends Monster {

    private int minionCooldown = 0;

    public MageSuguardEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.STICK));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 40.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.ARMOR, 2.0);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean doHurtTarget(net.minecraft.world.entity.Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.setSecondsOnFire(5);
        }
        return hit;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            minionCooldown++;
            if (minionCooldown >= 2400) {
                // Check if player within 16 blocks
                Player nearest = this.level().getNearestPlayer(this, 16.0);
                if (nearest != null) {
                    int count = 4 + this.random.nextInt(5); // 4-8
                    for (int i = 0; i < count; i++) {
                        SuguardEntity minion = ModEntities.SUGUARD.get().create(this.level());
                        if (minion != null) {
                            double angle = (2 * Math.PI * i) / count;
                            double spawnX = this.getX() + Math.cos(angle) * 3.0;
                            double spawnZ = this.getZ() + Math.sin(angle) * 3.0;
                            minion.moveTo(spawnX, this.getY(), spawnZ, this.getYRot(), 0.0F);
                            minion.setAngry(true);
                            this.level().addFreshEntity(minion);
                        }
                    }
                    minionCooldown = 0;
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("MinionCooldown", minionCooldown);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        minionCooldown = tag.getInt("MinionCooldown");
    }
}
