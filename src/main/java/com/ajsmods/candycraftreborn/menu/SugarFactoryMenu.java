package com.ajsmods.candycraftreborn.menu;

import com.ajsmods.candycraftreborn.blockentity.SugarFactoryBlockEntity;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SugarFactoryMenu extends AbstractContainerMenu {
    private final SugarFactoryBlockEntity blockEntity;
    private final ContainerData data;
    private final ContainerLevelAccess levelAccess;

    public SugarFactoryMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(containerId, playerInventory, getBlockEntity(playerInventory, extraData), new SimpleContainerData(SugarFactoryBlockEntity.DATA_COUNT));
    }

    private static SugarFactoryBlockEntity getBlockEntity(Inventory inv, FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        BlockEntity be = inv.player.level().getBlockEntity(pos);
        if (be instanceof SugarFactoryBlockEntity sf) return sf;
        throw new IllegalStateException("SugarFactoryBlockEntity not found at " + pos);
    }

    public SugarFactoryMenu(int containerId, Inventory playerInventory, SugarFactoryBlockEntity be, ContainerData data) {
        super(ModMenus.SUGAR_FACTORY_MENU.get(), containerId);
        this.blockEntity = be;
        this.data = data;
        this.levelAccess = ContainerLevelAccess.create(be.getLevel(), be.getBlockPos());

        addSlot(new Slot(be, SugarFactoryBlockEntity.SLOT_INPUT, 56, 17));
        addSlot(new Slot(be, SugarFactoryBlockEntity.SLOT_FUEL, 56, 53));
        addSlot(new Slot(be, SugarFactoryBlockEntity.SLOT_OUTPUT, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }
        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(playerInventory, col, 8 + col * 18, 142));
        }

        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack result = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            result = stack.copy();
            if (index < SugarFactoryBlockEntity.SLOT_COUNT) {
                if (!this.moveItemStackTo(stack, SugarFactoryBlockEntity.SLOT_COUNT, this.slots.size(), true)) return ItemStack.EMPTY;
            } else {
                if (!this.moveItemStackTo(stack, 0, SugarFactoryBlockEntity.SLOT_COUNT, false)) return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return result;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(levelAccess, player, ModBlocks.SUGAR_FACTORY.get())
                || stillValid(levelAccess, player, ModBlocks.ADVANCED_SUGAR_FACTORY.get());
    }

    public boolean isBurning() {
        return data.get(0) > 0;
    }

    public int getBurnProgress() {
        int burnDuration = data.get(1);
        if (burnDuration == 0) burnDuration = 200;
        return data.get(0) * 13 / burnDuration;
    }

    public int getCookProgress() {
        int processTime = data.get(2);
        int processTimeTotal = data.get(3);
        if (processTimeTotal == 0) return 0;
        return processTime * 24 / processTimeTotal;
    }
}
