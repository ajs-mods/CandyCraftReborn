package com.ajsmods.candycraftreborn.block;

import com.ajsmods.candycraftreborn.blockentity.MarshmallowChestBlockEntity;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MarshmallowChestBlock extends ChestBlock {
    public MarshmallowChestBlock(BlockBehaviour.Properties properties) {
        super(properties, () -> ModBlockEntities.MARSHMALLOW_CHEST.get());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MarshmallowChestBlockEntity(pos, state);
    }
}
