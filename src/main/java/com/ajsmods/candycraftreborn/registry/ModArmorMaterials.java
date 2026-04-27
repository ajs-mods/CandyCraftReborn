package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
    LICORICE("candycraftreborn:licorice", 18, Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
        m.put(ArmorItem.Type.BOOTS, 1);
        m.put(ArmorItem.Type.LEGGINGS, 5);
        m.put(ArmorItem.Type.CHESTPLATE, 4);
        m.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(ModItems.LICORICE.get())),

    HONEY("candycraftreborn:honey", 22, Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
        m.put(ArmorItem.Type.BOOTS, 2);
        m.put(ArmorItem.Type.LEGGINGS, 7);
        m.put(ArmorItem.Type.CHESTPLATE, 6);
        m.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, 0.0F, () -> Ingredient.of(ModItems.HONEY_SHARD.get())),

    PEZ("candycraftreborn:pez", 24, Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
        m.put(ArmorItem.Type.BOOTS, 4);
        m.put(ArmorItem.Type.LEGGINGS, 9);
        m.put(ArmorItem.Type.CHESTPLATE, 7);
        m.put(ArmorItem.Type.HELMET, 4);
    }), 6, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, () -> Ingredient.of(ModItems.PEZ.get())),

    JELLY_CROWN("candycraftreborn:jelly_crown", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
        m.put(ArmorItem.Type.BOOTS, 0);
        m.put(ArmorItem.Type.LEGGINGS, 0);
        m.put(ArmorItem.Type.CHESTPLATE, 0);
        m.put(ArmorItem.Type.HELMET, 0);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY),

    JELLY_BOOTS("candycraftreborn:jelly_boots", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
        m.put(ArmorItem.Type.BOOTS, 0);
        m.put(ArmorItem.Type.LEGGINGS, 0);
        m.put(ArmorItem.Type.CHESTPLATE, 0);
        m.put(ArmorItem.Type.HELMET, 0);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY),

    WATER_MASK("candycraftreborn:water_mask", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), m -> {
        m.put(ArmorItem.Type.BOOTS, 0);
        m.put(ArmorItem.Type.LEGGINGS, 0);
        m.put(ArmorItem.Type.CHESTPLATE, 0);
        m.put(ArmorItem.Type.HELMET, 0);
    }), 0, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;
    private static final int[] BASE_DURABILITY = {13, 15, 16, 11};

    ModArmorMaterials(String name, int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionMap,
                      int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance,
                      Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionFunctionForType = protectionMap;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override public int getDurabilityForType(ArmorItem.Type type) {
        return BASE_DURABILITY[type.ordinal()] * this.durabilityMultiplier;
    }
    @Override public int getDefenseForType(ArmorItem.Type type) {
        return this.protectionFunctionForType.get(type);
    }
    @Override public int getEnchantmentValue() { return this.enchantmentValue; }
    @Override public SoundEvent getEquipSound() { return this.equipSound; }
    @Override public Ingredient getRepairIngredient() { return this.repairIngredient.get(); }
    @Override public String getName() { return this.name; }
    @Override public float getToughness() { return this.toughness; }
    @Override public float getKnockbackResistance() { return this.knockbackResistance; }
}
