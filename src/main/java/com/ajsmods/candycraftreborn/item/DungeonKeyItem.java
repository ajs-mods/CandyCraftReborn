package com.ajsmods.candycraftreborn.item;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.blockentity.TeleporterBlockEntity;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.world.dungeon.DungeonType;
import com.ajsmods.candycraftreborn.world.dungeon.SlimeDungeonGenerator;
import com.ajsmods.candycraftreborn.world.dungeon.SuguardDungeonGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Random;

public class DungeonKeyItem extends Item {
    private static final ResourceKey<Level> CANDY_WORLD = ResourceKey.create(Registries.DIMENSION,
            new ResourceLocation(CandyCraftMod.MODID, "candy_world"));

    private final DungeonType dungeonType;

    public DungeonKeyItem(DungeonType dungeonType, Properties properties) {
        super(properties);
        this.dungeonType = dungeonType;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        BlockPos clickedPos = context.getClickedPos();
        BlockPos teleporterPos = clickedPos.above();

        MinecraftServer server = level.getServer();
        if (server == null) return InteractionResult.FAIL;

        ServerLevel candyWorld = server.getLevel(CANDY_WORLD);
        if (candyWorld == null) {
            if (context.getPlayer() != null) {
                context.getPlayer().displayClientMessage(
                        Component.literal("Candy World dimension not found!"), true);
            }
            return InteractionResult.FAIL;
        }

        // Calculate dungeon origin with random offset to avoid overlaps
        Random rand = new Random();
        int offsetX = rand.nextInt(1000) * 1000;
        BlockPos dungeonOrigin = new BlockPos(
                dungeonType.getBaseX() + offsetX,
                65,
                dungeonType.getBaseZ()
        );

        // Generate the dungeon in the candy world
        if (context.getPlayer() != null) {
            context.getPlayer().displayClientMessage(
                    Component.literal("Generating dungeon..."), true);
        }

        if (dungeonType == DungeonType.SLIME) {
            SlimeDungeonGenerator.generate(candyWorld, dungeonOrigin);
        } else {
            SuguardDungeonGenerator.generate(candyWorld, dungeonOrigin);
        }

        // Place teleporter at the clicked position (in the current dimension)
        level.setBlock(teleporterPos, ModBlocks.TELEPORTER_BLOCK.get().defaultBlockState(), 3);
        BlockEntity be = level.getBlockEntity(teleporterPos);
        if (be instanceof TeleporterBlockEntity teleporter) {
            teleporter.setTarget(dungeonOrigin);
            teleporter.setTargetDimension(CANDY_WORLD);
        }

        // Consume the key
        context.getItemInHand().shrink(1);

        if (context.getPlayer() != null) {
            context.getPlayer().displayClientMessage(
                    Component.literal("Dungeon ready! Step on the teleporter."), true);
        }

        return InteractionResult.CONSUME;
    }
}
