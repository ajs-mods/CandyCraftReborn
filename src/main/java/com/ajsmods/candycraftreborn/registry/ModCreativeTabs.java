package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CandyCraftMod.MODID);

    public static final RegistryObject<CreativeModeTab> CANDYCRAFT_TAB = CREATIVE_MODE_TABS.register("candycraft_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.candycraftreborn.candycraft_tab"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(() -> ModItems.PEPPERMINT.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        // Foods & ingredients
                        output.accept(ModItems.PEPPERMINT.get());
                        output.accept(ModItems.CARAMEL_SHARD.get());
                        output.accept(ModItems.SOUR_GEM.get());
                        output.accept(ModItems.LICORICE.get());
                        output.accept(ModItems.CANDY_CANE.get());
                        output.accept(ModItems.LOLLIPOP.get());
                        output.accept(ModItems.GUMMY.get());
                        output.accept(ModItems.HOT_GUMMY.get());
                        output.accept(ModItems.COTTON_CANDY.get());
                        output.accept(ModItems.HONEY_SHARD.get());
                        output.accept(ModItems.HONEYCOMB.get());
                        output.accept(ModItems.CHOCOLATE_COIN.get());
                        output.accept(ModItems.SUGAR_CRYSTAL.get());
                        output.accept(ModItems.SUGAR_PILL.get());
                        output.accept(ModItems.DRAGIBUS.get());
                        output.accept(ModItems.WAFFLE.get());
                        output.accept(ModItems.WAFFLE_NUGGET.get());
                        output.accept(ModItems.CANDIED_CHERRY.get());
                        output.accept(ModItems.CRANBERRY_FISH.get());
                        output.accept(ModItems.CRANBERRY_FISH_COOKED.get());
                        // Materials
                        output.accept(ModItems.CARAMEL_BUCKET.get());
                        output.accept(ModItems.PEZ.get());
                        output.accept(ModItems.PEZ_DUST.get());
                        output.accept(ModItems.NOUGAT_POWDER.get());
                        output.accept(ModItems.MARSHMALLOW_STICK.get());
                        output.accept(ModItems.GUMMY_BALL.get());
                        // Tools
                        output.accept(ModItems.MARSHMALLOW_SWORD.get());
                        output.accept(ModItems.MARSHMALLOW_PICKAXE.get());
                        output.accept(ModItems.MARSHMALLOW_AXE.get());
                        output.accept(ModItems.MARSHMALLOW_SHOVEL.get());
                        output.accept(ModItems.MARSHMALLOW_HOE.get());
                        output.accept(ModItems.LICORICE_SWORD.get());
                        output.accept(ModItems.LICORICE_PICKAXE.get());
                        output.accept(ModItems.LICORICE_AXE.get());
                        output.accept(ModItems.LICORICE_SHOVEL.get());
                        output.accept(ModItems.LICORICE_HOE.get());
                        output.accept(ModItems.HONEY_SWORD.get());
                        output.accept(ModItems.HONEY_PICKAXE.get());
                        output.accept(ModItems.HONEY_AXE.get());
                        output.accept(ModItems.HONEY_SHOVEL.get());
                        output.accept(ModItems.HONEY_HOE.get());
                        output.accept(ModItems.PEZ_SWORD.get());
                        output.accept(ModItems.PEZ_PICKAXE.get());
                        output.accept(ModItems.PEZ_AXE.get());
                        output.accept(ModItems.PEZ_SHOVEL.get());
                        output.accept(ModItems.PEZ_HOE.get());
                        // Weapons
                        output.accept(ModItems.LICORICE_SPEAR.get());
                        output.accept(ModItems.FORK.get());
                        output.accept(ModItems.DRAGIBUS_STICK.get());
                        output.accept(ModItems.JELLY_WAND.get());
                        output.accept(ModItems.JUMP_WAND.get());
                        output.accept(ModItems.CARAMEL_BOW.get());
                        output.accept(ModItems.CARAMEL_CROSSBOW.get());
                        // Armor
                        output.accept(ModItems.LICORICE_HELMET.get());
                        output.accept(ModItems.LICORICE_PLATE.get());
                        output.accept(ModItems.LICORICE_LEGGINGS.get());
                        output.accept(ModItems.LICORICE_BOOTS.get());
                        output.accept(ModItems.HONEY_HELMET.get());
                        output.accept(ModItems.HONEY_PLATE.get());
                        output.accept(ModItems.HONEY_LEGGINGS.get());
                        output.accept(ModItems.HONEY_BOOTS.get());
                        output.accept(ModItems.PEZ_HELMET.get());
                        output.accept(ModItems.PEZ_PLATE.get());
                        output.accept(ModItems.PEZ_LEGGINGS.get());
                        output.accept(ModItems.PEZ_BOOTS.get());
                        output.accept(ModItems.JELLY_CROWN.get());
                        output.accept(ModItems.JELLY_BOOTS.get());
                        output.accept(ModItems.WATER_MASK.get());
                        // Keys
                        output.accept(ModItems.JELLY_KEY.get());
                        output.accept(ModItems.SUGUARD_KEY.get());
                        output.accept(ModItems.SKY_KEY.get());
                        output.accept(ModItems.BEETLE_KEY.get());
                        output.accept(ModItems.JELLY_SENTRY_KEY.get());
                        output.accept(ModItems.JELLY_BOSS_KEY.get());
                        output.accept(ModItems.SUGUARD_SENTRY_KEY.get());
                        output.accept(ModItems.SUGUARD_BOSS_KEY.get());
                        // Emblems
                        output.accept(ModItems.HONEY_EMBLEM.get());
                        output.accept(ModItems.JELLY_EMBLEM.get());
                        output.accept(ModItems.SUGUARD_EMBLEM.get());
                        output.accept(ModItems.CRANBERRY_EMBLEM.get());
                        output.accept(ModItems.GINGERBREAD_EMBLEM.get());
                        output.accept(ModItems.WATER_EMBLEM.get());
                        output.accept(ModItems.CHEWING_GUM_EMBLEM.get());
                        output.accept(ModItems.SKY_EMBLEM.get());
                        // Projectiles
                        output.accept(ModItems.HONEY_ARROW.get());
                        output.accept(ModItems.HONEY_BOLT.get());
                        output.accept(ModItems.DYNAMITE.get());
                        output.accept(ModItems.GLUE_DYNAMITE.get());
                        // Music Discs
                        output.accept(ModItems.RECORD_1.get());
                        output.accept(ModItems.RECORD_2.get());
                        output.accept(ModItems.RECORD_3.get());
                        output.accept(ModItems.RECORD_4.get());
                        // Misc
                        output.accept(ModItems.LOLLIPOP_SEEDS.get());
                        output.accept(ModItems.CRANBERRY_SCALE.get());
                        output.accept(ModItems.CHEWING_GUM.get());
                        output.accept(ModItems.MARSHMALLOW_FLOWER.get());
                        output.accept(ModItems.GRENADINE_BUCKET.get());
                        // Building blocks
                        output.accept(ModBlocks.ROCK_CANDY_BLOCK.get());
                        output.accept(ModBlocks.MARSHMALLOW_BLOCK.get());
                        output.accept(ModBlocks.GUMMY_BLOCK.get());
                        output.accept(ModBlocks.LICORICE_BLOCK.get());
                        output.accept(ModBlocks.CANDY_CANE_BLOCK.get());
                        output.accept(ModBlocks.CARAMEL_BLOCK.get());
                        output.accept(ModBlocks.SUGAR_BLOCK.get());
                        output.accept(ModBlocks.CHOCOLATE_STONE.get());
                        output.accept(ModBlocks.COTTON_CANDY_BLOCK.get());
                        output.accept(ModBlocks.MINT_BLOCK.get());
                        output.accept(ModBlocks.RASPBERRY_BLOCK.get());
                        output.accept(ModBlocks.BANANA_BLOCK.get());
                        output.accept(ModBlocks.NOUGAT_BLOCK.get());
                        output.accept(ModBlocks.PUDDING.get());
                        output.accept(ModBlocks.FLOUR.get());
                        output.accept(ModBlocks.MARSHMALLOW_LOG.get());
                        output.accept(ModBlocks.LICORICE_BRICK.get());
                        output.accept(ModBlocks.CHOCOLATE_COBBLESTONE.get());
                        output.accept(ModBlocks.PEZ_BLOCK.get());
                        output.accept(ModBlocks.HONEY_BLOCK_CC.get());
                        output.accept(ModBlocks.CARAMEL_BRICK.get());
                        output.accept(ModBlocks.CHEWING_GUM_BLOCK.get());
                        output.accept(ModBlocks.TRAMPOJELLY.get());
                        // Ores
                        output.accept(ModBlocks.LICORICE_ORE.get());
                        output.accept(ModBlocks.JELLY_ORE.get());
                        output.accept(ModBlocks.HONEY_ORE.get());
                        output.accept(ModBlocks.NOUGAT_ORE.get());
                        output.accept(ModBlocks.PEZ_ORE.get());
                        // Decorative stairs & slabs
                        output.accept(ModBlocks.MARSHMALLOW_STAIRS.get());
                        output.accept(ModBlocks.MARSHMALLOW_SLAB.get());
                        output.accept(ModBlocks.CANDY_CANE_STAIRS.get());
                        output.accept(ModBlocks.CANDY_CANE_SLAB.get());
                        output.accept(ModBlocks.LICORICE_BRICK_STAIRS.get());
                        output.accept(ModBlocks.LICORICE_BRICK_SLAB.get());
                        output.accept(ModBlocks.CHOCOLATE_STONE_STAIRS.get());
                        output.accept(ModBlocks.CHOCOLATE_STONE_SLAB.get());
                        output.accept(ModBlocks.CARAMEL_STAIRS.get());
                        output.accept(ModBlocks.CARAMEL_SLAB.get());
                        output.accept(ModBlocks.COTTON_CANDY_STAIRS.get());
                        output.accept(ModBlocks.COTTON_CANDY_SLAB.get());
                        // Doors & specialty
                        output.accept(ModBlocks.MARSHMALLOW_DOOR.get());
                        // Ice Cream
                        output.accept(ModBlocks.ICE_CREAM_VANILLA.get());
                        output.accept(ModBlocks.ICE_CREAM_STRAWBERRY.get());
                        output.accept(ModBlocks.ICE_CREAM_CHOCOLATE.get());
                        output.accept(ModBlocks.ICE_CREAM_MINT.get());
                        // Marshmallow Set
                        output.accept(ModBlocks.MARSHMALLOW_PLANKS_1.get());
                        output.accept(ModBlocks.MARSHMALLOW_PLANKS_2.get());
                        output.accept(ModBlocks.MARSHMALLOW_FENCE.get());
                        output.accept(ModBlocks.MARSHMALLOW_LADDER.get());
                        output.accept(ModBlocks.MARSHMALLOW_TRAPDOOR.get());
                        output.accept(ModBlocks.MARSHMALLOW_STAIRS_1.get());
                        output.accept(ModBlocks.MARSHMALLOW_SLAB_1.get());
                        output.accept(ModBlocks.MARSHMALLOW_STAIRS_2.get());
                        output.accept(ModBlocks.MARSHMALLOW_SLAB_2.get());
                        // Chocolate Cobble
                        output.accept(ModBlocks.CHOCOLATE_COBBLE_STAIRS.get());
                        output.accept(ModBlocks.CHOCOLATE_COBBLE_SLAB.get());
                        output.accept(ModBlocks.CHOCOLATE_COBBLE_WALL.get());
                        // Caramel Set
                        output.accept(ModBlocks.CARAMEL_GLASS_0.get());
                        output.accept(ModBlocks.CARAMEL_GLASS_1.get());
                        output.accept(ModBlocks.CARAMEL_GLASS_2.get());
                        output.accept(ModBlocks.CARAMEL_PANE_0.get());
                        output.accept(ModBlocks.CARAMEL_PANE_1.get());
                        output.accept(ModBlocks.CARAMEL_PANE_2.get());
                        output.accept(ModBlocks.CARAMEL_BRICK_STAIRS.get());
                        output.accept(ModBlocks.CARAMEL_BRICK_SLAB.get());
                        // Honey
                        output.accept(ModBlocks.HONEY_TORCH.get());
                        output.accept(ModBlocks.HONEY_LAMP.get());
                        // Jelly Variants
                        output.accept(ModBlocks.RED_TRAMPOJELLY.get());
                        output.accept(ModBlocks.YELLOW_TRAMPOJELLY.get());
                        output.accept(ModBlocks.JELLY_SHOCK_ABSORBER.get());
                        output.accept(ModBlocks.PURPLE_TRAMPOJELLY.get());
                        // Cotton Candy
                        output.accept(ModBlocks.COTTON_CANDY_WEB.get());
                        // Candy Cane
                        output.accept(ModBlocks.CANDY_CANE_FENCE.get());
                        output.accept(ModBlocks.CANDY_CANE_WALL.get());
                        // Ice Cream Stairs & Slabs
                        output.accept(ModBlocks.ICE_CREAM_VANILLA_STAIRS.get());
                        output.accept(ModBlocks.ICE_CREAM_VANILLA_SLAB.get());
                        output.accept(ModBlocks.ICE_CREAM_STRAWBERRY_STAIRS.get());
                        output.accept(ModBlocks.ICE_CREAM_STRAWBERRY_SLAB.get());
                        output.accept(ModBlocks.ICE_CREAM_CHOCOLATE_STAIRS.get());
                        output.accept(ModBlocks.ICE_CREAM_CHOCOLATE_SLAB.get());
                        output.accept(ModBlocks.ICE_CREAM_MINT_STAIRS.get());
                        output.accept(ModBlocks.ICE_CREAM_MINT_SLAB.get());
                        // Nature
                        output.accept(ModBlocks.CANDY_LEAVES.get());
                        output.accept(ModBlocks.CANDY_LEAVES_2.get());
                        output.accept(ModBlocks.CANDY_SAPLING.get());
                        output.accept(ModBlocks.TALL_CANDY_GRASS.get());
                        output.accept(ModBlocks.CANDY_SOIL.get());
                        output.accept(ModBlocks.LOLLIPOP_PLANT.get());
                        output.accept(ModBlocks.LOLLIPOP_BLOCK.get());
                        output.accept(ModBlocks.PINK_SEAWEED.get());
                        output.accept(ModBlocks.GREEN_SEAWEED.get());
                        output.accept(ModBlocks.BANANA_SEAWEED.get());
                        output.accept(ModBlocks.FRAISE_TAGADA_FLOWER.get());
                        output.accept(ModBlocks.POISONOUS_FLOWER.get());
                        output.accept(ModBlocks.SUGAR_ESSENCE_FLOWER.get());
                        output.accept(ModBlocks.MARSHMALLOW_SLICE.get());
                        output.accept(ModBlocks.MARSHMALLOW_FLOWER_BLOCK.get());
                        output.accept(ModBlocks.CHERRY_BLOCK.get());
                        // Misc
                        output.accept(ModBlocks.JAW_BREAKER_BLOCK.get());
                        output.accept(ModBlocks.JAW_BREAKER_LIGHT.get());
                        output.accept(ModBlocks.CRANBERRY_SPIKES.get());
                        output.accept(ModBlocks.SUGAR_SPIKES.get());
                        output.accept(ModBlocks.NOUGAT_HEAD.get());
                        output.accept(ModBlocks.GRENADINE_BLOCK.get());
                        output.accept(ModBlocks.CHEWING_GUM_PUDDLE.get());
                        // Key Holes
                        output.accept(ModBlocks.JELLY_SENTRY_KEY_HOLE.get());
                        output.accept(ModBlocks.JELLY_BOSS_KEY_HOLE.get());
                        output.accept(ModBlocks.SUGUARD_SENTRY_KEY_HOLE.get());
                        output.accept(ModBlocks.SUGUARD_BOSS_KEY_HOLE.get());
                        // Machines
                        output.accept(ModBlocks.ALCHEMY_TABLE.get());
                        // Entities
                        output.accept(ModItems.CANDY_CRITTER_SPAWN_EGG.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
