package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.blockentity.AlchemyTableBlockEntity;
import com.ajsmods.candycraftreborn.blockentity.CandyEggBlockEntity;
import com.ajsmods.candycraftreborn.blockentity.LicoriceFurnaceBlockEntity;
import com.ajsmods.candycraftreborn.blockentity.MarshmallowChestBlockEntity;
import com.ajsmods.candycraftreborn.blockentity.SugarFactoryBlockEntity;
import com.ajsmods.candycraftreborn.blockentity.TeleporterBlockEntity;
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

    public static final RegistryObject<BlockEntityType<LicoriceFurnaceBlockEntity>> LICORICE_FURNACE =
            BLOCK_ENTITIES.register("licorice_furnace",
                    () -> BlockEntityType.Builder.of(LicoriceFurnaceBlockEntity::new, ModBlocks.LICORICE_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<MarshmallowChestBlockEntity>> MARSHMALLOW_CHEST =
            BLOCK_ENTITIES.register("marshmallow_chest",
                    () -> BlockEntityType.Builder.of(MarshmallowChestBlockEntity::new, ModBlocks.MARSHMALLOW_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<SugarFactoryBlockEntity>> SUGAR_FACTORY =
            BLOCK_ENTITIES.register("sugar_factory",
                    () -> BlockEntityType.Builder.of(SugarFactoryBlockEntity::new,
                            ModBlocks.SUGAR_FACTORY.get(), ModBlocks.ADVANCED_SUGAR_FACTORY.get()).build(null));

    public static final RegistryObject<BlockEntityType<TeleporterBlockEntity>> TELEPORTER =
            BLOCK_ENTITIES.register("teleporter",
                    () -> BlockEntityType.Builder.of(TeleporterBlockEntity::new, ModBlocks.TELEPORTER_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<CandyEggBlockEntity>> CANDY_EGG =
            BLOCK_ENTITIES.register("candy_egg",
                    () -> BlockEntityType.Builder.of(CandyEggBlockEntity::new,
                            ModBlocks.DRAGON_EGG_BLOCK.get(), ModBlocks.BEETLE_EGG_BLOCK.get()).build(null));

    private ModBlockEntities() {
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
