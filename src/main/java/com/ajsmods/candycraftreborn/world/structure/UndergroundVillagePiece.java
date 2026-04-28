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
 * Underground Gingerbread Village — massive 64×7×64 underground cavern village.
 */
public class UndergroundVillagePiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public UndergroundVillagePiece(int genDepth, int x, int y, int z) {
        super(ModStructures.UNDERGROUND_VILLAGE_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 63, y + 6, z + 63));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public UndergroundVillagePiece(CompoundTag tag) {
        super(ModStructures.UNDERGROUND_VILLAGE_PIECE.get(), tag);
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
        BlockState cobble = ModBlocks.CHOCOLATE_COBBLESTONE.get().defaultBlockState();
        BlockState pudding = ModBlocks.PUDDING.get().defaultBlockState();
        BlockState planks = ModBlocks.MARSHMALLOW_PLANKS_1.get().defaultBlockState();
        BlockState stone = ModBlocks.CHOCOLATE_STONE.get().defaultBlockState();
        BlockState lamp = ModBlocks.HONEY_LAMP.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState log = ModBlocks.MARSHMALLOW_LOG.get().defaultBlockState();
        BlockState fence = ModBlocks.CANDY_CANE_FENCE.get().defaultBlockState();
        BlockState grass = ModBlocks.TALL_CANDY_GRASS.get().defaultBlockState();
        BlockState workbench = ModBlocks.MARSHMALLOW_WORKBENCH.get().defaultBlockState();

        int sx = xStart, sy = yStart, sz = zStart;

        // Clear interior and build shell
        for (int dx = 0; dx < 64; dx++) {
            for (int dz = 0; dz < 64; dz++) {
                for (int dy = 0; dy <= 6; dy++) {
                    boolean isWall = dx == 0 || dx == 63 || dz == 0 || dz == 63;
                    boolean isFloor = dy == 0;
                    boolean isCeiling = dy == 6;

                    if (isFloor) {
                        // Floor: marshmallow_planks border, pudding interior
                        if (dx <= 1 || dx >= 62 || dz <= 1 || dz >= 62) {
                            placeBlockIfClear(level, planks, sx + dx, sy + dy, sz + dz, chunkBox);
                        } else {
                            placeBlockIfClear(level, pudding, sx + dx, sy + dy, sz + dz, chunkBox);
                        }
                    } else if (isCeiling) {
                        // Ceiling: checkerboard cobblestone + honey_lamp
                        if ((dx + dz) % 6 == 0) {
                            placeBlockIfClear(level, lamp, sx + dx, sy + dy, sz + dz, chunkBox);
                        } else {
                            placeBlockIfClear(level, cobble, sx + dx, sy + dy, sz + dz, chunkBox);
                        }
                    } else if (isWall) {
                        placeBlockIfClear(level, cobble, sx + dx, sy + dy, sz + dz, chunkBox);
                    } else {
                        placeBlockIfClear(level, air, sx + dx, sy + dy, sz + dz, chunkBox);
                    }
                }
            }
        }

        // Roads: 4x4 grid with 8-block spacing, using chocolate_stone paths
        for (int grid = 0; grid < 8; grid++) {
            int roadPos = 2 + grid * 8;
            if (roadPos >= 63) continue;
            for (int i = 1; i < 63; i++) {
                // Horizontal roads
                placeBlockIfClear(level, stone, sx + i, sy, sz + roadPos, chunkBox);
                placeBlockIfClear(level, stone, sx + i, sy, sz + roadPos + 1, chunkBox);
                // Vertical roads
                placeBlockIfClear(level, stone, sx + roadPos, sy, sz + i, chunkBox);
                placeBlockIfClear(level, stone, sx + roadPos + 1, sy, sz + i, chunkBox);
                // Fence along paths (every other block)
                if (i % 3 == 0 && roadPos + 2 < 63) {
                    placeBlockIfClear(level, fence, sx + i, sy + 1, sz + roadPos + 2, chunkBox);
                    placeBlockIfClear(level, fence, sx + roadPos + 2, sy + 1, sz + i, chunkBox);
                }
            }
        }

        // Place ~12-16 houses in grid cells
        int houseCount = 12 + random.nextInt(5);
        for (int h = 0; h < houseCount; h++) {
            int gridX = random.nextInt(7);
            int gridZ = random.nextInt(7);
            int hx = sx + 4 + gridX * 8;
            int hz = sz + 4 + gridZ * 8;
            int hy = sy + 1;

            // Ensure house fits (5x5x4 within bounds)
            if (hx + 4 >= sx + 63 || hz + 4 >= sz + 63) continue;

            buildHouse(level, random, hx, hy, hz, chunkBox);
        }

        // Place workbenches at some intersections
        for (int gx = 0; gx < 7; gx += 2) {
            for (int gz = 0; gz < 7; gz += 2) {
                if (random.nextInt(3) == 0) {
                    int wx = sx + 3 + gx * 8;
                    int wz = sz + 3 + gz * 8;
                    placeBlockIfClear(level, workbench, wx, sy + 1, wz, chunkBox);
                }
            }
        }

        // Scatter tall candy grass
        for (int i = 0; i < 30; i++) {
            int gx = sx + 3 + random.nextInt(58);
            int gz = sz + 3 + random.nextInt(58);
            placeBlockIfClear(level, grass, gx, sy + 1, gz, chunkBox);
        }
    }

    private void buildHouse(WorldGenLevel level, RandomSource random, int hx, int hy, int hz, BoundingBox box) {
        BlockState log = ModBlocks.MARSHMALLOW_LOG.get().defaultBlockState();
        BlockState walls = ModBlocks.MARSHMALLOW_PLANKS_1.get().defaultBlockState();
        BlockState floor = ModBlocks.CHOCOLATE_STONE.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();

        BlockState[] windowTypes = {
                ModBlocks.CARAMEL_PANE_0.get().defaultBlockState(),
                ModBlocks.CARAMEL_PANE_1.get().defaultBlockState(),
                ModBlocks.CARAMEL_PANE_2.get().defaultBlockState()
        };
        BlockState window = windowTypes[random.nextInt(3)];

        // Floor
        for (int dx = 0; dx < 5; dx++)
            for (int dz = 0; dz < 5; dz++)
                placeBlockIfClear(level, floor, hx + dx, hy, hz + dz, box);

        // Walls (3 high)
        for (int dy = 1; dy <= 3; dy++) {
            for (int dx = 0; dx < 5; dx++) {
                for (int dz = 0; dz < 5; dz++) {
                    boolean edge = dx == 0 || dx == 4 || dz == 0 || dz == 4;
                    boolean corner = (dx == 0 || dx == 4) && (dz == 0 || dz == 4);
                    if (corner) {
                        placeBlockIfClear(level, log, hx + dx, hy + dy, hz + dz, box);
                    } else if (edge) {
                        // Window at y=2 in middle of wall
                        boolean midX = dx == 2;
                        boolean midZ = dz == 2;
                        if (dy == 2 && (midX || midZ)) {
                            placeBlockIfClear(level, window, hx + dx, hy + dy, hz + dz, box);
                        } else {
                            placeBlockIfClear(level, walls, hx + dx, hy + dy, hz + dz, box);
                        }
                    } else {
                        placeBlockIfClear(level, air, hx + dx, hy + dy, hz + dz, box);
                    }
                }
            }
        }

        // Roof (horizontal marshmallow_log)
        for (int dx = 0; dx < 5; dx++)
            for (int dz = 0; dz < 5; dz++)
                placeBlockIfClear(level, log, hx + dx, hy + 4, hz + dz, box);

        // Door opening on one side
        int doorSide = random.nextInt(4);
        switch (doorSide) {
            case 0 -> { placeBlockIfClear(level, air, hx + 2, hy + 1, hz, box);
                         placeBlockIfClear(level, air, hx + 2, hy + 2, hz, box); }
            case 1 -> { placeBlockIfClear(level, air, hx + 2, hy + 1, hz + 4, box);
                         placeBlockIfClear(level, air, hx + 2, hy + 2, hz + 4, box); }
            case 2 -> { placeBlockIfClear(level, air, hx, hy + 1, hz + 2, box);
                         placeBlockIfClear(level, air, hx, hy + 2, hz + 2, box); }
            case 3 -> { placeBlockIfClear(level, air, hx + 4, hy + 1, hz + 2, box);
                         placeBlockIfClear(level, air, hx + 4, hy + 2, hz + 2, box); }
        }
    }

    private void placeBlockIfClear(WorldGenLevel level, BlockState state, int x, int y, int z, BoundingBox box) {
        BlockPos pos = new BlockPos(x, y, z);
        if (box.isInside(pos)) {
            level.setBlock(pos, state, 2);
        }
    }
}
