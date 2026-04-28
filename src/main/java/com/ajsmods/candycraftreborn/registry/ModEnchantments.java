package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.enchantment.DevourerEnchantment;
import com.ajsmods.candycraftreborn.enchantment.HoneyGlueEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, CandyCraftMod.MODID);

    public static final RegistryObject<Enchantment> DEVOURER = ENCHANTMENTS.register("devourer",
            DevourerEnchantment::new);

    public static final RegistryObject<Enchantment> HONEY_GLUE = ENCHANTMENTS.register("honey_glue",
            HoneyGlueEnchantment::new);

    public static void register(IEventBus modEventBus) {
        ENCHANTMENTS.register(modEventBus);
    }
}
