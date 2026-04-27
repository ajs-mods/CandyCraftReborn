package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.block.SugarFactoryBlock;
import com.ajsmods.candycraftreborn.menu.SugarFactoryMenu;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class SugarFactoryBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, MenuProvider {
    public static final int SLOT_INPUT = 0;
    public static final int SLOT_FUEL = 1;
    public static final int SLOT_OUTPUT = 2;
    public static final int SLOT_COUNT = 3;
    public static final int DATA_COUNT = 4;

    private NonNullList<ItemStack> items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
    private int burnTime;
    private int burnDuration;
    private int processTime;
    private int processTimeTotal = 200;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTime;
                case 1 -> burnDuration;
                case 2 -> processTime;
                case 3 -> processTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTime = value;
                case 1 -> burnDuration = value;
                case 2 -> processTime = value;
                case 3 -> processTimeTotal = value;
            }
        }

        @Override
        public int getCount() {
            return DATA_COUNT;
        }
    };

    public SugarFactoryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SUGAR_FACTORY.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.candycraftreborn.sugar_factory");
    }

    @Override
    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return new SugarFactoryMenu(containerId, playerInventory, this, this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return SLOT_COUNT;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if (side == Direction.DOWN) return new int[]{SLOT_OUTPUT};
        if (side == Direction.UP) return new int[]{SLOT_INPUT};
        return new int[]{SLOT_FUEL};
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
        return slot != SLOT_OUTPUT;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
        return slot == SLOT_OUTPUT;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("BurnDuration", burnDuration);
        tag.putInt("ProcessTime", processTime);
        tag.putInt("ProcessTimeTotal", processTimeTotal);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items = NonNullList.withSize(SLOT_COUNT, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, items);
        burnTime = tag.getInt("BurnTime");
        burnDuration = tag.getInt("BurnDuration");
        processTime = tag.getInt("ProcessTime");
        processTimeTotal = tag.getInt("ProcessTimeTotal");
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SugarFactoryBlockEntity be) {
        boolean wasLit = be.burnTime > 0;
        boolean changed = false;

        if (be.burnTime > 0) {
            be.burnTime--;
        }

        ItemStack input = be.items.get(SLOT_INPUT);
        ItemStack fuel = be.items.get(SLOT_FUEL);

        if (!input.isEmpty() && (be.burnTime > 0 || !fuel.isEmpty())) {
            if (be.burnTime <= 0 && !fuel.isEmpty()) {
                be.burnDuration = 200; // simplified burn time
                be.burnTime = be.burnDuration;
                fuel.shrink(1);
                changed = true;
            }

            if (be.burnTime > 0 && !input.isEmpty()) {
                be.processTime++;
                if (be.processTime >= be.processTimeTotal) {
                    be.processTime = 0;
                    // Output sugar from any input
                    ItemStack output = be.items.get(SLOT_OUTPUT);
                    if (output.isEmpty()) {
                        be.items.set(SLOT_OUTPUT, new ItemStack(Items.SUGAR, 1));
                    } else if (output.getItem() == Items.SUGAR && output.getCount() < output.getMaxStackSize()) {
                        output.grow(1);
                    }
                    input.shrink(1);
                    changed = true;
                }
            }
        } else {
            if (be.processTime > 0) {
                be.processTime = 0;
                changed = true;
            }
        }

        boolean isLit = be.burnTime > 0;
        if (wasLit != isLit) {
            level.setBlock(pos, state.setValue(SugarFactoryBlock.LIT, isLit), 3);
            changed = true;
        }

        if (changed) be.setChanged();
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }
}
