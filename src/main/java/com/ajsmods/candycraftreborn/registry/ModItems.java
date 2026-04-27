package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
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

    public static final RegistryObject<Item> CANDY_CRITTER_SPAWN_EGG = ITEMS.register("candy_critter_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntities.CANDY_CRITTER, 0xB26BFF, 0xFFF6BD, new Item.Properties()));

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
