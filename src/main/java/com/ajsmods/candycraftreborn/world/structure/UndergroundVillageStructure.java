package com.ajsmods.candycraftreborn.world.structure;

import com.ajsmods.candycraftreborn.registry.ModStructures;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;

import java.util.Optional;

public class UndergroundVillageStructure extends Structure {
    public static final Codec<UndergroundVillageStructure> CODEC = simpleCodec(UndergroundVillageStructure::new);

    public UndergroundVillageStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        int y = 16 + context.random().nextInt(16);
        BlockPos pos = new BlockPos(
                context.chunkPos().getMiddleBlockX(), y,
                context.chunkPos().getMiddleBlockZ());
        return Optional.of(new GenerationStub(pos, (StructurePiecesBuilder builder) -> {
            builder.addPiece(new UndergroundVillagePiece(0, pos.getX(), pos.getY(), pos.getZ()));
        }));
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.UNDERGROUND_VILLAGE_TYPE.get();
    }
}
