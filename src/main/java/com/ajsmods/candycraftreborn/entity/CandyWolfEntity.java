package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CandyWolfEntity extends Wolf {

    private static final EntityDataAccessor<Integer> FUR_TIME =
            SynchedEntityData.defineId(CandyWolfEntity.class, EntityDataSerializers.INT);

    public CandyWolfEntity(EntityType<? extends Wolf> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FUR_TIME, 0);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public int getFurTime() { return this.entityData.get(FUR_TIME); }
    public void setFurTime(int time) { this.entityData.set(FUR_TIME, time); }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Taming with candy cane
        if (!this.isTame() && stack.is(ModItems.CANDY_CANE.get())) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            if (!this.level().isClientSide) {
                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.setHealth(20.0F);
                    this.setFurTime(12000 + this.random.nextInt(10001));
                    this.level().broadcastEntityEvent(this, (byte) 7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte) 6);
                }
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        // Milking with empty bucket
        if (this.isTame() && stack.is(Items.BUCKET) && getFurTime() <= 0) {
            if (!this.level().isClientSide) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                ItemStack caramelBucket = new ItemStack(ModItems.CARAMEL_BUCKET.get());
                if (!player.getInventory().add(caramelBucket)) {
                    player.drop(caramelBucket, false);
                }
                setFurTime(12000 + this.random.nextInt(5001));
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.CANDY_CANE.get());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isTame() && getFurTime() > 0 && !this.level().isClientSide) {
            setFurTime(getFurTime() - 1);
        }
        // Particles when ready to milk
        if (this.level().isClientSide && this.isTame() && getFurTime() <= 0) {
            this.level().addParticle(ParticleTypes.FLAME,
                    this.getRandomX(0.6), this.getRandomY(), this.getRandomZ(0.6),
                    0.0, 0.0, 0.0);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("FurTime", getFurTime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setFurTime(tag.getInt("FurTime"));
    }
}
