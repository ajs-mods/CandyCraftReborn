package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.world.structure.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModStructures {

    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, CandyCraftMod.MODID);

    public static final DeferredRegister<StructurePieceType> PIECE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PIECE, CandyCraftMod.MODID);

    // ── Structure Types ──────────────────────────────────────────────────────
    public static final RegistryObject<StructureType<CandyHouseStructure>> CANDY_HOUSE_TYPE =
            STRUCTURE_TYPES.register("candy_house", () -> () -> CandyHouseStructure.CODEC);

    public static final RegistryObject<StructureType<HoneyDungeonStructure>> HONEY_DUNGEON_TYPE =
            STRUCTURE_TYPES.register("honey_dungeon", () -> () -> HoneyDungeonStructure.CODEC);

    public static final RegistryObject<StructureType<IceTowerStructure>> ICE_TOWER_TYPE =
            STRUCTURE_TYPES.register("ice_tower", () -> () -> IceTowerStructure.CODEC);

    public static final RegistryObject<StructureType<WaterTempleStructure>> WATER_TEMPLE_TYPE =
            STRUCTURE_TYPES.register("water_temple", () -> () -> WaterTempleStructure.CODEC);

    public static final RegistryObject<StructureType<ChewingGumTotemStructure>> CHEWING_GUM_TOTEM_TYPE =
            STRUCTURE_TYPES.register("chewing_gum_totem", () -> () -> ChewingGumTotemStructure.CODEC);

    public static final RegistryObject<StructureType<WebLakeStructure>> WEB_LAKE_TYPE =
            STRUCTURE_TYPES.register("web_lake", () -> () -> WebLakeStructure.CODEC);

    public static final RegistryObject<StructureType<GeyserStructure>> GEYSER_TYPE =
            STRUCTURE_TYPES.register("geyser", () -> () -> GeyserStructure.CODEC);

    public static final RegistryObject<StructureType<FloatingIslandStructure>> FLOATING_ISLAND_TYPE =
            STRUCTURE_TYPES.register("floating_island", () -> () -> FloatingIslandStructure.CODEC);

    public static final RegistryObject<StructureType<UndergroundVillageStructure>> UNDERGROUND_VILLAGE_TYPE =
            STRUCTURE_TYPES.register("underground_village", () -> () -> UndergroundVillageStructure.CODEC);

    // ── Piece Types ──────────────────────────────────────────────────────────
    public static final RegistryObject<StructurePieceType> CANDY_HOUSE_PIECE =
            PIECE_TYPES.register("candy_house", () -> (StructurePieceType.ContextlessType) CandyHousePiece::new);

    public static final RegistryObject<StructurePieceType> HONEY_DUNGEON_PIECE =
            PIECE_TYPES.register("honey_dungeon", () -> (StructurePieceType.ContextlessType) HoneyDungeonPiece::new);

    public static final RegistryObject<StructurePieceType> ICE_TOWER_PIECE =
            PIECE_TYPES.register("ice_tower", () -> (StructurePieceType.ContextlessType) IceTowerPiece::new);

    public static final RegistryObject<StructurePieceType> WATER_TEMPLE_PIECE =
            PIECE_TYPES.register("water_temple", () -> (StructurePieceType.ContextlessType) WaterTemplePiece::new);

    public static final RegistryObject<StructurePieceType> CHEWING_GUM_TOTEM_PIECE =
            PIECE_TYPES.register("chewing_gum_totem", () -> (StructurePieceType.ContextlessType) ChewingGumTotemPiece::new);

    public static final RegistryObject<StructurePieceType> WEB_LAKE_PIECE =
            PIECE_TYPES.register("web_lake", () -> (StructurePieceType.ContextlessType) WebLakePiece::new);

    public static final RegistryObject<StructurePieceType> GEYSER_PIECE =
            PIECE_TYPES.register("geyser", () -> (StructurePieceType.ContextlessType) GeyserPiece::new);

    public static final RegistryObject<StructurePieceType> FLOATING_ISLAND_PIECE =
            PIECE_TYPES.register("floating_island", () -> (StructurePieceType.ContextlessType) FloatingIslandPiece::new);

    public static final RegistryObject<StructurePieceType> UNDERGROUND_VILLAGE_PIECE =
            PIECE_TYPES.register("underground_village", () -> (StructurePieceType.ContextlessType) UndergroundVillagePiece::new);

    private ModStructures() {}

    public static void register(IEventBus eventBus) {
        STRUCTURE_TYPES.register(eventBus);
        PIECE_TYPES.register(eventBus);
    }
}
