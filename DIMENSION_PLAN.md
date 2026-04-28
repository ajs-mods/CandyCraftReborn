# Candy Dimension Setup Plan

Last updated: 2026-04-28

## Context

The portal block (`CandyPortalBlock`) and frame activation logic (`SugarBlock`) are already implemented. The portal targets `candycraftreborn:candy_world`, but that dimension doesn't exist yet — no datapack JSONs are registered.

This plan covers the minimum work to make the portal functional, plus a roadmap toward full custom worldgen.

## Phase 4 Reference

From `REBUILD_PLAN.md` Phase 4 (World and Dimension Systems):
- [ ] Rebuild Candy dimension registration
- [ ] Port custom biomes
- [ ] Reimplement world generation and structures/features
- [ ] Validate generation stability/performance

---

## Step 1: Minimal Dimension (Testable Portal)

Goal: Portal teleports the player to a flat candy world so we can validate the full round-trip.

### Files to create:

#### `data/candycraftreborn/dimension_type/candy_world.json`
```json
{
  "ultrawarm": false,
  "natural": false,
  "piglin_safe": false,
  "respawn_anchor_works": false,
  "bed_works": false,
  "has_raids": false,
  "has_skylight": true,
  "has_ceiling": false,
  "coordinate_scale": 1.0,
  "ambient_light": 0.5,
  "logical_height": 384,
  "effects": "minecraft:overworld",
  "infiniburn": "#minecraft:infiniburn_overworld",
  "min_y": -64,
  "height": 384,
  "monster_spawn_light_level": 0,
  "monster_spawn_block_light_limit": 0
}
```

#### `data/candycraftreborn/dimension/candy_world.json`
```json
{
  "type": "candycraftreborn:candy_world",
  "generator": {
    "type": "minecraft:flat",
    "settings": {
      "biome": "minecraft:plains",
      "lakes": false,
      "features": false,
      "layers": [
        { "block": "minecraft:bedrock", "height": 1 },
        { "block": "candycraftreborn:sugar_block", "height": 10 },
        { "block": "candycraftreborn:marshmallow_block", "height": 3 },
        { "block": "candycraftreborn:cotton_candy_block", "height": 1 }
      ]
    }
  }
}
```

### Validation:
- Build and run client (`./gradlew runClient`)
- Build Sugar Block portal frame (2x3 interior)
- Right-click frame with lava bucket
- Step into portal → confirm teleportation to candy_world
- Step into portal in candy_world → confirm return to Overworld

### Known limitations at this step:
- No matching portal on arrival (player spawns at world spawn or same coords)
- Flat terrain only (no custom biomes/features)
- Uses `minecraft:plains` biome placeholder

---

## Step 2: Custom Biome(s)

Goal: Replace placeholder biome with candy-themed biome(s).

### Files:
- `data/candycraftreborn/worldgen/biome/candy_plains.json`
- `data/candycraftreborn/worldgen/biome/chocolate_forest.json` (optional)

### Properties to define:
- Sky/fog/water/grass colors (pink, pastel palette)
- Custom mob spawns
- Custom surface rules

---

## Step 3: Noise-Based Terrain

Goal: Replace flat generator with proper candy terrain generation.

### Work:
- Custom noise settings JSON or custom chunk generator class
- Surface rules (marshmallow top, sugar below, etc.)
- Ore placement (candy ores)
- Feature placement (candy trees, lollipops, etc.)

---

## Step 4: Portal Arrival Logic

Goal: Create/find a portal on the destination side (like Nether portals do).

### Work:
- Implement custom `ITeleporter` for `CandyPortalBlock`
- Search for existing portal within range at target coords
- Build new portal frame if none found
- Handle chunk loading edge cases

---

## Step 5: Structures & Polish

Goal: Add dimension-specific structures and finalize worldgen.

### Work:
- Candy village / dungeon structures
- Structure sets and placement
- Custom sky renderer (optional)
- Ambient sounds / music

---

## Quick Reference: Testing the Portal

Once Step 1 files are in place:

```
/tp @s ~ 100 ~       (get to open area)
/give @s candycraftreborn:sugar_block 64
/give @s minecraft:lava_bucket
```

Build a 4x5 frame (2x3 interior) of sugar blocks, right-click interior edge with lava bucket.
