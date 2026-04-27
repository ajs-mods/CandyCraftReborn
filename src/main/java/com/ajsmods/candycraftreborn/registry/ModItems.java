package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.item.SugarPillItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CandyCraftMod.MODID);

    public static final RegistryObject<Item> PEPPERMINT = ITEMS.register("peppermint",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build())));

    public static final RegistryObject<Item> CARAMEL_SHARD = ITEMS.register("caramel_shard",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUR_GEM = ITEMS.register("sour_gem",
        () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> LICORICE = ITEMS.register("licorice",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().build())));

    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).alwaysEat().build())));

    public static final RegistryObject<Item> LOLLIPOP = ITEMS.register("lollipop",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).alwaysEat().build())));

    public static final RegistryObject<Item> GUMMY = ITEMS.register("gummy",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5F).build())));

    public static final RegistryObject<Item> HOT_GUMMY = ITEMS.register("hot_gummy",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.7F).build())));

    public static final RegistryObject<Item> COTTON_CANDY = ITEMS.register("cotton_candy",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).alwaysEat().build())));

    public static final RegistryObject<Item> HONEY_SHARD = ITEMS.register("honey_shard",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HONEYCOMB = ITEMS.register("honeycomb",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHOCOLATE_COIN = ITEMS.register("chocolate_coin",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    public static final RegistryObject<Item> SUGAR_CRYSTAL = ITEMS.register("sugar_crystal",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<SugarPillItem> SUGAR_PILL = ITEMS.register("sugar_pill",
        () -> new SugarPillItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CANDY_CRITTER_SPAWN_EGG = ITEMS.register("candy_critter_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntities.CANDY_CRITTER, 0xB26BFF, 0xFFF6BD, new Item.Properties()));

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
