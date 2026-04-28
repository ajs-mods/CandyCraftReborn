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
 * Ice Tower — 7×6×7 surface tower made of ice cream blocks with chest at top.
 */
public class IceTowerPiece extends StructurePiece {
    private final int xStart, yStart, zStart;

    public IceTowerPiece(int genDepth, int x, int y, int z) {
        super(ModStructures.ICE_TOWER_PIECE.get(), genDepth,
                new BoundingBox(x, y, z, x + 6, y + 11, z + 6));
        this.xStart = x;
        this.yStart = y;
        this.zStart = z;
    }

    public IceTowerPiece(CompoundTag tag) {
        super(ModStructures.ICE_TOWER_PIECE.get(), tag);
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
        BlockState[] iceCreams = {
                ModBlocks.ICE_CREAM_VANILLA.get().defaultBlockState(),
                ModBlocks.ICE_CREAM_STRAWBERRY.get().defaultBlockState(),
                ModBlocks.ICE_CREAM_CHOCOLATE.get().defaultBlockState(),
                ModBlocks.ICE_CREAM_MINT.get().defaultBlockState()
        };
        BlockState air = Blocks.AIR.defaultBlockState();
        int sx = xStart, sy = yStart, sz = zStart;

        // Two-storey tower: base 7×7, 6 blocks per storey = 12 high
        for (int storey = 0; storey < 2; storey++) {
            int baseY = sy + storey * 6;
            BlockState wallBlock = iceCreams[storey % iceCreams.length];
            BlockState altBlock = iceCreams[(storey + 1) % iceCreams.length];

            // Floor
            for (int dx = 0; dx < 7; dx++)
                for (int dz = 0; dz < 7; dz++)
                    place(level, altBlock, sx + dx, baseY, sz + dz, chunkBox);

            // Walls (5 layers high)
            for (int dy = 1; dy <= 5; dy++) {
                for (int dx = 0; dx < 7; dx++) {
                    for (int dz = 0; dz < 7; dz++) {
                        boolean edge = dx == 0 || dx == 6 || dz == 0 || dz == 6;
                        if (edge) {
                            place(level, wallBlock, sx + dx, baseY + dy, sz + dz, chunkBox);
                        } else {
                            place(level, air, sx + dx, baseY + dy, sz + dz, chunkBox);
                        }
                    }
                }
            }
        }

        // Door opening ground floor
        place(level, air, sx + 3, sy + 1, sz, chunkBox);
        place(level, air, sx + 3, sy + 2, sz, chunkBox);

        // Ladder inside (spiral stair simplified as ladder column)
        BlockState ladder = Blocks.LADDER.defaultBlockState();
        for (int dy = 1; dy <= 11; dy++) {
            place(level, ladder, sx + 1, sy + dy, sz + 1, chunkBox);
        }

        // Chest at top
        BlockPos chestPos = new BlockPos(sx + 3, sy + 7, sz + 3);
        if (chunkBox.isInside(chestPos)) {
            level.setBlock(chestPos, ModBlocks.MARSHMALLOW_CHEST.get().defaultBlockState(), 2);
            var be = level.getBlockEntity(chestPos);
            if (be instanceof net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity rce) {
                rce.setLootTable(new ResourceLocation("candycraftreborn", "chests/candy_house_chest"), random.nextLong());
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
