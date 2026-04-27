package com.ajsmods.candycraftreborn.menu;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public class AlchemyTableMenu extends AbstractContainerMenu {
    private final BlockPos blockPos;

    public AlchemyTableMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, extraData.readBlockPos());
    }

    public AlchemyTableMenu(int containerId, Inventory playerInventory, BlockPos blockPos) {
        super(ModMenus.ALCHEMY_TABLE_MENU.get(), containerId);
        this.blockPos = blockPos;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(player.level(), blockPos), player, ModBlocks.ALCHEMY_TABLE.get());
    }
}
