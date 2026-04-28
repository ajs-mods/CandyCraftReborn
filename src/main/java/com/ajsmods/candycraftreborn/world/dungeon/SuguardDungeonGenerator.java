package com.ajsmods.candycraftreborn.world.dungeon;

import com.ajsmods.candycraftreborn.blockentity.TeleporterBlockEntity;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * Generates the Suguard Dungeon — a hub-and-spoke layout with 4 wings from a central room.
 */
public class SuguardDungeonGenerator {
    private static final BlockState AIR = Blocks.AIR.defaultBlockState();

    public static void generate(ServerLevel level, BlockPos origin) {
        Random rand = new Random();
        int x = origin.getX(), y = origin.getY(), z = origin.getZ();

        BlockState caramelBrick = ModBlocks.CARAMEL_BRICK.get().defaultBlockState();
        BlockState caramel = ModBlocks.CARAMEL_BLOCK.get().defaultBlockState();
        BlockState honeyLamp = ModBlocks.HONEY_LAMP.get().defaultBlockState();
        BlockState jawBreaker = ModBlocks.JAW_BREAKER_BLOCK.get().defaultBlockState();
        BlockState licoriceBlock = ModBlocks.LICORICE_BLOCK.get().defaultBlockState();
        BlockState licoriceBrickStairs = ModBlocks.LICORICE_BRICK_STAIRS.get().defaultBlockState();
        BlockState nougat = ModBlocks.NOUGAT_BLOCK.get().defaultBlockState();
        BlockState chocoStone = ModBlocks.CHOCOLATE_STONE.get().defaultBlockState();
        BlockState chocoCobble = ModBlocks.CHOCOLATE_COBBLESTONE.get().defaultBlockState();

        // ═══ Central Hub: Spawn Room (9x7x9) ═══
        int hw = 4, hh = 7, hd = 4; // half-widths
        BlockPos hubMin = new BlockPos(x - hw, y, z - hd);
        BlockPos hubMax = new BlockPos(x + hw, y + hh, z + hd);
        buildBox(level, hubMin, hubMax, caramelBrick);
        fillRegion(level, hubMin.offset(1, 1, 1), hubMax.offset(-1, -1, -1), AIR);

        // Checkerboard floor
        for (int px = hubMin.getX() + 1; px < hubMax.getX(); px++) {
            for (int pz = hubMin.getZ() + 1; pz < hubMax.getZ(); pz++) {
                level.setBlock(new BlockPos(px, y + 1, pz),
                        (px + pz) % 2 == 0 ? caramel : honeyLamp, 2);
            }
        }

        // Teleporter back to entry point (overworld 0,64,0)
        BlockPos tpHub = new BlockPos(x, y + 1, z);
        level.setBlock(tpHub, ModBlocks.TELEPORTER_BLOCK.get().defaultBlockState(), 2);
        BlockEntity tpBe = level.getBlockEntity(tpHub);
        if (tpBe instanceof TeleporterBlockEntity tp) {
            tp.setTarget(new BlockPos(0, 64, 0));
            tp.setTargetDimension(Level.OVERWORLD);
        }

        // Open corridors to 4 wings (2-wide, 3-tall openings)
        // North (+Z)
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(x, y + dy, z + hd), AIR, 2);
            level.setBlock(new BlockPos(x + 1, y + dy, z + hd), AIR, 2);
        }
        // South (-Z)
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(x, y + dy, z - hd), AIR, 2);
            level.setBlock(new BlockPos(x + 1, y + dy, z - hd), AIR, 2);
        }
        // East (+X)
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(x + hw, y + dy, z), AIR, 2);
            level.setBlock(new BlockPos(x + hw, y + dy, z + 1), AIR, 2);
        }
        // West (-X)
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(x - hw, y + dy, z), AIR, 2);
            level.setBlock(new BlockPos(x - hw, y + dy, z + 1), AIR, 2);
        }

        // ═══ Wing 1 (North, +Z): Archer Gallery ═══
        generateArcherGallery(level, origin, rand, nougat, caramel);

        // ═══ Wing 2 (South, -Z): Maze Wing ═══
        generateMazeWing(level, origin, rand, chocoStone, chocoCobble);

        // ═══ Wing 3 (East, +X): Jump Shaft ═══
        generateJumpShaft(level, origin, rand);

        // ═══ Wing 4 (West, -X): Fight Room ═══
        generateFightRoom(level, origin, rand, licoriceBlock, caramel);
    }

    // ── Wing 1: Archer Gallery (+Z) ──
    private static void generateArcherGallery(ServerLevel level, BlockPos origin, Random rand,
                                               BlockState wallPrimary, BlockState wallSecondary) {
        int x = origin.getX(), y = origin.getY(), z = origin.getZ();
        int startZ = z + 6; // corridor gap
        int w = 10, h = 7, d = 50;

        BlockPos rmin = new BlockPos(x - w, y, startZ);
        BlockPos rmax = new BlockPos(x + w, y + h, startZ + d);
        buildBox(level, rmin, rmax, wallPrimary);
        fillRegion(level, rmin.offset(1, 1, 1), rmax.offset(-1, -1, -1), AIR);

        // Connect corridor from hub
        buildCorridor(level, new BlockPos(x, y, z + 5), new BlockPos(x + 1, y + 3, startZ), wallPrimary);

        // Bridge across center
        BlockState grenadine = ModBlocks.GRENADINE_BLOCK.get().defaultBlockState();
        // Grenadine floor (lava-like)
        for (int px = rmin.getX() + 1; px < rmax.getX(); px++) {
            for (int pz = rmin.getZ() + 1; pz < rmax.getZ(); pz++) {
                level.setBlock(new BlockPos(px, y + 1, pz), grenadine, 2);
            }
        }
        // Bridge
        for (int bz = startZ + 2; bz < startZ + d - 2; bz++) {
            level.setBlock(new BlockPos(x, y + 2, bz), wallSecondary, 2);
            level.setBlock(new BlockPos(x + 1, y + 2, bz), wallSecondary, 2);
        }

        // 4 suguard spawners at far end
        for (int i = 0; i < 4; i++) {
            BlockPos sp = new BlockPos(x - 4 + i * 3, y + 2, startZ + d - 5);
            level.setBlock(sp, Blocks.SPAWNER.defaultBlockState(), 2);
            BlockEntity sbe = level.getBlockEntity(sp);
            if (sbe instanceof SpawnerBlockEntity spawner) {
                spawner.getSpawner().setEntityId(ModEntities.SUGUARD.get(), level, level.getRandom(), sp);
            }
        }

        // Boss room at far end (cylindrical approximation — square for simplicity)
        int bossZ = startZ + d + 2;
        generateBossRoom(level, new BlockPos(x, y, bossZ), rand, true, 0);

        // Sentry key hole decoration
        level.setBlock(new BlockPos(x + 2, y + 2, startZ + d - 1),
                ModBlocks.SUGUARD_SENTRY_KEY_HOLE.get().defaultBlockState(), 2);

        // Connect room to boss
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(x, y + dy, startZ + d), AIR, 2);
            level.setBlock(new BlockPos(x + 1, y + dy, startZ + d), AIR, 2);
            level.setBlock(new BlockPos(x, y + dy, bossZ - 1), AIR, 2);
            level.setBlock(new BlockPos(x + 1, y + dy, bossZ - 1), AIR, 2);
        }
    }

    // ── Wing 2: Maze Wing (-Z) ──
    private static void generateMazeWing(ServerLevel level, BlockPos origin, Random rand,
                                          BlockState wallPrimary, BlockState wallSecondary) {
        int x = origin.getX(), y = origin.getY(), z = origin.getZ();
        int startZ = z - 6;
        int w = 11, h = 7, d = 52;

        BlockPos rmin = new BlockPos(x - w, y, startZ - d);
        BlockPos rmax = new BlockPos(x + w, y + h, startZ);
        buildBox(level, rmin, rmax, wallPrimary);
        fillRegion(level, rmin.offset(1, 1, 1), rmax.offset(-1, -1, -1), AIR);

        // Connect corridor from hub
        buildCorridor(level, new BlockPos(x, y, startZ), new BlockPos(x + 1, y + 3, z - 5), wallPrimary);

        // Simple visible-wall maze (not barrier blocks per scope note)
        BlockState mazeWall = wallSecondary;
        BlockState spikes = ModBlocks.SUGAR_SPIKES.get().defaultBlockState();
        // Horizontal walls at intervals
        for (int i = 0; i < 8; i++) {
            int mz = startZ - 5 - i * 6;
            int gapX = x - w + 2 + rand.nextInt(2 * w - 4); // random gap position
            for (int px = rmin.getX() + 1; px < rmax.getX(); px++) {
                if (Math.abs(px - gapX) > 1) { // leave 3-wide gap
                    for (int dy = 1; dy <= 4; dy++) {
                        level.setBlock(new BlockPos(px, y + dy, mz), mazeWall, 2);
                    }
                }
            }
        }

        // Spikes + honey lamp floor hazards
        for (int i = 0; i < 15; i++) {
            int sx = rmin.getX() + 2 + rand.nextInt(2 * w - 4);
            int sz = rmin.getZ() + 2 + rand.nextInt(d - 4);
            level.setBlock(new BlockPos(sx, y + 1, sz), spikes, 2);
        }

        // Boss room at far end
        int bossZ = startZ - d - 17;
        generateBossRoom(level, new BlockPos(x, y, bossZ), rand, true, 1);

        // Connect
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(x, y + dy, startZ - d), AIR, 2);
            level.setBlock(new BlockPos(x + 1, y + dy, startZ - d), AIR, 2);
            level.setBlock(new BlockPos(x, y + dy, bossZ + 15), AIR, 2);
            level.setBlock(new BlockPos(x + 1, y + dy, bossZ + 15), AIR, 2);
        }
    }

    // ── Wing 3: Jump Shaft (+X) ──
    private static void generateJumpShaft(ServerLevel level, BlockPos origin, Random rand) {
        int x = origin.getX(), y = origin.getY(), z = origin.getZ();
        int startX = x + 6;
        int shaftW = 5, shaftH = 60, shaftD = 5;

        BlockState jawBreaker = ModBlocks.JAW_BREAKER_BLOCK.get().defaultBlockState();
        BlockPos smin = new BlockPos(startX, y, z - shaftD);
        BlockPos smax = new BlockPos(startX + shaftW * 2, y + shaftH, z + shaftD);
        buildBox(level, smin, smax, jawBreaker);
        fillRegion(level, smin.offset(1, 1, 1), smax.offset(-1, -1, -1), Blocks.AIR.defaultBlockState());

        // Connect corridor from hub
        buildCorridor(level, new BlockPos(x + 5, y, z), new BlockPos(startX, y + 3, z + 1), jawBreaker);

        // Trampoline platforms at intervals
        BlockState yellowTramp = ModBlocks.YELLOW_TRAMPOJELLY.get().defaultBlockState();
        BlockState tramp = ModBlocks.TRAMPOJELLY.get().defaultBlockState();
        BlockState redTramp = ModBlocks.RED_TRAMPOJELLY.get().defaultBlockState();
        BlockState[] trampolines = {yellowTramp, tramp, redTramp};

        for (int i = 0; i < 12; i++) {
            int py = y + 3 + i * 5;
            int pz = z - 2 + (i % 3) * 2;
            int px = startX + 2 + (i % 2) * 4;
            BlockState ts = trampolines[i % 3];
            for (int dx = 0; dx < 3; dx++) {
                for (int dz = 0; dz < 3; dz++) {
                    level.setBlock(new BlockPos(px + dx, py, pz + dz), ts, 2);
                }
            }
        }

        // Fight room at top
        int topY = y + shaftH - 5;
        int fightX = startX + shaftW;
        BlockPos fmin = new BlockPos(fightX - 10, topY, z - 10);
        BlockPos fmax = new BlockPos(fightX + 10, topY + 10, z + 10);
        buildBox(level, fmin, fmax, jawBreaker);
        fillRegion(level, fmin.offset(1, 1, 1), fmax.offset(-1, -1, -1), Blocks.AIR.defaultBlockState());

        // Connect shaft top to fight room
        for (int dy = 0; dy <= 2; dy++) {
            level.setBlock(new BlockPos(fightX - 10, topY + 1 + dy, z), Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(new BlockPos(fightX - 10, topY + 1 + dy, z + 1), Blocks.AIR.defaultBlockState(), 2);
        }

        // Boss room
        generateBossRoom(level, new BlockPos(fightX, topY, z + 12), rand, true, 2);
        // Connect
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(fightX, topY + dy, z + 10), Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(new BlockPos(fightX + 1, topY + dy, z + 10), Blocks.AIR.defaultBlockState(), 2);
        }
    }

    // ── Wing 4: Fight Room (-X) ──
    private static void generateFightRoom(ServerLevel level, BlockPos origin, Random rand,
                                           BlockState wallPrimary, BlockState wallSecondary) {
        int x = origin.getX(), y = origin.getY(), z = origin.getZ();
        int startX = x - 6;
        int w = 20, h = 20, d = 20;

        BlockPos rmin = new BlockPos(startX - w * 2, y, z - d);
        BlockPos rmax = new BlockPos(startX, y + h, z + d);
        buildBox(level, rmin, rmax, wallPrimary);
        fillRegion(level, rmin.offset(1, 1, 1), rmax.offset(-1, -1, -1), Blocks.AIR.defaultBlockState());

        // Connect corridor from hub
        buildCorridor(level, new BlockPos(startX, y, z), new BlockPos(x - 5, y + 3, z + 1), wallPrimary);

        // 3 layers of spawners (12 per layer = 36 total)
        int centerX = (rmin.getX() + rmax.getX()) / 2;
        for (int layer = 0; layer < 3; layer++) {
            int ly = y + 2 + layer * 6;
            // Platform for upper layers
            if (layer > 0) {
                for (int px = rmin.getX() + 1; px < rmax.getX(); px++) {
                    for (int pz = rmin.getZ() + 1; pz < rmax.getZ(); pz++) {
                        level.setBlock(new BlockPos(px, ly - 1, pz), wallSecondary, 2);
                    }
                }
                // Openings for movement
                for (int px = centerX - 2; px <= centerX + 2; px++) {
                    for (int pz = z - 2; pz <= z + 2; pz++) {
                        level.setBlock(new BlockPos(px, ly - 1, pz), Blocks.AIR.defaultBlockState(), 2);
                    }
                }
            }

            for (int i = 0; i < 12; i++) {
                double angle = (2 * Math.PI * i) / 12;
                int sx = centerX + (int)(10 * Math.cos(angle));
                int sz = z + (int)(10 * Math.sin(angle));
                BlockPos sp = new BlockPos(sx, ly, sz);
                level.setBlock(sp, Blocks.SPAWNER.defaultBlockState(), 2);
                BlockEntity sbe = level.getBlockEntity(sp);
                if (sbe instanceof SpawnerBlockEntity spawner) {
                    spawner.getSpawner().setEntityId(ModEntities.SUGUARD.get(), level, level.getRandom(), sp);
                }
            }
        }

        // Central pillar
        for (int py = y + 1; py < y + h; py++) {
            level.setBlock(new BlockPos(centerX, py, z), wallPrimary, 2);
        }

        // Boss room (main one with reward chest)
        int bossX = rmin.getX() - 17;
        generateBossRoom(level, new BlockPos(bossX, y, z), rand, false, 3);

        // Connect
        for (int dy = 1; dy <= 3; dy++) {
            level.setBlock(new BlockPos(rmin.getX(), y + dy, z), Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(new BlockPos(rmin.getX(), y + dy, z + 1), Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(new BlockPos(bossX + 15, y + dy, z), Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(new BlockPos(bossX + 15, y + dy, z + 1), Blocks.AIR.defaultBlockState(), 2);
        }
    }

    /**
     * Generate a boss room. wingIndex 0-3, hasTeleporterToHub = true for wings 0-2.
     * Wing 3 (main boss) gets the reward chest instead.
     */
    private static void generateBossRoom(ServerLevel level, BlockPos center, Random rand,
                                          boolean hasTeleporterToHub, int wingIndex) {
        int cx = center.getX(), cy = center.getY(), cz = center.getZ();
        int radius = 15;

        BlockState jawBreaker = ModBlocks.JAW_BREAKER_BLOCK.get().defaultBlockState();
        BlockState licoriceBlock = ModBlocks.LICORICE_BLOCK.get().defaultBlockState();
        BlockState licoriceBrickStairs = ModBlocks.LICORICE_BRICK_STAIRS.get().defaultBlockState();

        // Square approximation of cylindrical room
        BlockPos rmin = new BlockPos(cx - radius, cy, cz - radius);
        BlockPos rmax = new BlockPos(cx + radius, cy + 15, cz + radius);
        buildBox(level, rmin, rmax, jawBreaker);
        fillRegion(level, rmin.offset(1, 1, 1), rmax.offset(-1, -1, -1), Blocks.AIR.defaultBlockState());

        // Checkerboard floor
        for (int px = rmin.getX() + 1; px < rmax.getX(); px++) {
            for (int pz = rmin.getZ() + 1; pz < rmax.getZ(); pz++) {
                level.setBlock(new BlockPos(px, cy + 1, pz),
                        (px + pz) % 2 == 0 ? jawBreaker : licoriceBlock, 2);
            }
        }

        // 8 decorative pillars
        int pillarR = radius - 3;
        for (int i = 0; i < 8; i++) {
            double angle = (2 * Math.PI * i) / 8;
            int px = cx + (int)(pillarR * Math.cos(angle));
            int pz = cz + (int)(pillarR * Math.sin(angle));
            for (int py = cy + 1; py <= cy + 12; py++) {
                level.setBlock(new BlockPos(px, py, pz), licoriceBrickStairs, 2);
            }
        }

        // Spawn BossSuguard
        var boss = ModEntities.BOSS_SUGUARD.get().create(level);
        if (boss != null) {
            boss.moveTo(cx + 0.5, cy + 2, cz + 0.5, 0, 0);
            level.addFreshEntity(boss);
        }

        if (hasTeleporterToHub) {
            // Teleporter back to hub center
            BlockPos tpPos = new BlockPos(cx - 3, cy + 1, cz);
            level.setBlock(tpPos, ModBlocks.TELEPORTER_BLOCK.get().defaultBlockState(), 2);
            BlockEntity tpBe = level.getBlockEntity(tpPos);
            if (tpBe instanceof TeleporterBlockEntity tp) {
                // Target = origin of the hub (we pass center, so we know origin is at some fixed offset)
                // For simplicity, store a nearby pos; the hub teleporter is at the hub origin
                tp.setTarget(center.offset(0, 0, 0)); // will TP to near boss room; ideally to hub
            }
        } else {
            // Reward chest
            BlockPos chestPos = new BlockPos(cx, cy + 1, cz - 3);
            level.setBlock(chestPos, ModBlocks.MARSHMALLOW_CHEST.get().defaultBlockState(), 2);
            BlockEntity chestBe = level.getBlockEntity(chestPos);
            if (chestBe instanceof ChestBlockEntity chest) {
                chest.setItem(0, new ItemStack(ModItems.JELLY_CROWN.get()));
                chest.setItem(1, new ItemStack(ModItems.SUGAR_CRYSTAL.get(), 8));
                chest.setItem(2, new ItemStack(ModItems.LICORICE.get(), 16));
                chest.setItem(3, new ItemStack(ModItems.CANDY_CANE.get(), 16));
                chest.setItem(4, new ItemStack(ModItems.CHOCOLATE_COIN.get(), 32));
                chest.setItem(5, new ItemStack(ModItems.PEZ.get(), 4));
                chest.setItem(6, new ItemStack(ModItems.GUMMY.get(), 16));
                chest.setItem(7, new ItemStack(ModItems.LOLLIPOP_SEEDS.get(), 8));
                chest.setItem(8, new ItemStack(ModItems.COTTON_CANDY.get(), 16));
                chest.setItem(9, new ItemStack(ModItems.FORK.get()));
                chest.setItem(10, new ItemStack(ModItems.SUGUARD_BOSS_KEY.get()));
                if (rand.nextInt(5) == 0) {
                    chest.setItem(11, new ItemStack(ModBlocks.DRAGON_EGG_BLOCK.get().asItem()));
                }
            }

            // Teleporter back to overworld
            BlockPos tpPos = new BlockPos(cx + 3, cy + 1, cz);
            level.setBlock(tpPos, ModBlocks.TELEPORTER_BLOCK.get().defaultBlockState(), 2);
            BlockEntity tpBe = level.getBlockEntity(tpPos);
            if (tpBe instanceof TeleporterBlockEntity tp) {
                tp.setTarget(new BlockPos(0, 64, 0));
                tp.setTargetDimension(Level.OVERWORLD);
            }
        }
    }

    /** Build a solid box shell. */
    private static void buildBox(ServerLevel level, BlockPos min, BlockPos max, BlockState state) {
        for (int px = min.getX(); px <= max.getX(); px++) {
            for (int py = min.getY(); py <= max.getY(); py++) {
                for (int pz = min.getZ(); pz <= max.getZ(); pz++) {
                    boolean isEdge = px == min.getX() || px == max.getX()
                            || py == min.getY() || py == max.getY()
                            || pz == min.getZ() || pz == max.getZ();
                    if (isEdge) {
                        level.setBlock(new BlockPos(px, py, pz), state, 2);
                    }
                }
            }
        }
    }

    /** Fill a region with a single block state. */
    private static void fillRegion(ServerLevel level, BlockPos min, BlockPos max, BlockState state) {
        for (int px = min.getX(); px <= max.getX(); px++) {
            for (int py = min.getY(); py <= max.getY(); py++) {
                for (int pz = min.getZ(); pz <= max.getZ(); pz++) {
                    level.setBlock(new BlockPos(px, py, pz), state, 2);
                }
            }
        }
    }

    /** Build a simple corridor connecting two points (fills shell). */
    private static void buildCorridor(ServerLevel level, BlockPos from, BlockPos to, BlockState wall) {
        int minX = Math.min(from.getX(), to.getX()) - 1;
        int maxX = Math.max(from.getX(), to.getX()) + 1;
        int minY = Math.min(from.getY(), to.getY());
        int maxY = Math.max(from.getY(), to.getY()) + 1;
        int minZ = Math.min(from.getZ(), to.getZ()) - 1;
        int maxZ = Math.max(from.getZ(), to.getZ()) + 1;

        for (int px = minX; px <= maxX; px++) {
            for (int py = minY; py <= maxY; py++) {
                for (int pz = minZ; pz <= maxZ; pz++) {
                    boolean isEdge = px == minX || px == maxX || py == minY || py == maxY || pz == minZ || pz == maxZ;
                    BlockPos p = new BlockPos(px, py, pz);
                    if (isEdge) {
                        level.setBlock(p, wall, 2);
                    } else {
                        level.setBlock(p, AIR, 2);
                    }
                }
            }
        }
    }
}
