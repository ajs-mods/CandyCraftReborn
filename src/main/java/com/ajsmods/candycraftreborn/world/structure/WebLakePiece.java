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
 * Web Lake (Cotton Candy Spider Nest) — underground blob up to 16×8×16 filled with webs and a spawner.
 */
public class WebLakePiece extends StructurePiece {
    private final int xStart, yStart, zStart;
    private final int sizeX, sizeY, sizeZ;

    public WebLakePiece(int genDepth, int x, int y, int z, RandomSource random) {
        this(genDepth, x, y, z, 8 + random.nextInt(9), 4 + random.nextInt(5), 8 + random.nextInt(9));
    }

    public WebLakePiece(int genDepth, int x, int y, int z, int sx, int sy, int sz) {
        super(ModStructures.WEB_LAKE_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + sx - 1, y + sy - 1, z + sz - 1));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
        this.sizeX = sx;
        this.sizeY = sy;
        this.sizeZ = sz;
    }

    public WebLakePiece(CompoundTag tag) {
        super(ModStructures.WEB_LAKE_PIECE.get(), tag);
        this.xStart = tag.getInt("xS");
        this.yStart = tag.getInt("yS");
        this.zStart = tag.getInt("zS");
        this.sizeX = tag.getInt("sX");
        this.sizeY = tag.getInt("sY");
        this.sizeZ = tag.getInt("sZ");
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext ctx, CompoundTag tag) {
        tag.putInt("xS", xStart);
        tag.putInt("yS", yStart);
        tag.putInt("zS", zStart);
        tag.putInt("sX", sizeX);
        tag.putInt("sY", sizeY);
        tag.putInt("sZ", sizeZ);
    }

    @Override
    public void postProcess(WorldGenLevel level, StructureManager mgr, ChunkGenerator gen,
                            RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
        BlockState web = ModBlocks.COTTON_CANDY_WEB.get().defaultBlockState();
        int sx = xStart, sy = yStart, sz = zStart;

        // Ellipsoid fill
        float rx = sizeX / 2.0f, ry = sizeY / 2.0f, rz = sizeZ / 2.0f;
        float cx = sx + rx, cy = sy + ry, cz = sz + rz;

        for (int dx = 0; dx < sizeX; dx++) {
            for (int dy = 0; dy < sizeY; dy++) {
                for (int dz = 0; dz < sizeZ; dz++) {
                    float nx = (sx + dx - cx) / rx;
                    float ny = (sy + dy - cy) / ry;
                    float nz = (sz + dz - cz) / rz;
                    if (nx * nx + ny * ny + nz * nz <= 1.0f) {
                        place(level, web, sx + dx, sy + dy, sz + dz, chunkBox);
                    }
                }
            }
        }

        // Spawner in center
        BlockPos spawnerPos = new BlockPos((int) cx, (int) cy, (int) cz);
        if (chunkBox.isInside(spawnerPos)) {
            level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
            var be = level.getBlockEntity(spawnerPos);
            if (be instanceof SpawnerBlockEntity spawner) {
                spawner.getSpawner().setEntityId(ModEntities.COTTON_CANDY_SPIDER.get(), level.getLevel(), random, spawnerPos);
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
