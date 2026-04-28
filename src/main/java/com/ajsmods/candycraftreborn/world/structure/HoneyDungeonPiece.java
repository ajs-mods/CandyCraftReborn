package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;

/**
 * Honey Dungeon — 8×4×8 underground room with spawner and chest.
 */
public class HoneyDungeonPiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public HoneyDungeonPiece(int genDepth, int x, int y, int z) {
        super(ModStructures.HONEY_DUNGEON_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 7, y + 3, z + 7));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public HoneyDungeonPiece(CompoundTag tag) {
        super(ModStructures.HONEY_DUNGEON_PIECE.get(), tag);
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
        BlockState wall = ModBlocks.HONEY_BLOCK_CC.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();
        int sx = xStart, sy = yStart, sz = zStart;

        for (int dx = 0; dx < 8; dx++) {
            for (int dy = 0; dy < 4; dy++) {
                for (int dz = 0; dz < 8; dz++) {
                    boolean edge = dx == 0 || dx == 7 || dy == 0 || dy == 3 || dz == 0 || dz == 7;
                    place(level, edge ? wall : air, sx + dx, sy + dy, sz + dz, chunkBox);
                }
            }
        }

        // Spawner in center
        BlockPos spawnerPos = new BlockPos(sx + 3, sy + 1, sz + 3);
        if (chunkBox.isInside(spawnerPos)) {
            level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
            var be = level.getBlockEntity(spawnerPos);
            if (be instanceof SpawnerBlockEntity spawner) {
                spawner.getSpawner().setEntityId(ModEntities.CANDY_BEE.get(), level.getLevel(), random, spawnerPos);
            }
        }

        // Chest
        BlockPos chestPos = new BlockPos(sx + 5, sy + 1, sz + 5);
        if (chunkBox.isInside(chestPos)) {
            level.setBlock(chestPos, ModBlocks.MARSHMALLOW_CHEST.get().defaultBlockState(), 2);
            var be = level.getBlockEntity(chestPos);
            if (be instanceof net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity rce) {
                rce.setLootTable(new ResourceLocation("candycraftreborn", "chests/honey_dungeon_chest"), random.nextLong());
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
