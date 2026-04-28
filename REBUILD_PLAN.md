# CandyCraftReborn Rebuild Plan

Last updated: 2026-04-28

## Goal
Rebuild the legacy CandyCraft mod (1.12.2) into CandyCraftReborn (Forge 1.20.1) with modern APIs, stable multiplayer behavior, and clean project structure.

## Success Criteria
- Mod loads in client and dedicated server with no startup errors.
- Core content parity reached (blocks, items, entities, machines, world systems).
- Stable release candidate published with migration changelog.

## Legacy Source of Truth
- Legacy codebase: ../CandyCraft
- New codebase: .

## Phased Roadmap

### Phase 1: Foundation (Week 1)
Status: DONE

Scope:
- Finalize package/namespace and registries.
- Build a migration map from legacy systems to 1.20.1 systems.
- Create a vertical slice proving end-to-end content flow.

Tasks:
- [x] Rename and initialize CandyCraftReborn repository.
- [x] Align mod ID/package to candycraftreborn and com.ajsmods.candycraftreborn.
- [x] Create clean starter registries (ModBlocks, ModItems).
- [x] Add first vertical slice content (3 blocks, 3 items, 1 entity, 1 recipe).
- [x] Create migration matrix file-by-file from legacy package.

### Phase 2: Core Content Migration (Weeks 2-4)
Status: DONE

Scope:
- Port blocks/items and recipe systems.
- Replace old registration patterns with DeferredRegister/data-driven assets.

Tasks:
- [x] Migrate block registrations from legacy CCBlocks (~125 blocks total).
- [x] Migrate item registrations from legacy CCItems (~100+ items).
- [x] Port armor/tool tier logic to modern 1.20.1 APIs (20 tools, 6 weapons, 15 armor sets).
- [x] Convert hardcoded recipes to JSON recipes (121 recipes).
- [x] Add 13 missing functional blocks: licorice furnace, marshmallow chest, cotton candy bed, marshmallow workbench, cotton candy jukebox, sugar factory, advanced sugar factory, teleporter, dragon egg, beetle egg, dragibus crops, candy farmland, grenadine liquid (v1.1.0).
- [x] Add block entities, menus, and registries for functional blocks (ModBlockEntities, ModMenus).

### Phase 3: Gameplay Systems (Weeks 4-7)
Status: DONE

Scope:
- Port entities, fluids, block entities, menus, screens, and relevant networking.

Tasks:
- [x] Migrate 25 entity registrations and 22 spawn eggs.
- [x] Port AI/attributes for all mobs.
- [x] Add entity renderers for all 25 entities — 16 custom renderer classes, 22 entity textures (v1.2.0).
- [x] Implement mount system: Nessie (water), Dragon (flying), King Beetle (ground) with PowerMount interface (v1.4.0).
- [x] Implement passive mob interactions: wolf milking/taming, penguin ice cream production, candy pig steering, waffle sheep nugget drops, creeper mega-explosion (v1.4.0).
- [x] Implement GingerBreadMan trading with 4 professions (v1.4.0).
- [x] Implement hostile mob AI: beetle enrage, suguard water weakness, mage suguard minion spawns, bee poison, mermaid ranged attacks (v1.4.0).
- [x] Implement 5 boss fights: Jelly Queen, PEZ Jelly, King Slime, Boss Suguard, Boss Beetle — multi-phase AI, boss bars, loot tables (v1.5.0).
- [x] Port machines and block entities with MenuType (sugar factory menu, alchemy table).
- [ ] Port fluids (Grenadine, Caramel) to ForgeFlowingFluid — placeholder liquid block exists, full fluid flow not yet implemented.
- [ ] Add client screens/GUIs for sugar factory and alchemy table.

### Phase 4: World and Dimension Systems (Weeks 7-10)
Status: DONE

Scope:
- Rebuild worldgen/biomes/dimensions using modern 1.20.1 data-driven systems.

Tasks:
- [x] Port 9 custom biomes (multi_noise biome source).
- [x] Rebuild Candy dimension registration (dimension + dimension_type + noise_settings).
- [x] Implement 7 ore types, 3 tree types, 4 vegetation features (all data-driven JSON).
- [x] Fix noise_settings density functions (replaced invalid `minecraft:zero` refs with `0.0`).
- [x] Implement 9 world gen structures: candy house, honey dungeon, ice tower, water temple, chewing gum totem, web lake, geyser, floating island, underground gingerbread village (v1.3.0).
- [x] Implement 2 key-activated instanced dungeons: Slime Dungeon (8-room linear) + Suguard Dungeon (4-wing hub-and-spoke) with cross-dimension teleportation (v1.6.0).
- [x] Validate generation stability — fixed registry crash, tested world creation.

### Phase 5: Parity and Polish (Weeks 10-12)
Status: MOSTLY DONE

Scope:
- Asset completion, balancing, enchantments, advancements, user-facing polish.

Tasks:
- [x] Port block models/textures (254 PNGs, 785+ JSON models/blockstates).
- [x] Port item textures (113 legacy item textures migrated).
- [x] Port entity textures (22 placeholder textures created).
- [x] Port lang file (en_us.json with all blocks, items, entities, containers).
- [x] Port sound events and OGG files (6 sounds: 2 nessie + 4 music discs) (v1.6.1).
- [x] Add 12 block tags, 5 advancements.
- [ ] Port enchantments (Devourer, Honey Glue, etc.) — not yet implemented.
- [ ] Gameplay tuning and balance pass — needs playtesting.
- [ ] Remove debug placeholders and temp content.
- [ ] Replace placeholder entity textures with proper pixel art.
- [ ] Replace placeholder sound tones with real music/SFX.

### Phase 6: Stabilization and Release (Week 12+)
Status: IN PROGRESS

Scope:
- Hardening, compatibility testing, release process.

Tasks:
- [x] Fix JAR packaging (stale examplemod JAR replaced with proper build).
- [x] Fix registry crash on Singleplayer click (v1.0.1).
- [x] Fix marshmallow_planks recipe output.
- [x] Publish releases v1.0.0 through v1.6.1 on GitHub.
- [ ] Multiplayer soak tests and regression checks.
- [ ] Performance and memory profiling pass.
- [ ] Crash triage and bugfix sprint.
- [ ] Tag final release candidate.

## Release History

| Version | Date | Content |
|---------|------|---------|
| v1.0.0 | 2026-04-27 | Foundation — 112 blocks, 100+ items, 25 entities, worldgen, 121 recipes |
| v1.0.1 | 2026-04-27 | Registry crash fix (minecraft:zero → 0.0), marshmallow_planks recipe fix |
| v1.1.0 | 2026-04-27 | +13 functional blocks (furnace, chest, bed, factories, eggs, crops, farmland) |
| v1.2.0 | 2026-04-27 | 25 entity renderers + 22 entity textures |
| v1.3.0 | 2026-04-28 | 9 world gen structures |
| v1.4.0 | 2026-04-28 | Entity mechanics (3 mounts, trading, 5 passive interactions, 5 hostile AI) |
| v1.5.0 | 2026-04-28 | 5 boss fights with multi-phase AI |
| v1.6.0 | 2026-04-28 | 2 instanced dungeons (Slime + Suguard, key-activated) |
| v1.6.1 | 2026-04-28 | 6 sound files |

## Content Inventory

| Category | Count | Status |
|----------|-------|--------|
| Blocks | ~125 | Complete |
| Items | ~100+ | Complete |
| Entities (mobs) | 25 | Complete |
| Boss entities | 5 | Complete |
| Spawn eggs | 27 | Complete |
| Recipes | 121+ | Complete |
| World gen structures | 9 | Complete |
| Instanced dungeons | 2 | Complete |
| Biomes | 9 | Complete |
| Block entities | 5 types | Complete |
| Menu types | 2 | Complete |
| Sound events | 6 | Complete |
| Block tags | 12 | Complete |
| Advancements | 5 | Complete |
| Java classes | ~120+ | Complete |
| JSON files | 880+ | Complete |
| Textures (total) | 280+ | Complete (placeholders for entities) |

## Remaining Work (Polish)

### Not Yet Implemented
1. Full fluid system (Grenadine/Caramel ForgeFlowingFluid with flow mechanics)
2. Custom enchantments (Devourer, Honey Glue)
3. Client GUI screens for sugar factory and alchemy table
4. Proper entity pixel art (currently solid-color placeholders)
5. Real music/SFX for sound events (currently sine wave placeholders)

### Testing Needed
1. Multiplayer compatibility testing
2. Performance profiling (especially dungeon generation)
3. Balance pass on boss HP/damage/drops
4. Structure generation frequency tuning

## Migration Risks and Mitigation

### Risk: 1.12 worldgen architecture does not map directly to 1.20.1
Mitigation: RESOLVED — All worldgen is data-driven JSON with inlined density functions.

### Risk: Legacy proxy/event patterns break in modern Forge
Mitigation: RESOLVED — All sided behavior uses modern event subscriptions.

### Risk: Large asset volume slows engineering progress
Mitigation: RESOLVED — Functional placeholders used for entity textures and sounds; full art pass scheduled as polish.

## Change Log
- 2026-04-27: Initial rebuild plan created from legacy inventory and migration analysis.
- 2026-04-27: Phase 1 vertical slice implemented.
- 2026-04-27: Phase 2 core content migration completed (blocks, items, recipes).
- 2026-04-27: Phase 3 entities and Phase 4 worldgen completed.
- 2026-04-27: Phase 5 polish (textures, tags, advancements, recipes).
- 2026-04-27: v1.0.0-v1.2.0 released (crash fix, missing blocks, entity renderers).
- 2026-04-28: v1.3.0-v1.6.1 released (structures, entity mechanics, bosses, dungeons, sounds).
- 2026-04-28: Rebuild plan updated — Phases 1-4 DONE, Phase 5 mostly done, Phase 6 in progress.
