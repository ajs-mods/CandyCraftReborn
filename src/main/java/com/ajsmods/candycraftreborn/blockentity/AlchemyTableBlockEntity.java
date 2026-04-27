package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.item.SugarPillItem;
import com.ajsmods.candycraftreborn.menu.AlchemyTableMenu;
import com.ajsmods.candycraftreborn.recipe.AlchemyRecipes;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class AlchemyTableBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SLOT_COUNT = 4;

    private final SimpleContainer ingredients = new SimpleContainer(SLOT_COUNT) {
        @Override
        public void setChanged() {
            super.setChanged();
            AlchemyTableBlockEntity.this.setChanged();
        }
    };

    public AlchemyTableBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALCHEMY_TABLE.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("menu.candycraftreborn.alchemy_table");
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AlchemyTableMenu(containerId, playerInventory, this);
    }

    public SimpleContainer getIngredients() {
        return ingredients;
    }

    /** Called by the menu when a player places an item into a slot. */
    public void onIngredientChanged() {
        if (level == null || level.isClientSide) return;
        tryProcess();
    }

    private void tryProcess() {
        // Only process when all 4 slots are filled
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (ingredients.getItem(i).isEmpty()) return;
        }

        // Build effect id list
        List<String> effects = new ArrayList<>();
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack slot = ingredients.getItem(i);
            var effect = AlchemyRecipes.getEffect(slot.getItem());
            ResourceLocation key = ForgeRegistries.MOB_EFFECTS.getKey(effect);
            if (key != null) effects.add(key.toString());
        }

        // Create sugar pill with encoded effects
        ItemStack pill = new ItemStack(ModItems.SUGAR_PILL.get());
        CompoundTag tag = pill.getOrCreateTag();
        ListTag effectList = new ListTag();
        effects.forEach(e -> effectList.add(StringTag.valueOf(e)));
        tag.put(SugarPillItem.TAG_EFFECTS, effectList);

        // Spawn item above the block
        double x = worldPosition.getX() + 0.5;
        double y = worldPosition.getY() + 1.1;
        double z = worldPosition.getZ() + 0.5;
        ItemEntity itemEntity = new ItemEntity(level, x, y, z, pill);
        itemEntity.setPickUpDelay(10);
        level.addFreshEntity(itemEntity);

        // Clear all ingredient slots
        for (int i = 0; i < SLOT_COUNT; i++) ingredients.setItem(i, ItemStack.EMPTY);
        setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        for (int i = 0; i < SLOT_COUNT; i++) {
            ItemStack stack = ingredients.getItem(i);
            if (!stack.isEmpty()) {
                tag.put("Ingredient" + i, stack.save(new CompoundTag()));
            }
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        for (int i = 0; i < SLOT_COUNT; i++) {
            if (tag.contains("Ingredient" + i)) {
                ingredients.setItem(i, ItemStack.of(tag.getCompound("Ingredient" + i)));
            }
        }
    }
}
