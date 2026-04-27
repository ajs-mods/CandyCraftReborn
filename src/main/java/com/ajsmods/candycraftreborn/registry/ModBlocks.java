package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
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
