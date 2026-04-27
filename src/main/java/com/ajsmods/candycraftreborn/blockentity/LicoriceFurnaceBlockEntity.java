package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.FurnaceMenu;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LicoriceFurnaceBlockEntity extends AbstractFurnaceBlockEntity {
    public LicoriceFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.LICORICE_FURNACE.get(), pos, state, RecipeType.SMELTING);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.candycraftreborn.licorice_furnace");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new FurnaceMenu(containerId, playerInventory, this, this.dataAccess);
    }
}
