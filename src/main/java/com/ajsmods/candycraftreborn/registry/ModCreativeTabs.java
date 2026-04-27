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
                        output.accept(ModItems.CANDY_CRITTER_SPAWN_EGG.get());
                    })
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
