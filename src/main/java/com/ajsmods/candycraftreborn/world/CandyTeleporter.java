package com.ajsmods.candycraftreborn.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.function.Function;

/**
 * Custom teleporter for the Candy dimension portal.
 * Places entities at same X/Z, Y=200 with Resistance 30 for fall protection.
 */
public class CandyTeleporter implements ITeleporter {

    @Override
    @Nullable
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        // Place at same X/Z, Y=200 in the target dimension
        double x = entity.getX();
        double z = entity.getZ();
        double y = 200.0;

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

        // Apply Resistance 30 for 15 seconds (300 ticks) — fall protection like legacy
        if (repositioned instanceof ServerPlayer player) {
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 300, 29, false, false, true));
        }

        return repositioned;
    }
}
