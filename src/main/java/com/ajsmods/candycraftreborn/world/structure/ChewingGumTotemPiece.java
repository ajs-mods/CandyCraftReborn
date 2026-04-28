package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

/**
 * Chewing Gum Totem — 9×8×9 elevated platform on pillars with spawner underneath.
 */
public class ChewingGumTotemPiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public ChewingGumTotemPiece(int genDepth, int x, int y, int z) {
        super(ModStructures.CHEWING_GUM_TOTEM_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 8, y + 7, z + 8));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public ChewingGumTotemPiece(CompoundTag tag) {
        super(ModStructures.CHEWING_GUM_TOTEM_PIECE.get(), tag);
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
        BlockState gum = ModBlocks.CHEWING_GUM_BLOCK.get().defaultBlockState();
        int sx = xStart, sy = yStart, sz = zStart;

        // 4 corner pillars (height 5)
        int[][] pillars = {{0, 0}, {8, 0}, {0, 8}, {8, 8}};
        for (int[] p : pillars) {
            for (int dy = 0; dy < 5; dy++) {
                place(level, gum, sx + p[0], sy + dy, sz + p[1], chunkBox);
            }
        }

        // Platform at y+5 (9×9)
        for (int dx = 0; dx < 9; dx++)
            for (int dz = 0; dz < 9; dz++)
                place(level, gum, sx + dx, sy + 5, sz + dz, chunkBox);

        // Fence/rim at y+6
        for (int dx = 0; dx < 9; dx++) {
            place(level, gum, sx + dx, sy + 6, sz, chunkBox);
            place(level, gum, sx + dx, sy + 6, sz + 8, chunkBox);
        }
        for (int dz = 1; dz < 8; dz++) {
            place(level, gum, sx, sy + 6, sz + dz, chunkBox);
            place(level, gum, sx + 8, sy + 6, sz + dz, chunkBox);
        }

        // Totem pillar in center (y+6 to y+7)
        place(level, gum, sx + 4, sy + 6, sz + 4, chunkBox);
        place(level, gum, sx + 4, sy + 7, sz + 4, chunkBox);

        // Spawner underneath platform
        BlockPos spawnerPos = new BlockPos(sx + 4, sy + 1, sz + 4);
        if (chunkBox.isInside(spawnerPos)) {
            level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
            var be = level.getBlockEntity(spawnerPos);
            if (be instanceof SpawnerBlockEntity spawner) {
                spawner.getSpawner().setEntityId(ModEntities.BEETLE.get(), level.getLevel(), random, spawnerPos);
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
