package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class WaterTempleStructure extends Structure {
    public static final Codec<WaterTempleStructure> CODEC = simpleCodec(WaterTempleStructure::new);

    public WaterTempleStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.OCEAN_FLOOR_WG, (StructurePiecesBuilder builder) -> {
            int x = context.chunkPos().getMinBlockX();
            int z = context.chunkPos().getMinBlockZ();
            int y = context.chunkGenerator().getFirstOccupiedHeight(x, z,
                    Heightmap.Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
            builder.addPiece(new WaterTemplePiece(0, x, y, z));
        });
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.WATER_TEMPLE_TYPE.get();
    }
}
