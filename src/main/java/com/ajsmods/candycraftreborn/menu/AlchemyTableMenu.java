package com.ajsmods.candycraftreborn.menu;

import com.ajsmods.candycraftreborn.blockentity.AlchemyTableBlockEntity;
import com.ajsmods.candycraftreborn.recipe.AlchemyRecipes;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class AlchemyTableMenu extends AbstractContainerMenu {
    private final AlchemyTableBlockEntity blockEntity;
    private final ContainerLevelAccess levelAccess;

    /** Network constructor — opens from block entity at the given position. */
    public AlchemyTableMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, getBlockEntity(playerInventory, extraData));
    }

    private static AlchemyTableBlockEntity getBlockEntity(Inventory playerInventory, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = playerInventory.player.level().getBlockEntity(pos);
        if (be instanceof AlchemyTableBlockEntity ate) return ate;
        throw new IllegalStateException("AlchemyTableBlockEntity not found at " + pos);
    }

    /** Direct constructor (server-side). */
    public AlchemyTableMenu(int containerId, Inventory playerInventory, AlchemyTableBlockEntity be) {
        super(ModMenus.ALCHEMY_TABLE_MENU.get(), containerId);
        this.blockEntity = be;
        this.levelAccess = ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());

        // ----- 4 ingredient slots (arranged in a 2×2 grid, GUI coords) -----
        //  Slot 0 (top-left)  Slot 1 (top-right)
        //  Slot 2 (bot-left)  Slot 3 (bot-right)
        var inv = be.getIngredients();
        addSlot(new AlchemySlot(inv, 0, 62,  27, be));  // top-left
        addSlot(new AlchemySlot(inv, 1, 98,  27, be));  // top-right
        addSlot(new AlchemySlot(inv, 2, 62,  63, be));  // bottom-left
        addSlot(new AlchemySlot(inv, 3, 98,  63, be));  // bottom-right

        // ----- Player inventory (rows 0-2) -----
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 103 + row * 18));
            }
        }
        // ----- Hot-bar -----
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 161));
        }
    }

    /** A slot that only accepts valid alchemy ingredients. */
    private static class AlchemySlot extends Slot {
        private final AlchemyTableBlockEntity be;
        AlchemySlot(net.minecraft.world.Container container, int slot, int x, int y, AlchemyTableBlockEntity be) {
            super(container, slot, x, y);
            this.be = be;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return AlchemyRecipes.isIngredient(stack.getItem());
        }

        @Override
        public void setChanged() {
            super.setChanged();
            be.onIngredientChanged();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            int beSlots = AlchemyTableBlockEntity.SLOT_COUNT;
            int total = this.slots.size();
            if (index < beSlots) {
                // Move from BE to player inventory
                if (!this.moveItemStackTo(stack, beSlots, total, true)) return ItemStack.EMPTY;
            } else {
                // Move from player to BE
                if (!this.moveItemStackTo(stack, 0, beSlots, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(levelAccess, player, ModBlocks.ALCHEMY_TABLE.get());
    }

    public AlchemyTableBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
