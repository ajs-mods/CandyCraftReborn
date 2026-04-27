package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.menu.AlchemyTableMenu;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AlchemyTableBlockEntity extends BlockEntity implements MenuProvider {
    public AlchemyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALCHEMY_TABLE.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.candycraftreborn.alchemy_table");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AlchemyTableMenu(containerId, playerInventory, worldPosition);
    }
}
