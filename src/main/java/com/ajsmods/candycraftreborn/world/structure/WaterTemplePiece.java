package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

/**
 * Water Temple — 9×5×9 underwater octagonal structure.
 */
public class WaterTemplePiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public WaterTemplePiece(int genDepth, int x, int y, int z) {
        super(ModStructures.WATER_TEMPLE_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 8, y + 4, z + 8));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public WaterTemplePiece(CompoundTag tag) {
        super(ModStructures.WATER_TEMPLE_PIECE.get(), tag);
        this.xStart = tag.getInt("xS");
        this.yStart = tag.getInt("yS");
        this.zStart = tag.getInt("zS");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tag) {
        tag.putInt("xS", xStart);
        tag.putInt("yS", yStart);
        tag.putInt("zS", zStart);
    }

    /** Check if position is inside octagonal shape (9×9 with corners cut). */
    private boolean isOctagonal(int dx, int dz) {
        // Cut 2-block corners from a 9×9 square
        int cut = 2;
        if (dx + dz < cut) return false;
        if (dx + (8 - dz) < cut) return false;
        if ((8 - dx) + dz < cut) return false;
        if ((8 - dx) + (8 - dz) < cut) return false;
        return true;
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager mgr, ChunkGenerator gen,
                            RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        BlockState wall = ModBlocks.CHOCOLATE_STONE.get().defaultBlockState();
        BlockState cobble = ModBlocks.CHOCOLATE_COBBLESTONE.get().defaultBlockState();
        BlockState glass = ModBlocks.CARAMEL_GLASS_1.get().defaultBlockState();
        BlockState lamp = ModBlocks.HONEY_LAMP.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        int sx = xStart, sy = yStart, sz = zStart;

        for (int dx = 0; dx < 9; dx++) {
            for (int dz = 0; dz < 9; dz++) {
                if (!isOctagonal(dx, dz)) continue;

                for (int dy = 0; dy < 5; dy++) {
                    boolean isFloor = dy == 0;
                    boolean isCeiling = dy == 4;
                    boolean isEdge = dx == 0 || dx == 8 || dz == 0 || dz == 8
                            || !isOctagonal(dx - 1, dz) || !isOctagonal(dx + 1, dz)
                            || !isOctagonal(dx, dz - 1) || !isOctagonal(dx, dz + 1);

                    if (isFloor || isCeiling) {
                        place(level, cobble, sx + dx, sy + dy, sz + dz, chunkBox);
                    } else if (isEdge) {
                        // Windows at mid height
                        if (dy == 2 && (dx == 4 || dz == 4)) {
                            place(level, glass, sx + dx, sy + dy, sz + dz, chunkBox);
                        } else {
                            place(level, wall, sx + dx, sy + dy, sz + dz, chunkBox);
                        }
                    } else {
                        place(level, air, sx + dx, sy + dy, sz + dz, chunkBox);
                    }
                }
            }
        }

        // Honey lamps at corners inside
        place(level, lamp, sx + 2, sy + 3, sz + 2, chunkBox);
        place(level, lamp, sx + 6, sy + 3, sz + 2, chunkBox);
        place(level, lamp, sx + 2, sy + 3, sz + 6, chunkBox);
        place(level, lamp, sx + 6, sy + 3, sz + 6, chunkBox);

        // Chest
        BlockPos chestPos = new BlockPos(sx + 4, sy + 1, sz + 4);
        if (chunkBox.isInside(chestPos)) {
            level.setBlock(chestPos, ModBlocks.MARSHMALLOW_CHEST.get().defaultBlockState(), 2);
            var be = level.getBlockEntity(chestPos);
            if (be instanceof net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity rce) {
                rce.setLootTable(new ResourceLocation("candycraftreborn", "chests/water_temple_chest"), random.nextLong());
            }
        }
    }

    private void place(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
        BlockPos pos = new BlockPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(pos, state, 2);
        }
    }
}
