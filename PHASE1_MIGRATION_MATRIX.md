# Phase 1 Migration Matrix

Last updated: 2026-04-27

This matrix maps core legacy systems from CandyCraft 1.12.2 to their CandyCraftReborn 1.20.1 targets.

## Core Bootstrap

| Legacy Source | Legacy Purpose | CandyCraftReborn Target | Status | Notes |
|---|---|---|---|---|
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/CandyCraft.java | Mod entrypoint, lifecycle, proxy wiring | src/main/java/com/ajsmods/candycraftreborn/CandyCraftMod.java | In Progress | Lifecycle moved to modern event bus; no proxy pattern |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/CommonProxy.java | Common-side initialization | src/main/java/com/ajsmods/candycraftreborn/CandyCraftMod.java | Planned | Replace with explicit setup events and dedicated classes |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/client/ClientProxy.java | Client render/input wiring | src/main/java/com/ajsmods/candycraftreborn/CandyCraftMod.java | In Progress | Client event subscriber active; expand in later phases |

## Registries

| Legacy Source | Legacy Purpose | CandyCraftReborn Target | Status | Notes |
|---|---|---|---|---|
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/blocks/CCBlocks.java | Block registration | src/main/java/com/ajsmods/candycraftreborn/registry/ModBlocks.java | In Progress | Vertical slice + representative batch complete (13 blocks total) |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/items/CCItems.java | Item registration | src/main/java/com/ajsmods/candycraftreborn/registry/ModItems.java | In Progress | Vertical slice + representative batch complete (13 items + spawn egg) |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/entity/CCEntities.java | Entity registration | src/main/java/com/ajsmods/candycraftreborn/registry/ModEntities.java | In Progress | Vertical slice entity + renderer + attributes complete |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/misc/CCCreativeTabs.java | Creative tab setup | src/main/java/com/ajsmods/candycraftreborn/registry/ModCreativeTabs.java | Done | New tab created for migrated content |

## Data and Assets

| Legacy Source | Legacy Purpose | CandyCraftReborn Target | Status | Notes |
|---|---|---|---|---|
| CandyCraft/src/main/resources/assets/candycraftmod/lang/* | Localization | src/main/resources/assets/candycraftreborn/lang/en_us.json | In Progress | Vertical slice + representative batch names added |
| CandyCraft/src/main/resources/assets/candycraftmod/blockstates/* | Blockstate definitions | src/main/resources/assets/candycraftreborn/blockstates/* | In Progress | Added 13 migrated blockstates |
| CandyCraft/src/main/resources/assets/candycraftmod/models/* | Models for blocks/items | src/main/resources/assets/candycraftreborn/models/* | In Progress | Added block/item model set for migrated entries |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/misc/CCRecipes.java | Hardcoded recipes | src/main/resources/data/candycraftreborn/recipes/* | In Progress | JSON recipe migration started |

## Deferred to Later Phases

| Legacy Source | Legacy Purpose | Planned Phase | Notes |
|---|---|---|---|
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/blocks/tileentity/* | Tile entities, machines, recipe handlers | Phase 3 | Move to BlockEntity + Menu + Screen |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/world/* | Dimensions, chunk provider, worldgen | Phase 4 | Full rewrite to modern worldgen APIs |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/client/gui/* | Legacy GUI handlers/screens | Phase 3 | Rebuild with modern menu/screen flow |
| CandyCraft/src/main/java/com/valentin4311/candycraftmod/event/* | Event handlers | Phase 3 | Port incrementally as systems migrate |

## Phase 1 Deliverables Snapshot

- Implemented vertical slice content:
  - 3 blocks: rock_candy_block, marshmallow_block, gummy_block
  - 3 items: peppermint, caramel_shard, sour_gem
  - 1 entity: candy_critter (+ spawn egg, renderer, attributes)
- Implemented representative legacy migration batch:
  - 10 blocks: licorice_block, candy_cane_block, caramel_block, sugar_block, chocolate_stone, cotton_candy_block, mint_block, raspberry_block, banana_block, nougat_block
  - 10 items: licorice, candy_cane, lollipop, gummy, hot_gummy, cotton_candy, honey_shard, honeycomb, chocolate_coin, sugar_crystal
- Added baseline data assets:
  - lang, blockstates, models, recipes, block loot tables
- Verified compile success with prepareRunClientCompile
