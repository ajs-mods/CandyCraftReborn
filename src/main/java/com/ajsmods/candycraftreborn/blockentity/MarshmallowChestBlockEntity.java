package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MarshmallowChestBlockEntity extends ChestBlockEntity {
    public MarshmallowChestBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MARSHMALLOW_CHEST.get(), pos, state);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("container.candycraftreborn.marshmallow_chest");
    }
}
