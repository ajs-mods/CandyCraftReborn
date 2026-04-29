package com.ajsmods.candycraftreborn.world;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Custom teleporter for the Candy dimension portal.
 * Places entities at the surface of the target dimension at the same X/Z.
 */
public class CandyTeleporter implements ITeleporter {

    @Override
    @Nullable
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        double x = entity.getX();
        double z = entity.getZ();

        // Force-load the chunk to get accurate heightmap
        BlockPos blockPos = BlockPos.containing(x, 0, z);
        destWorld.getChunk(blockPos);

        // Find the top solid block at this X/Z
        int surfaceY = destWorld.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) x, (int) z);

        // Ensure a minimum Y so we don't spawn inside the void
        double y = Math.max(surfaceY + 1, 70);

        return new PortalInfo(
                new Vec3(x, y, z),
                Vec3.ZERO,
                entity.getYRot(),
                entity.getXRot()
        );
    }

    @Override
    public boolean playTeleportSound(ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
        return false; // We handle sound via the portal block's ambient sound
    }

    @Override
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw,
                               Function<Boolean, Entity> repositionEntity) {
        // Let Forge reposition the entity using our PortalInfo
        Entity repositioned = repositionEntity.apply(false);

        // Apply Resistance 5 for 10 seconds (200 ticks) — mild fall protection just in case
        if (repositioned instanceof ServerPlayer player) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 200, 4, false, false, true));
        }

        return repositioned;
    }
}
