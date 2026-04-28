package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class WebLakeStructure extends Structure {
    public static final Codec<WebLakeStructure> CODEC = simpleCodec(WebLakeStructure::new);

    public WebLakeStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (StructurePiecesBuilder builder) -> {
            int x = context.chunkPos().getMinBlockX();
            int z = context.chunkPos().getMinBlockZ();
            int y = 10 + context.random().nextInt(30);
            builder.addPiece(new WebLakePiece(0, x, y, z, context.random()));
        });
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.WEB_LAKE_TYPE.get();
    }
}
