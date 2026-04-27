# CandyCraftReborn Rebuild Plan

Last updated: 2026-04-27

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
Status: In Progress

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

Exit Criteria:
- Project compiles and runs with starter architecture.
- Vertical slice appears in-game.

### Phase 2: Core Content Migration (Weeks 2-4)
Status: Not Started

Scope:
- Port blocks/items and recipe systems.
- Replace old registration patterns with DeferredRegister/data-driven assets.

Tasks:
- [ ] Migrate block registrations from legacy CCBlocks.
- [ ] Migrate item registrations from legacy CCItems.
- [ ] Port armor/tool tier logic to modern 1.20.1 APIs.
- [ ] Convert hardcoded recipes to JSON recipes.

Exit Criteria:
- First major content set fully migrated and craftable.

### Phase 3: Gameplay Systems (Weeks 4-7)
Status: Not Started

Scope:
- Port entities, fluids, block entities, menus, screens, and relevant networking.

Tasks:
- [ ] Migrate entity registrations and spawn eggs.
- [ ] Port AI/attributes for priority mobs.
- [ ] Port fluids (Grenadine, Caramel) to ForgeFlowingFluid.
- [ ] Port machines and block entities with MenuType + Screens.
- [ ] Replace legacy proxy-era client wiring.

Exit Criteria:
- Entities and machines work in both singleplayer and multiplayer.

### Phase 4: World and Dimension Systems (Weeks 7-10)
Status: Not Started

Scope:
- Rebuild worldgen/biomes/dimensions using modern 1.20.1 data-driven systems.

Tasks:
- [ ] Port custom biomes.
- [ ] Rebuild Candy dimension registration.
- [ ] Reimplement world generation and structures/features.
- [ ] Validate generation stability/performance.

Exit Criteria:
- Custom world and structures generate consistently.

### Phase 5: Parity and Polish (Weeks 10-12)
Status: Not Started

Scope:
- Asset completion, balancing, enchantments, advancements, user-facing polish.

Tasks:
- [ ] Port remaining models/textures/lang/sounds.
- [ ] Port enchantments and advancements.
- [ ] Gameplay tuning and balance pass.
- [ ] Remove debug placeholders and temp content.

Exit Criteria:
- Feature-complete public alpha candidate.

### Phase 6: Stabilization and Release (Week 12+)
Status: Not Started

Scope:
- Hardening, compatibility testing, release process.

Tasks:
- [ ] Multiplayer soak tests and regression checks.
- [ ] Performance and memory profiling pass.
- [ ] Crash triage and bugfix sprint.
- [ ] Tag release candidate and publish notes.

Exit Criteria:
- Public release candidate available on GitHub.

## Priority Workstreams
1. Registry and content bootstrapping
2. Entity/fluid/block-entity systems
3. GUI/menu/network synchronization
4. Worldgen and dimensions
5. Bosses/structures/final polish

## Migration Risks and Mitigation

### Risk: 1.12 worldgen architecture does not map directly to 1.20.1
Mitigation:
- Implement worldgen in isolated milestones.
- Validate each biome/feature incrementally.

### Risk: Legacy proxy/event patterns break in modern Forge
Mitigation:
- Keep all sided behavior in modern event subscriptions.
- Avoid reintroducing old proxy abstractions.

### Risk: Large asset volume slows engineering progress
Mitigation:
- Prioritize functional placeholders first.
- Schedule full asset parity in Phase 5.

## Sprint 1 (Immediate)
Status: Next Up

Sprint Goal:
Ship a vertical slice proving the rebuild architecture is production-viable.

Backlog:
- [x] Add 10 representative blocks from legacy design set.
- [x] Add 10 representative items from legacy design set.
- [x] Add 1 basic machine block entity + menu + screen.
- [x] Add 1 migrated entity with spawn egg and renderer.
- [x] Add recipes and data assets for migrated content.
- [x] Run compile + runClient validation.

Definition of Done:
- Content appears in-game with textures and names.
- No crashes during world load/join.
- Changes committed and pushed with clear changelog.

## Tracking Conventions
- Update each phase status: Not Started, In Progress, Blocked, Done.
- Keep tasks checked off as they land in main.
- Add blockers under the relevant phase as they appear.

## Change Log
- 2026-04-27: Initial rebuild plan created from legacy inventory and migration analysis.
- 2026-04-27: Phase 1 vertical slice implemented (3 blocks, 3 items, 1 entity, recipes/assets) and migration matrix added.
- 2026-04-27: Added representative migration batch (10 blocks, 10 items) with blockstates/models/lang/loot and compile validation.
- 2026-04-27: Added basic alchemy_table machine slice with block entity, menu, and client screen.
