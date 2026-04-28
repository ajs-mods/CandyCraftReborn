package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nullable;

public class GingerBreadManEntity extends AbstractVillager {

    private static final EntityDataAccessor<Integer> PROFESSION =
            SynchedEntityData.defineId(GingerBreadManEntity.class, EntityDataSerializers.INT);

    public GingerBreadManEntity(EntityType<? extends AbstractVillager> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PROFESSION, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 0.25));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.33);
    }

    public int getProfession() { return this.entityData.get(PROFESSION); }
    public void setProfession(int prof) { this.entityData.set(PROFESSION, prof % 4); }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                         MobSpawnType reason, @Nullable SpawnGroupData data, @Nullable CompoundTag tag) {
        setProfession(this.random.nextInt(4));
        return super.finalizeSpawn(level, difficulty, reason, data, tag);
    }

    @Override
    protected void updateTrades() {
        MerchantOffers offers = this.getOffers();
        switch (getProfession()) {
            case 0 -> addBlacksmithTrades(offers);
            case 1 -> addFarmerTrades(offers);
            case 2 -> addCitizenTrades(offers);
            case 3 -> addElderTrades(offers);
        }
    }

    private void addBlacksmithTrades(MerchantOffers offers) {
        // Sells weapons/armor for chocolate_coin
        ItemStack coin = new ItemStack(ModItems.CHOCOLATE_COIN.get());
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 8),
                new ItemStack(ModItems.LICORICE_SWORD.get()), 4, 2, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 12),
                new ItemStack(ModItems.HONEY_SWORD.get()), 4, 2, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 6),
                new ItemStack(ModItems.LICORICE_HELMET.get()), 4, 2, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 10),
                new ItemStack(ModItems.LICORICE_PLATE.get()), 4, 2, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 5),
                new ItemStack(ModItems.MARSHMALLOW_PICKAXE.get()), 4, 2, 0.05F));
    }

    private void addFarmerTrades(MerchantOffers offers) {
        // Sells food/seeds for chocolate_coin
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 2),
                new ItemStack(ModItems.CANDY_CANE.get(), 4), 8, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 3),
                new ItemStack(ModItems.WAFFLE.get(), 2), 8, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 1),
                new ItemStack(ModItems.LOLLIPOP_SEEDS.get(), 4), 8, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 2),
                new ItemStack(ModItems.DRAGIBUS.get(), 4), 8, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 3),
                new ItemStack(ModItems.CRANBERRY_FISH_COOKED.get(), 3), 8, 1, 0.05F));
    }

    private void addCitizenTrades(MerchantOffers offers) {
        // Buys player items for chocolate_coin
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.SUGAR_CRYSTAL.get(), 4),
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 1), 12, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.HONEY_SHARD.get(), 6),
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 2), 12, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.CARAMEL_SHARD.get(), 8),
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 1), 12, 1, 0.05F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.NOUGAT_POWDER.get(), 4),
                new ItemStack(ModItems.CHOCOLATE_COIN.get(), 2), 12, 1, 0.05F));
    }

    private void addElderTrades(MerchantOffers offers) {
        // Fixed trades: PEZ → special items
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.PEZ.get(), 5),
                new ItemStack(ModItems.JELLY_KEY.get()), 2, 5, 0.0F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.PEZ.get(), 10),
                new ItemStack(ModItems.SKY_EMBLEM.get()), 2, 10, 0.0F));
        offers.add(new MerchantOffer(
                new ItemStack(ModItems.PEZ.get(), 20),
                new ItemStack(ModItems.RECORD_3.get()), 1, 15, 0.0F));
    }

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
    }

    @Override
    public int getExperienceReward() {
        return 0;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Profession", getProfession());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setProfession(tag.getInt("Profession"));
    }
}
