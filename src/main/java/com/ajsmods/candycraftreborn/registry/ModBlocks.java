package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.block.AlchemyTableBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CandyCraftMod.MODID);

    public static final RegistryObject<Block> ROCK_CANDY_BLOCK = registerBlock("rock_candy_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_PINK)
                    .strength(1.8F)
                    .sound(SoundType.AMETHYST)));

    public static final RegistryObject<Block> MARSHMALLOW_BLOCK = registerBlock("marshmallow_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.6F)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> GUMMY_BLOCK = registerBlock("gummy_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.0F)
                    .sound(SoundType.HONEY_BLOCK)));

        public static final RegistryObject<Block> LICORICE_BLOCK = registerBlock("licorice_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BLACK)
                .strength(5.0F, 8.0F)
                .sound(SoundType.METAL)));

        public static final RegistryObject<Block> CANDY_CANE_BLOCK = registerBlock("candy_cane_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .strength(1.2F)
                .sound(SoundType.WOOD)));

        public static final RegistryObject<Block> CARAMEL_BLOCK = registerBlock("caramel_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_ORANGE)
                .strength(2.0F, 4.0F)
                .sound(SoundType.MUD)));

        public static final RegistryObject<Block> SUGAR_BLOCK = registerBlock("sugar_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.SAND)
                .strength(0.4F)
                .sound(SoundType.SAND)));

        public static final RegistryObject<Block> CHOCOLATE_STONE = registerBlock("chocolate_stone",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_BROWN)
                .strength(1.8F, 6.0F)
                .sound(SoundType.STONE)));

        public static final RegistryObject<Block> COTTON_CANDY_BLOCK = registerBlock("cotton_candy_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_PINK)
                .strength(0.3F)
                .sound(SoundType.WOOL)));

        public static final RegistryObject<Block> MINT_BLOCK = registerBlock("mint_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
                .strength(1.0F)
                .sound(SoundType.GRASS)));

        public static final RegistryObject<Block> RASPBERRY_BLOCK = registerBlock("raspberry_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_RED)
                .strength(1.0F)
                .sound(SoundType.GRASS)));

        public static final RegistryObject<Block> BANANA_BLOCK = registerBlock("banana_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_YELLOW)
                .strength(1.0F)
                .sound(SoundType.GRASS)));

        public static final RegistryObject<Block> NOUGAT_BLOCK = registerBlock("nougat_block",
            () -> new Block(BlockBehaviour.Properties.of()
                .mapColor(MapColor.COLOR_LIGHT_GRAY)
                .strength(2.0F, 5.0F)
                .sound(SoundType.STONE)));

    public static final RegistryObject<Block> ALCHEMY_TABLE = registerBlock("alchemy_table",
            () -> new AlchemyTableBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(2.5F, 6.0F)
                    .sound(SoundType.METAL)));

    // ── Ores ─────────────────────────────────────────────────────────────────
    public static final RegistryObject<Block> LICORICE_ORE = registerBlock("licorice_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(3.0F, 5.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> JELLY_ORE = registerBlock("jelly_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(3.0F, 5.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> HONEY_ORE = registerBlock("honey_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(3.0F, 5.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> NOUGAT_ORE = registerBlock("nougat_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY)
                    .strength(3.0F, 5.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> PEZ_ORE = registerBlock("pez_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .strength(3.5F, 5.0F)
                    .sound(SoundType.STONE)
                    .requiresCorrectToolForDrops()));

    // ── Nature ───────────────────────────────────────────────────────────────
    public static final RegistryObject<Block> PUDDING = registerBlock("pudding",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_YELLOW)
                    .strength(0.6F)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> FLOUR = registerBlock("flour",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SAND)
                    .strength(0.6F)
                    .sound(SoundType.SAND)));

    public static final RegistryObject<Block> MARSHMALLOW_LOG = registerBlock("marshmallow_log",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(2.0F)
                    .sound(SoundType.WOOD)));

    // ── Decorative ───────────────────────────────────────────────────────────
    public static final RegistryObject<Block> LICORICE_BRICK = registerBlock("licorice_brick",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(3.0F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHOCOLATE_COBBLESTONE = registerBlock("chocolate_cobblestone",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(2.0F, 6.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> PEZ_BLOCK = registerBlock("pez_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIAMOND)
                    .strength(5.0F, 6.0F)
                    .sound(SoundType.METAL)));

    public static final RegistryObject<Block> HONEY_BLOCK_CC = registerBlock("honey_block_cc",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW)
                    .strength(2.5F)
                    .sound(SoundType.HONEY_BLOCK)));

    public static final RegistryObject<Block> CARAMEL_BRICK = registerBlock("caramel_brick",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(2.5F, 4.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHEWING_GUM_BLOCK = registerBlock("chewing_gum_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(1.5F)
                    .sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> TRAMPOJELLY = registerBlock("trampojelly",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN)
                    .strength(3.0F, 2000.0F)
                    .sound(SoundType.SLIME_BLOCK)));

    private ModBlocks() {
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> blockSupplier) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        registerBlockItem(name, block);
        return block;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
