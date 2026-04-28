package com.ajsmods.candycraftreborn.blockentity;

import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class TeleporterBlockEntity extends BlockEntity {
    private BlockPos target;
    @Nullable
    private ResourceKey<Level> targetDimension;

    public TeleporterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TELEPORTER.get(), pos, state);
    }

    public boolean hasTarget() {
        return target != null;
    }

    public BlockPos getTarget() {
        return target;
    }

    public void setTarget(BlockPos target) {
        this.target = target;
        setChanged();
    }

    @Nullable
    public ResourceKey<Level> getTargetDimension() {
        return targetDimension;
    }

    public void setTargetDimension(@Nullable ResourceKey<Level> targetDimension) {
        this.targetDimension = targetDimension;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (target != null) {
            tag.putInt("TargetX", target.getX());
            tag.putInt("TargetY", target.getY());
            tag.putInt("TargetZ", target.getZ());
        }
        if (targetDimension != null) {
            tag.putString("TargetDimension", targetDimension.location().toString());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("TargetX")) {
            target = new BlockPos(tag.getInt("TargetX"), tag.getInt("TargetY"), tag.getInt("TargetZ"));
        }
        if (tag.contains("TargetDimension")) {
            targetDimension = ResourceKey.create(Registries.DIMENSION,
                    new ResourceLocation(tag.getString("TargetDimension")));
        }
    }
}
