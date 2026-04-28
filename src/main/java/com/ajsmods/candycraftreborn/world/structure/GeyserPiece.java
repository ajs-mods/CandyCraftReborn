package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
 * Geyser — 3×4×3 water column structure on ocean floor.
 */
public class GeyserPiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public GeyserPiece(int genDepth, int x, int y, int z) {
        super(ModStructures.GEYSER_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 2, y + 3, z + 2));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public GeyserPiece(CompoundTag tag) {
        super(ModStructures.GEYSER_PIECE.get(), tag);
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

    @Override
    public void postProcess(WorldGenLevel level, StructureManager mgr, ChunkGenerator gen,
                            RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        BlockState stone = ModBlocks.CHOCOLATE_STONE.get().defaultBlockState();
        BlockState water = Blocks.WATER.defaultBlockState();
        int sx = xStart, sy = yStart, sz = zStart;

        // Base ring (3×3)
        for (int dx = 0; dx < 3; dx++)
            for (int dz = 0; dz < 3; dz++)
                place(level, stone, sx + dx, sy, sz + dz, chunkBox);

        // Water column in center, 3 blocks high
        for (int dy = 1; dy <= 3; dy++) {
            place(level, water, sx + 1, sy + dy, sz + 1, chunkBox);
        }

        // Stone rim at y+1
        place(level, stone, sx, sy + 1, sz + 1, chunkBox);
        place(level, stone, sx + 2, sy + 1, sz + 1, chunkBox);
        place(level, stone, sx + 1, sy + 1, sz, chunkBox);
        place(level, stone, sx + 1, sy + 1, sz + 2, chunkBox);
    }

    private void place(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
        BlockPos pos = new BlockPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(pos, state, 2);
        }
    }
}
