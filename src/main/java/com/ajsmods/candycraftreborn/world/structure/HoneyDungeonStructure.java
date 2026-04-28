package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class HoneyDungeonStructure extends Structure {
    public static final Codec<HoneyDungeonStructure> CODEC = simpleCodec(HoneyDungeonStructure::new);

    public HoneyDungeonStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (StructurePiecesBuilder builder) -> {
            int x = context.chunkPos().getMinBlockX();
            int z = context.chunkPos().getMinBlockZ();
            // Underground: place between y=10 and y=40
            int y = 10 + context.random().nextInt(30);
            builder.addPiece(new HoneyDungeonPiece(0, x, y, z));
        });
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.HONEY_DUNGEON_TYPE.get();
    }
}
