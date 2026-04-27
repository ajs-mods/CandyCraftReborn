package com.ajsmods.candycraftreborn.recipe;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.HashMap;
import java.util.Map;

public final class AlchemyRecipes {
    public static final Map<Item, MobEffect> RECIPE_MAP = new HashMap<>();

    public static void init() {
        addLazy();
    }

    private static void addLazy() {
        put(com.ajsmods.candycraftreborn.registry.ModItems.CANDY_CANE.get(),    MobEffects.REGENERATION);
        put(com.ajsmods.candycraftreborn.registry.ModItems.COTTON_CANDY.get(),  MobEffects.ABSORPTION);
        put(com.ajsmods.candycraftreborn.registry.ModItems.GUMMY.get(),          MobEffects.JUMP);
        put(com.ajsmods.candycraftreborn.registry.ModItems.HONEY_SHARD.get(),   MobEffects.DIG_SPEED);
        put(com.ajsmods.candycraftreborn.registry.ModItems.SUGAR_CRYSTAL.get(), MobEffects.INVISIBILITY);
        put(com.ajsmods.candycraftreborn.registry.ModItems.LICORICE.get(),      MobEffects.NIGHT_VISION);
        put(com.ajsmods.candycraftreborn.registry.ModItems.HONEYCOMB.get(),     MobEffects.MOVEMENT_SPEED);
        put(com.ajsmods.candycraftreborn.registry.ModItems.CHOCOLATE_COIN.get(),MobEffects.HARM);
        put(com.ajsmods.candycraftreborn.registry.ModItems.LOLLIPOP.get(),      MobEffects.HEAL);
        put(com.ajsmods.candycraftreborn.registry.ModItems.HOT_GUMMY.get(),     MobEffects.DAMAGE_BOOST);
        put(com.ajsmods.candycraftreborn.registry.ModItems.PEPPERMINT.get(),    MobEffects.MOVEMENT_SPEED);
        // Vanilla items
        put(Items.COOKIE,  MobEffects.BLINDNESS);
        put(Items.SUGAR,   MobEffects.HUNGER);
    }

    private static void put(Item item, MobEffect effect) {
        RECIPE_MAP.put(item, effect);
    }

    public static MobEffect getEffect(Item item) {
        return RECIPE_MAP.getOrDefault(item, MobEffects.HUNGER);
    }

    public static boolean isIngredient(Item item) {
        return RECIPE_MAP.containsKey(item);
    }

    private AlchemyRecipes() {
    }
}
