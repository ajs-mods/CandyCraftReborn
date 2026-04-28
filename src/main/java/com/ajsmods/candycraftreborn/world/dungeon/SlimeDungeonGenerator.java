package com.ajsmods.candycraftreborn.world.dungeon;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.blockentity.TeleporterBlockEntity;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * Generates the Slime Dungeon — a linear sequence of 8 rooms extending in -Z from the origin.
 */
public class SlimeDungeonGenerator {
    private static final ResourceKey<Level> OVERWORLD = Level.OVERWORLD;

    public static void generate(ServerLevel level, BlockPos origin) {
        Random rand = new Random();
        net.minecraft.util.RandomSource randomSource = level.getRandom();
        BlockState jawBreaker = ModBlocks.JAW_BREAKER_BLOCK.get().defaultBlockState();
        BlockState jawLight = ModBlocks.JAW_BREAKER_LIGHT.get().defaultBlockState();
        BlockState air = Blocks.AIR.defaultBlockState();

        // Room positions track the current Z offset (rooms extend in -Z)
        int z = origin.getZ();
        int y = origin.getY();
        int x = origin.getX();

        // ═══ Room 1: Spawn Room (10x5x3, centered on origin) ═══
        int r1w = 10, r1h = 5, r1d = 3;
        BlockPos r1min = new BlockPos(x - r1w / 2, y, z - r1d + 1);
        BlockPos r1max = new BlockPos(x + r1w / 2, y + r1h, z);
        buildBox(level, r1min, r1max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r1min.offset(1, 1, 1), r1max.offset(-1, -1, -1), air);

        // Teleporter back to overworld spawn (0, 64, 0)
        BlockPos tpBack = new BlockPos(x, y + 1, z - 1);
        level.setBlock(tpBack, ModBlocks.TELEPORTER_BLOCK.get().defaultBlockState(), 2);
        BlockEntity tpBe = level.getBlockEntity(tpBack);
        if (tpBe instanceof TeleporterBlockEntity tp) {
            tp.setTarget(new BlockPos(0, 64, 0));
            tp.setTargetDimension(OVERWORLD);
        }

        // Door in -Z wall
        int doorZ = z - r1d;
        level.setBlock(new BlockPos(x, y + 1, doorZ), air, 2);
        level.setBlock(new BlockPos(x, y + 2, doorZ), air, 2);

        // ═══ Room 2: Corridor (4x5x10) ═══
        int corZ = doorZ;
        int r2w = 4, r2h = 5, r2d = 10;
        BlockPos r2min = new BlockPos(x - r2w / 2, y, corZ - r2d);
        BlockPos r2max = new BlockPos(x + r2w / 2, y + r2h, corZ);
        buildBox(level, r2min, r2max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r2min.offset(1, 1, 1), r2max.offset(-1, -1, -1), air);

        // Decorative trampojelly on walls
        BlockState redTramp = ModBlocks.RED_TRAMPOJELLY.get().defaultBlockState();
        BlockState shockAbs = ModBlocks.JELLY_SHOCK_ABSORBER.get().defaultBlockState();
        for (int dz = corZ - r2d + 1; dz < corZ; dz++) {
            boolean alt = (dz % 2 == 0);
            level.setBlock(new BlockPos(x - r2w / 2 + 1, y + 2, dz), alt ? redTramp : shockAbs, 2);
            level.setBlock(new BlockPos(x + r2w / 2 - 1, y + 2, dz), alt ? shockAbs : redTramp, 2);
        }

        // Door out
        int cor2Z = corZ - r2d;
        level.setBlock(new BlockPos(x, y + 1, cor2Z), air, 2);
        level.setBlock(new BlockPos(x, y + 2, cor2Z), air, 2);

        // ═══ Room 3: Jump Room (8x33x41) ═══
        int r3w = 8, r3h = 33, r3d = 41;
        BlockPos r3min = new BlockPos(x - r3w / 2, y, cor2Z - r3d);
        BlockPos r3max = new BlockPos(x + r3w / 2, y + r3h, cor2Z);
        buildBox(level, r3min, r3max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r3min.offset(1, 1, 1), r3max.offset(-1, -1, -1), air);

        // Water floor
        BlockState water = Blocks.WATER.defaultBlockState();
        for (int px = r3min.getX() + 1; px < r3max.getX(); px++) {
            for (int pz = r3min.getZ() + 1; pz < r3max.getZ(); pz++) {
                level.setBlock(new BlockPos(px, y + 1, pz), water, 2);
            }
        }

        // Platforms at various heights
        BlockState purpleTramp = ModBlocks.PURPLE_TRAMPOJELLY.get().defaultBlockState();
        BlockState honeyLamp = ModBlocks.HONEY_LAMP.get().defaultBlockState();
        int[] platformHeights = {5, 10, 15, 20, 25, 30};
        for (int i = 0; i < platformHeights.length; i++) {
            int ph = y + platformHeights[i];
            int pz = r3min.getZ() + 5 + i * 6;
            int side = (i % 2 == 0) ? (x - 2) : (x + 1);
            for (int px = side; px < side + 3; px++) {
                for (int pdz = pz; pdz < pz + 3; pdz++) {
                    level.setBlock(new BlockPos(px, ph, pdz), purpleTramp, 2);
                }
            }
            level.setBlock(new BlockPos(side + 1, ph + 1, pz + 1), honeyLamp, 2);
        }

        // Exit at top -Z wall
        int r3exitZ = cor2Z - r3d;
        level.setBlock(new BlockPos(x, y + r3h - 2, r3exitZ), air, 2);
        level.setBlock(new BlockPos(x, y + r3h - 1, r3exitZ), air, 2);

        // ═══ Room 4: Water Room (24x24x24) ═══
        // Adjust Y to the top exit of jump room
        int r4y = y + r3h - 3;
        int r4w = 24, r4h = 24, r4d = 24;
        BlockPos r4min = new BlockPos(x - r4w / 2, r4y, r3exitZ - r4d);
        BlockPos r4max = new BlockPos(x + r4w / 2, r4y + r4h, r3exitZ);
        buildBox(level, r4min, r4max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r4min.offset(1, 1, 1), r4max.offset(-1, -1, -1), air);

        // Water layers (lower half)
        for (int py = r4y + 1; py < r4y + r4h / 2; py++) {
            for (int px = r4min.getX() + 1; px < r4max.getX(); px++) {
                for (int pz = r4min.getZ() + 1; pz < r4max.getZ(); pz++) {
                    level.setBlock(new BlockPos(px, py, pz), water, 2);
                }
            }
        }

        // Cotton candy web patches
        BlockState web = ModBlocks.COTTON_CANDY_WEB.get().defaultBlockState();
        for (int i = 0; i < 20; i++) {
            int wx = r4min.getX() + 2 + rand.nextInt(r4w - 4);
            int wy = r4y + 2 + rand.nextInt(r4h / 2 - 2);
            int wz = r4min.getZ() + 2 + rand.nextInt(r4d - 4);
            level.setBlock(new BlockPos(wx, wy, wz), web, 2);
        }

        // Re-enter at ground level for remaining rooms
        // Bridge down from water room: place a staircase / ladder or just a drop with water
        int r4exitZ = r3exitZ - r4d;
        // Door at ground level -Z
        level.setBlock(new BlockPos(x, r4y + 1, r4exitZ), air, 2);
        level.setBlock(new BlockPos(x, r4y + 2, r4exitZ), air, 2);

        // Transition corridor back to ground level
        int groundY = y;
        int transZ = r4exitZ;
        // Simple drop shaft with water at bottom
        for (int py = groundY; py <= r4y + 2; py++) {
            level.setBlock(new BlockPos(x, py, transZ - 1), air, 2);
            level.setBlock(new BlockPos(x + 1, py, transZ - 1), air, 2);
        }
        // Water at bottom of shaft
        level.setBlock(new BlockPos(x, groundY, transZ - 1), water, 2);
        level.setBlock(new BlockPos(x + 1, groundY, transZ - 1), water, 2);
        // Walls around shaft
        for (int py = groundY; py <= r4y + 3; py++) {
            for (int dx = -1; dx <= 2; dx++) {
                for (int dz = -2; dz <= 0; dz++) {
                    if (dx == 0 && dz == -1) continue;
                    if (dx == 1 && dz == -1) continue;
                    BlockPos sp = new BlockPos(x + dx, py, transZ + dz);
                    if (level.getBlockState(sp).isAir()) {
                        level.setBlock(sp, jawBreaker, 2);
                    }
                }
            }
        }

        int nextZ = transZ - 3;

        // ═══ Room 5: Mob Room (22x7x55) ═══
        int r5w = 22, r5h = 7, r5d = 55;
        BlockPos r5min = new BlockPos(x - r5w / 2, groundY, nextZ - r5d);
        BlockPos r5max = new BlockPos(x + r5w / 2, groundY + r5h, nextZ);
        buildBox(level, r5min, r5max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r5min.offset(1, 1, 1), r5max.offset(-1, -1, -1), air);

        // Door in
        level.setBlock(new BlockPos(x, groundY + 1, nextZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, nextZ), air, 2);

        // 16 spawners in two rows
        EntityType<?>[] jellyTypes = {
                ModEntities.JELLY.get(), ModEntities.YELLOW_JELLY.get(), ModEntities.RED_JELLY.get()
        };
        for (int row = 0; row < 2; row++) {
            int sx = x - 6 + row * 12;
            for (int i = 0; i < 8; i++) {
                int sz = nextZ - 5 - i * 6;
                BlockPos spawnerPos = new BlockPos(sx, groundY + 1, sz);
                level.setBlock(spawnerPos, Blocks.SPAWNER.defaultBlockState(), 2);
                BlockEntity sbe = level.getBlockEntity(spawnerPos);
                if (sbe instanceof SpawnerBlockEntity spawner) {
                    spawner.getSpawner().setEntityId(jellyTypes[i % 3], level, randomSource, spawnerPos);
                }
            }
        }

        // Door out
        int r5exitZ = nextZ - r5d;
        level.setBlock(new BlockPos(x, groundY + 1, r5exitZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, r5exitZ), air, 2);

        // ═══ Room 6: Mini-Boss Room (24x24x24) ═══
        int r6w = 24, r6h = 24, r6d = 24;
        BlockPos r6min = new BlockPos(x - r6w / 2, groundY, r5exitZ - r6d);
        BlockPos r6max = new BlockPos(x + r6w / 2, groundY + r6h, r5exitZ);
        buildBox(level, r6min, r6max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r6min.offset(1, 1, 1), r6max.offset(-1, -1, -1), air);

        // Door in
        level.setBlock(new BlockPos(x, groundY + 1, r5exitZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, r5exitZ), air, 2);

        // Grenadine floor pattern
        BlockState grenadine = ModBlocks.GRENADINE_BLOCK.get().defaultBlockState();
        for (int px = r6min.getX() + 1; px < r6max.getX(); px++) {
            for (int pz = r6min.getZ() + 1; pz < r6max.getZ(); pz++) {
                if ((px + pz) % 3 == 0) {
                    level.setBlock(new BlockPos(px, groundY + 1, pz), grenadine, 2);
                }
            }
        }

        // Sentry key hole decoration
        level.setBlock(new BlockPos(x + 2, groundY + 2, r6min.getZ() + 1), ModBlocks.JELLY_SENTRY_KEY_HOLE.get().defaultBlockState(), 2);

        // Spawn PEZJelly boss
        var pezJelly = ModEntities.PEZ_JELLY.get().create(level);
        if (pezJelly != null) {
            pezJelly.moveTo(x + 0.5, groundY + 2, r6min.getZ() + r6d / 2.0 + 0.5, 0, 0);
            level.addFreshEntity(pezJelly);
        }

        // Door out
        int r6exitZ = r5exitZ - r6d;
        level.setBlock(new BlockPos(x, groundY + 1, r6exitZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, r6exitZ), air, 2);

        // ═══ Room 7: Boss Room (50x50x49) ═══
        int r7w = 50, r7h = 50, r7d = 49;
        BlockPos r7min = new BlockPos(x - r7w / 2, groundY, r6exitZ - r7d);
        BlockPos r7max = new BlockPos(x + r7w / 2, groundY + r7h, r6exitZ);
        buildBox(level, r7min, r7max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r7min.offset(1, 1, 1), r7max.offset(-1, -1, -1), air);

        // Door in
        level.setBlock(new BlockPos(x, groundY + 1, r6exitZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, r6exitZ), air, 2);

        // Boss key hole decoration
        level.setBlock(new BlockPos(x + 2, groundY + 2, r7min.getZ() + 1), ModBlocks.JELLY_BOSS_KEY_HOLE.get().defaultBlockState(), 2);

        // Candy cane slab floor
        BlockState ccSlab = ModBlocks.CANDY_CANE_SLAB.get().defaultBlockState();
        for (int px = r7min.getX() + 1; px < r7max.getX(); px++) {
            for (int pz = r7min.getZ() + 1; pz < r7max.getZ(); pz++) {
                if ((px + pz) % 2 == 0) {
                    level.setBlock(new BlockPos(px, groundY + 1, pz), ccSlab, 2);
                }
            }
        }

        // Spawn KingSlime boss
        var kingSlime = ModEntities.KING_SLIME.get().create(level);
        if (kingSlime != null) {
            kingSlime.moveTo(x + 0.5, groundY + 2, r7min.getZ() + r7d / 2.0 + 0.5, 0, 0);
            level.addFreshEntity(kingSlime);
        }

        // Door out
        int r7exitZ = r6exitZ - r7d;
        level.setBlock(new BlockPos(x, groundY + 1, r7exitZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, r7exitZ), air, 2);

        // ═══ Room 8: Reward Room (24x24x24) ═══
        int r8w = 24, r8h = 24, r8d = 24;
        BlockPos r8min = new BlockPos(x - r8w / 2, groundY, r7exitZ - r8d);
        BlockPos r8max = new BlockPos(x + r8w / 2, groundY + r8h, r7exitZ);
        buildBox(level, r8min, r8max, jawBreaker, jawLight, rand, 0.1f);
        fillInterior(level, r8min.offset(1, 1, 1), r8max.offset(-1, -1, -1), air);

        // Door in
        level.setBlock(new BlockPos(x, groundY + 1, r7exitZ), air, 2);
        level.setBlock(new BlockPos(x, groundY + 2, r7exitZ), air, 2);

        // Decorative licorice/caramel walls inside
        BlockState licorice = ModBlocks.LICORICE_BLOCK.get().defaultBlockState();
        BlockState caramel = ModBlocks.CARAMEL_BLOCK.get().defaultBlockState();
        for (int py = groundY + 1; py < groundY + 4; py++) {
            for (int px = r8min.getX() + 1; px < r8max.getX(); px++) {
                if (px % 4 == 0) {
                    level.setBlock(new BlockPos(px, py, r8min.getZ() + 1), licorice, 2);
                    level.setBlock(new BlockPos(px, py, r8max.getZ() - 1), caramel, 2);
                }
            }
        }

        // Reward chest at center
        BlockPos chestPos = new BlockPos(x, groundY + 1, r8min.getZ() + r8d / 2);
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
            if (rand.nextInt(5) == 0) {
                chest.setItem(10, new ItemStack(ModBlocks.DRAGON_EGG_BLOCK.get().asItem()));
            }
        }

        // Teleporter back to dungeon entrance
        BlockPos tpReward = new BlockPos(x + 2, groundY + 1, r8min.getZ() + r8d / 2);
        level.setBlock(tpReward, ModBlocks.TELEPORTER_BLOCK.get().defaultBlockState(), 2);
        BlockEntity tpRewardBe = level.getBlockEntity(tpReward);
        if (tpRewardBe instanceof TeleporterBlockEntity tp) {
            tp.setTarget(origin);
            // Same dimension (candy world), no dimension key needed
        }
    }

    /** Build a hollow box shell with random light blocks mixed in. */
    private static void buildBox(ServerLevel level, BlockPos min, BlockPos max,
                                 BlockState primary, BlockState secondary, Random rand, float secondaryChance) {
        for (int px = min.getX(); px <= max.getX(); px++) {
            for (int py = min.getY(); py <= max.getY(); py++) {
                for (int pz = min.getZ(); pz <= max.getZ(); pz++) {
                    boolean isEdge = px == min.getX() || px == max.getX()
                            || py == min.getY() || py == max.getY()
                            || pz == min.getZ() || pz == max.getZ();
                    if (isEdge) {
                        BlockState state = rand.nextFloat() < secondaryChance ? secondary : primary;
                        level.setBlock(new BlockPos(px, py, pz), state, 2);
                    }
                }
            }
        }
    }

    /** Fill a region with a single block state. */
    private static void fillInterior(ServerLevel level, BlockPos min, BlockPos max, BlockState state) {
        for (int px = min.getX(); px <= max.getX(); px++) {
            for (int py = min.getY(); py <= max.getY(); py++) {
                for (int pz = min.getZ(); pz <= max.getZ(); pz++) {
                    level.setBlock(new BlockPos(px, py, pz), state, 2);
                }
            }
        }
    }
}
