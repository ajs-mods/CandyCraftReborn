package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CandyEggBlockEntity extends BlockEntity {
    private int hatchTimer;
    private static final int HATCH_TIME = 24000; // one Minecraft day

    public CandyEggBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CANDY_EGG.get(), pos, state);
    }

    public void serverTick() {
        if (level == null || level.isClientSide) return;
        // Check for sugar_essence_flower ring in 5x5
        boolean hasFlowers = checkFlowerRing();
        if (hasFlowers) {
            hatchTimer++;
            if (hatchTimer >= HATCH_TIME) {
                // Hatching logic - remove egg block
                level.removeBlock(worldPosition, false);
            }
            setChanged();
        } else if (hatchTimer > 0) {
            hatchTimer = Math.max(0, hatchTimer - 1);
            setChanged();
        }
    }

    private boolean checkFlowerRing() {
        if (level == null) return false;
        int count = 0;
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                if (dx == 0 && dz == 0) continue;
                if (Math.abs(dx) == 2 || Math.abs(dz) == 2) {
                    BlockPos checkPos = worldPosition.offset(dx, 0, dz);
                    if (level.getBlockState(checkPos).is(ModBlocks.SUGAR_ESSENCE_FLOWER.get())) {
                        count++;
                    }
                }
            }
        }
        return count >= 4; // need at least 4 flowers
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("HatchTimer", hatchTimer);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        hatchTimer = tag.getInt("HatchTimer");
    }
}
