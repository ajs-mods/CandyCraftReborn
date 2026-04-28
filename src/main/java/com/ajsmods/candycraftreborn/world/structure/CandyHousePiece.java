package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.storage.loot.BuiltInLootTables;

/**
 * Candy House — 5×6×5 surface cottage built from candy blocks.
 */
public class CandyHousePiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public CandyHousePiece(int genDepth, int x, int y, int z) {
        super(ModStructures.CANDY_HOUSE_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 4, y + 5, z + 4));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public CandyHousePiece(CompoundTag tag) {
        super(ModStructures.CANDY_HOUSE_PIECE.get(), tag);
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
        BlockState wall = ModBlocks.CANDY_CANE_BLOCK.get().defaultBlockState();
        BlockState frame = ModBlocks.MARSHMALLOW_LOG.get().defaultBlockState();
        BlockState floor = ModBlocks.MARSHMALLOW_PLANKS_1.get().defaultBlockState();
        BlockState glass = ModBlocks.CARAMEL_GLASS_0.get().defaultBlockState();
        BlockState roof = ModBlocks.CANDY_CANE_BLOCK.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();

        int sx = xStart, sy = yStart, sz = zStart;

        // Floor (5×5)
        for (int dx = 0; dx < 5; dx++)
            for (int dz = 0; dz < 5; dz++)
                placeBlockIfClear(level, floor, sx + dx, sy, sz + dz, chunkBox);

        // Walls — 4 high, hollow
        for (int dy = 1; dy <= 4; dy++) {
            for (int dx = 0; dx < 5; dx++) {
                for (int dz = 0; dz < 5; dz++) {
                    boolean edge = dx == 0 || dx == 4 || dz == 0 || dz == 4;
                    boolean corner = (dx == 0 || dx == 4) && (dz == 0 || dz == 4);
                    if (corner) {
                        placeBlockIfClear(level, frame, sx + dx, sy + dy, sz + dz, chunkBox);
                    } else if (edge) {
                        // Windows at y=2,3 on middle of each wall
                        boolean midX = dx == 2;
                        boolean midZ = dz == 2;
                        if (dy >= 2 && dy <= 3 && (midX || midZ)) {
                            placeBlockIfClear(level, glass, sx + dx, sy + dy, sz + dz, chunkBox);
                        } else {
                            placeBlockIfClear(level, wall, sx + dx, sy + dy, sz + dz, chunkBox);
                        }
                    } else {
                        placeBlockIfClear(level, air, sx + dx, sy + dy, sz + dz, chunkBox);
                    }
                }
            }
        }

        // Roof (flat for simplicity)
        for (int dx = 0; dx < 5; dx++)
            for (int dz = 0; dz < 5; dz++)
                placeBlockIfClear(level, roof, sx + dx, sy + 5, sz + dz, chunkBox);

        // Door opening
        placeBlockIfClear(level, air, sx + 2, sy + 1, sz, chunkBox);
        placeBlockIfClear(level, air, sx + 2, sy + 2, sz, chunkBox);

        // Chest inside
        BlockPos chestPos = new BlockPos(sx + 1, sy + 1, sz + 3);
        if (chunkBox.isInside(chestPos)) {
            level.setBlock(chestPos, ModBlocks.MARSHMALLOW_CHEST.get().defaultBlockState(), 2);
            // Set loot table
            var be = level.getBlockEntity(chestPos);
            if (be instanceof net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity rce) {
                rce.setLootTable(new ResourceLocation("candycraftreborn", "chests/candy_house_chest"), random.nextLong());
            }
        }
    }

    private void placeBlockIfClear(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
        BlockPos pos = new BlockPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(pos, state, 2);
        }
    }
}
