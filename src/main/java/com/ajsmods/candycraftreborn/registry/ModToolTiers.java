package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public final class ModToolTiers {
    public static final Tier MARSHMALLOW = TierSortingRegistry.registerTier(
            new ForgeTier(0, 59, 2.0F, 0.0F, 15,
                    null, () -> Ingredient.of(ModItems.MARSHMALLOW_STICK.get())),
            new ResourceLocation(CandyCraftMod.MODID, "marshmallow"),
            List.of(), List.of(Tiers.WOOD));

    public static final Tier LICORICE = TierSortingRegistry.registerTier(
            new ForgeTier(1, 175, 4.0F, 1.0F, 8,
                    null, () -> Ingredient.of(ModItems.LICORICE.get())),
            new ResourceLocation(CandyCraftMod.MODID, "licorice"),
            List.of(Tiers.STONE), List.of(Tiers.IRON));

    public static final Tier HONEY = TierSortingRegistry.registerTier(
            new ForgeTier(3, 400, 7.0F, 2.0F, 18,
                    null, () -> Ingredient.of(ModItems.HONEY_SHARD.get())),
            new ResourceLocation(CandyCraftMod.MODID, "honey"),
            List.of(Tiers.IRON), List.of(Tiers.DIAMOND));

    public static final Tier PEZ = TierSortingRegistry.registerTier(
            new ForgeTier(4, 1034, 7.6F, 3.4F, 3,
                    null, () -> Ingredient.of(ModItems.PEZ.get())),
            new ResourceLocation(CandyCraftMod.MODID, "pez"),
            List.of(Tiers.DIAMOND), List.of(Tiers.NETHERITE));

    private ModToolTiers() {}
}
