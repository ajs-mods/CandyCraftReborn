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
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

/**
 * Floating Island — large ellipsoid sky structure with surface decorations.
 */
public class FloatingIslandPiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public FloatingIslandPiece(int genDepth, int x, int y, int z) {
        super(ModStructures.FLOATING_ISLAND_PIECE.get(), genDepth,
                new BoundingBox(x - 15, y - 8, z - 15, x + 15, y + 12, z + 15));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public FloatingIslandPiece(CompoundTag tag) {
        super(ModStructures.FLOATING_ISLAND_PIECE.get(), tag);
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
        // Ellipsoid dimensions (randomized per island)
        int radiusX = 10 + random.nextInt(6); // 10-15
        int radiusZ = 10 + random.nextInt(6); // 10-15
        int radiusY = 4 + random.nextInt(2);  // 4-5

        BlockState pudding = ModBlocks.PUDDING.get().defaultBlockState();
        BlockState flour = ModBlocks.FLOUR.get().defaultBlockState();
        BlockState stone = ModBlocks.CHOCOLATE_STONE.get().defaultBlockState();

        // Generate ellipsoid island
        for (int dx = -radiusX; dx <= radiusX; dx++) {
            for (int dz = -radiusZ; dz <= radiusZ; dz++) {
                for (int dy = -radiusY; dy <= radiusY; dy++) {
                    double dist = (double)(dx * dx) / (radiusX * radiusX)
                                + (double)(dz * dz) / (radiusZ * radiusZ)
                                + (double)(dy * dy) / (radiusY * radiusY);
                    if (dist <= 1.0) {
                        BlockState block;
                        if (dy == radiusY || (dy >= radiusY - 1 && dist <= 0.85)) {
                            block = pudding; // Top surface
                        } else if (dy >= radiusY - 2) {
                            block = flour;   // Sub-surface
                        } else {
                            block = stone;   // Core
                        }
                        placeBlockIfClear(level, block, xStart + dx, yStart + dy, zStart + dz, chunkBox);
                    }
                }
            }
        }

        // Surface decorations on top of the island
        int surfaceY = yStart + radiusY + 1;
        int decorType = random.nextInt(3);

        // Place grass and saplings across the surface
        for (int dx = -radiusX + 2; dx <= radiusX - 2; dx++) {
            for (int dz = -radiusZ + 2; dz <= radiusZ - 2; dz++) {
                double surfDist = (double)(dx * dx) / (radiusX * radiusX)
                                + (double)(dz * dz) / (radiusZ * radiusZ);
                if (surfDist <= 0.7) {
                    // Random tall candy grass
                    if (random.nextInt(8) == 0) {
                        placeBlockIfClear(level, ModBlocks.TALL_CANDY_GRASS.get().defaultBlockState(),
                                xStart + dx, surfaceY, zStart + dz, chunkBox);
                    }
                }
            }
        }

        // Type-specific decorations
        if (decorType == 0 || decorType == 1) {
            // Small farm area near center
            for (int fx = -2; fx <= 2; fx++) {
                for (int fz = -2; fz <= 2; fz++) {
                    placeBlockIfClear(level, ModBlocks.CANDY_FARMLAND.get().defaultBlockState(),
                            xStart + fx, yStart + radiusY, zStart + fz, chunkBox);
                    placeBlockIfClear(level, ModBlocks.DRAGIBUS_CROPS.get().defaultBlockState()
                                    .setValue(CropBlock.AGE, 7),
                            xStart + fx, surfaceY, zStart + fz, chunkBox);
                }
            }
        }

        if (decorType == 1) {
            // Small 5x5 gingerbread house offset from center
            int hx = xStart + 5;
            int hz = zStart + 5;
            int hy = surfaceY;
            BlockState frame = ModBlocks.MARSHMALLOW_LOG.get().defaultBlockState();
            BlockState walls = ModBlocks.MARSHMALLOW_PLANKS_1.get().defaultBlockState();
            BlockState air = Blocks.AIR.defaultBlockState();

            // Floor
            for (int dx = 0; dx < 5; dx++)
                for (int dz = 0; dz < 5; dz++)
                    placeBlockIfClear(level, walls, hx + dx, hy, hz + dz, chunkBox);

            // Walls (3 high)
            for (int dy = 1; dy <= 3; dy++) {
                for (int dx = 0; dx < 5; dx++) {
                    for (int dz = 0; dz < 5; dz++) {
                        boolean edge = dx == 0 || dx == 4 || dz == 0 || dz == 4;
                        boolean corner = (dx == 0 || dx == 4) && (dz == 0 || dz == 4);
                        if (corner) {
                            placeBlockIfClear(level, frame, hx + dx, hy + dy, hz + dz, chunkBox);
                        } else if (edge) {
                            placeBlockIfClear(level, walls, hx + dx, hy + dy, hz + dz, chunkBox);
                        } else {
                            placeBlockIfClear(level, air, hx + dx, hy + dy, hz + dz, chunkBox);
                        }
                    }
                }
            }

            // Roof
            for (int dx = 0; dx < 5; dx++)
                for (int dz = 0; dz < 5; dz++)
                    placeBlockIfClear(level, frame, hx + dx, hy + 4, hz + dz, chunkBox);

            // Door opening
            placeBlockIfClear(level, ModBlocks.MARSHMALLOW_DOOR.get().defaultBlockState(),
                    hx + 2, hy + 1, hz, chunkBox);
            placeBlockIfClear(level, air, hx + 2, hy + 2, hz, chunkBox);
        }

        // Place 1-2 candy saplings
        int saplingCount = 1 + random.nextInt(2);
        for (int i = 0; i < saplingCount; i++) {
            int sx = xStart + random.nextInt(radiusX) - radiusX / 2;
            int sz = zStart + random.nextInt(radiusZ) - radiusZ / 2;
            placeBlockIfClear(level, ModBlocks.CANDY_SAPLING.get().defaultBlockState(),
                    sx, surfaceY, sz, chunkBox);
        }
    }

    private void placeBlockIfClear(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
        BlockPos pos = new BlockPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(pos, state, 2);
        }
    }
}
