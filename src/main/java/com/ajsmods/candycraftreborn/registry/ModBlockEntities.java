package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.blockentity.AlchemyTableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CandyCraftMod.MODID);

    public static final RegistryObject<BlockEntityType<AlchemyTableBlockEntity>> ALCHEMY_TABLE =
            BLOCK_ENTITIES.register("alchemy_table",
                    () -> BlockEntityType.Builder.of(AlchemyTableBlockEntity::new, ModBlocks.ALCHEMY_TABLE.get()).build(null));

    private ModBlockEntities() {
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
