package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.block.AlchemyTableBlock;
import com.ajsmods.candycraftreborn.block.CandyPortalBlock;
import com.ajsmods.candycraftreborn.block.SugarBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.grower.OakTreeGrower;
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
            () -> new SugarBlock(BlockBehaviour.Properties.of()
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

    // ── Decorative stairs & slabs ───────────────────────────────────────────
    public static final RegistryObject<Block> MARSHMALLOW_STAIRS = registerBlock("marshmallow_stairs",
            () -> new StairBlock(MARSHMALLOW_BLOCK.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SNOW)
                            .strength(0.6F)
                            .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MARSHMALLOW_SLAB = registerBlock("marshmallow_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(0.6F)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> CANDY_CANE_STAIRS = registerBlock("candy_cane_stairs",
            () -> new StairBlock(CANDY_CANE_BLOCK.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_RED)
                            .strength(1.2F)
                            .sound(SoundType.WOOD)));

    public static final RegistryObject<Block> CANDY_CANE_SLAB = registerBlock("candy_cane_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(1.2F)
                    .sound(SoundType.WOOD)));

    public static final RegistryObject<Block> LICORICE_BRICK_STAIRS = registerBlock("licorice_brick_stairs",
            () -> new StairBlock(LICORICE_BRICK.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BLACK)
                            .strength(3.0F, 5.0F)
                            .sound(SoundType.STONE)));

    public static final RegistryObject<Block> LICORICE_BRICK_SLAB = registerBlock("licorice_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLACK)
                    .strength(3.0F, 5.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHOCOLATE_STONE_STAIRS = registerBlock("chocolate_stone_stairs",
            () -> new StairBlock(CHOCOLATE_STONE.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN)
                            .strength(1.8F, 6.0F)
                            .sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHOCOLATE_STONE_SLAB = registerBlock("chocolate_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(1.8F, 6.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> CARAMEL_STAIRS = registerBlock("caramel_stairs",
            () -> new StairBlock(CARAMEL_BLOCK.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_ORANGE)
                            .strength(2.0F, 4.0F)
                            .sound(SoundType.STONE)));

    public static final RegistryObject<Block> CARAMEL_SLAB = registerBlock("caramel_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE)
                    .strength(2.0F, 4.0F)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> COTTON_CANDY_STAIRS = registerBlock("cotton_candy_stairs",
            () -> new StairBlock(COTTON_CANDY_BLOCK.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_PINK)
                            .strength(0.3F)
                            .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> COTTON_CANDY_SLAB = registerBlock("cotton_candy_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK)
                    .strength(0.3F)
                    .sound(SoundType.WOOL)));

    // ── Doors ───────────────────────────────────────────────────────────────
    public static final RegistryObject<Block> MARSHMALLOW_DOOR = registerBlock("marshmallow_door",
            () -> new DoorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW)
                    .strength(3.0F)
                    .sound(SoundType.WOOD)
                    .noOcclusion(),
                    BlockSetType.OAK));

    // ── Ice Cream blocks ────────────────────────────────────────────────────
    public static final RegistryObject<Block> ICE_CREAM_VANILLA = registerBlock("ice_cream_vanilla",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_STRAWBERRY = registerBlock("ice_cream_strawberry",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_CHOCOLATE = registerBlock("ice_cream_chocolate",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_MINT = registerBlock("ice_cream_mint",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN)
                    .strength(0.4F)
                    .sound(SoundType.WOOL)));

    // ── Marshmallow Planks Variants ──────────────────────────────────────────
    public static final RegistryObject<Block> MARSHMALLOW_PLANKS_1 = registerBlock("marshmallow_planks_1",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MARSHMALLOW_PLANKS_2 = registerBlock("marshmallow_planks_2",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    // ── Marshmallow Set ──────────────────────────────────────────────────────
    public static final RegistryObject<Block> MARSHMALLOW_FENCE = registerBlock("marshmallow_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MARSHMALLOW_LADDER = registerBlock("marshmallow_ladder",
            () -> new LadderBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.4F).sound(SoundType.LADDER).noOcclusion()));

    public static final RegistryObject<Block> MARSHMALLOW_TRAPDOOR = registerBlock("marshmallow_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(3.0F).sound(SoundType.WOOD).noOcclusion(),
                    BlockSetType.OAK));

    public static final RegistryObject<Block> MARSHMALLOW_STAIRS_1 = registerBlock("marshmallow_stairs_1",
            () -> new StairBlock(MARSHMALLOW_PLANKS_1.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MARSHMALLOW_SLAB_1 = registerBlock("marshmallow_slab_1",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MARSHMALLOW_STAIRS_2 = registerBlock("marshmallow_stairs_2",
            () -> new StairBlock(MARSHMALLOW_PLANKS_2.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MARSHMALLOW_SLAB_2 = registerBlock("marshmallow_slab_2",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.6F).sound(SoundType.WOOL)));

    // ── Chocolate Cobble Set ─────────────────────────────────────────────────
    public static final RegistryObject<Block> CHOCOLATE_COBBLE_STAIRS = registerBlock("chocolate_cobble_stairs",
            () -> new StairBlock(CHOCOLATE_COBBLESTONE.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN).strength(2.0F, 6.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHOCOLATE_COBBLE_SLAB = registerBlock("chocolate_cobble_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).strength(2.0F, 6.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> CHOCOLATE_COBBLE_WALL = registerBlock("chocolate_cobble_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).strength(2.0F, 6.0F).sound(SoundType.STONE)));

    // ── Caramel Set ──────────────────────────────────────────────────────────
    public static final RegistryObject<Block> CARAMEL_GLASS_0 = registerBlock("caramel_glass_0",
            () -> new GlassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CARAMEL_GLASS_1 = registerBlock("caramel_glass_1",
            () -> new GlassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CARAMEL_GLASS_2 = registerBlock("caramel_glass_2",
            () -> new GlassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CARAMEL_PANE_0 = registerBlock("caramel_pane_0",
            () -> new IronBarsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CARAMEL_PANE_1 = registerBlock("caramel_pane_1",
            () -> new IronBarsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CARAMEL_PANE_2 = registerBlock("caramel_pane_2",
            () -> new IronBarsBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CARAMEL_BRICK_STAIRS = registerBlock("caramel_brick_stairs",
            () -> new StairBlock(CARAMEL_BRICK.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_ORANGE).strength(2.5F, 4.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> CARAMEL_BRICK_SLAB = registerBlock("caramel_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_ORANGE).strength(2.5F, 4.0F).sound(SoundType.STONE)));

    // ── Honey Set ────────────────────────────────────────────────────────────
    public static final RegistryObject<Block> HONEY_TORCH = registerBlock("honey_torch",
            () -> new TorchBlock(BlockBehaviour.Properties.of()
                    .noCollission().instabreak().lightLevel(s -> 14).sound(SoundType.WOOD),
                    ParticleTypes.FLAME));

    public static final RegistryObject<Block> HONEY_LAMP = registerBlock("honey_lamp",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).strength(0.3F).lightLevel(s -> 15).sound(SoundType.GLASS)));

    // ── Jelly Variants ───────────────────────────────────────────────────────
    public static final RegistryObject<Block> RED_TRAMPOJELLY = registerBlock("red_trampojelly",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(3.0F, 2000.0F).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> YELLOW_TRAMPOJELLY = registerBlock("yellow_trampojelly",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).strength(3.0F, 2000.0F).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> JELLY_SHOCK_ABSORBER = registerBlock("jelly_shock_absorber",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BLUE).strength(3.0F, 2000.0F).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> PURPLE_TRAMPOJELLY = registerBlock("purple_trampojelly",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PURPLE).strength(3.0F, 2000.0F).lightLevel(s -> 7).sound(SoundType.SLIME_BLOCK)));

    // ── Cotton Candy Extras ──────────────────────────────────────────────────
    public static final RegistryObject<Block> COTTON_CANDY_WEB = registerBlock("cotton_candy_web",
            () -> new WebBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK).noCollission().strength(4.0F).sound(SoundType.WOOL)));

    // ── Candy Cane Fence & Wall ──────────────────────────────────────────────
    public static final RegistryObject<Block> CANDY_CANE_FENCE = registerBlock("candy_cane_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(1.2F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> CANDY_CANE_WALL = registerBlock("candy_cane_wall",
            () -> new WallBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(1.2F).sound(SoundType.WOOD)));

    // ── Ice Cream Stairs & Slabs ─────────────────────────────────────────────
    public static final RegistryObject<Block> ICE_CREAM_VANILLA_STAIRS = registerBlock("ice_cream_vanilla_stairs",
            () -> new StairBlock(ICE_CREAM_VANILLA.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.TERRACOTTA_WHITE).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_VANILLA_SLAB = registerBlock("ice_cream_vanilla_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_STRAWBERRY_STAIRS = registerBlock("ice_cream_strawberry_stairs",
            () -> new StairBlock(ICE_CREAM_STRAWBERRY.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_RED).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_STRAWBERRY_SLAB = registerBlock("ice_cream_strawberry_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_CHOCOLATE_STAIRS = registerBlock("ice_cream_chocolate_stairs",
            () -> new StairBlock(ICE_CREAM_CHOCOLATE.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_BROWN).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_CHOCOLATE_SLAB = registerBlock("ice_cream_chocolate_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_MINT_STAIRS = registerBlock("ice_cream_mint_stairs",
            () -> new StairBlock(ICE_CREAM_MINT.get()::defaultBlockState,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.4F).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ICE_CREAM_MINT_SLAB = registerBlock("ice_cream_mint_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GREEN).strength(0.4F).sound(SoundType.WOOL)));

    // ── Nature / Plants ──────────────────────────────────────────────────────
    public static final RegistryObject<Block> CANDY_LEAVES = registerBlock("candy_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                    .noOcclusion().isValidSpawn((s, g, p, e) -> false)
                    .isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false)));

    public static final RegistryObject<Block> CANDY_LEAVES_2 = registerBlock("candy_leaves_2",
            () -> new LeavesBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(SoundType.GRASS)
                    .noOcclusion().isValidSpawn((s, g, p, e) -> false)
                    .isSuffocating((s, g, p) -> false).isViewBlocking((s, g, p) -> false)));

    public static final RegistryObject<Block> CANDY_SAPLING = registerBlock("candy_sapling",
            () -> new SaplingBlock(new OakTreeGrower(), BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> TALL_CANDY_GRASS = registerBlock("tall_candy_grass",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)));

    public static final RegistryObject<Block> CANDY_SOIL = registerBlock("candy_soil",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.DIRT).strength(0.6F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Block> LOLLIPOP_PLANT = registerBlock("lollipop_plant",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> LOLLIPOP_BLOCK = registerBlock("lollipop_block",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> PINK_SEAWEED = registerBlock("pink_seaweed",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> GREEN_SEAWEED = registerBlock("green_seaweed",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> BANANA_SEAWEED = registerBlock("banana_seaweed",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_YELLOW).noCollission().instabreak().sound(SoundType.WET_GRASS)));

    public static final RegistryObject<Block> FRAISE_TAGADA_FLOWER = registerBlock("fraise_tagada_flower",
            () -> new FlowerBlock(MobEffects.HEAL, 5, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> POISONOUS_FLOWER = registerBlock("poisonous_flower",
            () -> new FlowerBlock(MobEffects.POISON, 12, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).noCollission().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> SUGAR_ESSENCE_FLOWER = registerBlock("sugar_essence_flower",
            () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 8, BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).noCollission().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> MARSHMALLOW_SLICE = registerBlock("marshmallow_slice",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.0F).sound(SoundType.WOOL).noOcclusion()));

    public static final RegistryObject<Block> MARSHMALLOW_FLOWER_BLOCK = registerBlock("marshmallow_flower_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK).strength(0.0F).sound(SoundType.WOOL).noOcclusion()));

    public static final RegistryObject<Block> CHERRY_BLOCK = registerBlock("cherry_block",
            () -> new BushBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).noCollission().instabreak().sound(SoundType.GRASS)));

    // ── Misc Blocks ──────────────────────────────────────────────────────────
    public static final RegistryObject<Block> JAW_BREAKER_BLOCK = registerBlock("jaw_breaker_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE).strength(-1.0F, 3600000.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> JAW_BREAKER_LIGHT = registerBlock("jaw_breaker_light",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE).strength(-1.0F, 3600000.0F).lightLevel(s -> 15).sound(SoundType.STONE)));

    public static final RegistryObject<Block> CRANBERRY_SPIKES = registerBlock("cranberry_spikes",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(0.0F).noOcclusion().sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUGAR_SPIKES = registerBlock("sugar_spikes",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.SNOW).strength(0.0F).noOcclusion().sound(SoundType.STONE)));

    public static final RegistryObject<Block> NOUGAT_HEAD = registerBlock("nougat_head",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_GRAY).strength(1.0F).sound(SoundType.STONE).noOcclusion()));

    public static final RegistryObject<Block> GRENADINE_BLOCK = registerBlock("grenadine_block",
            () -> new GlassBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_RED).strength(0.3F).sound(SoundType.GLASS).noOcclusion()));

    public static final RegistryObject<Block> CHEWING_GUM_PUDDLE = registerBlock("chewing_gum_puddle",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_PINK).strength(0.5F).sound(SoundType.SLIME_BLOCK).noOcclusion()));

    // ── Key Holes ────────────────────────────────────────────────────────────
    public static final RegistryObject<Block> JELLY_SENTRY_KEY_HOLE = registerBlock("jelly_sentry_key_hole",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).strength(-1.0F, 3600000.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> JELLY_BOSS_KEY_HOLE = registerBlock("jelly_boss_key_hole",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_GREEN).strength(-1.0F, 3600000.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUGUARD_SENTRY_KEY_HOLE = registerBlock("suguard_sentry_key_hole",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE).strength(-1.0F, 3600000.0F).sound(SoundType.STONE)));

    public static final RegistryObject<Block> SUGUARD_BOSS_KEY_HOLE = registerBlock("suguard_boss_key_hole",
            () -> new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_WHITE).strength(-1.0F, 3600000.0F).sound(SoundType.STONE)));

    // ── Portal ──────────────────────────────────────────────────────────────
    // Registered without a BlockItem — portal blocks are not obtainable
    public static final RegistryObject<Block> CANDY_PORTAL = BLOCKS.register("candy_portal",
            () -> new CandyPortalBlock(BlockBehaviour.Properties.of()
                    .noCollission().noLootTable()
                    .strength(-1.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 11)));

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
