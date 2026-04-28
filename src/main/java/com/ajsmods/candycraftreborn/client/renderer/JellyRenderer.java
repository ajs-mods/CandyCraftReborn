package com.ajsmods.candycraftreborn.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class JellyRenderer<T extends Mob> extends MobRenderer<T, SlimeModel<T>> {
    private final ResourceLocation texture;

    public JellyRenderer(EntityRendererProvider.Context ctx, ResourceLocation texture) {
        super(ctx, new SlimeModel<>(ctx.bakeLayer(ModelLayers.SLIME)), 0.25F);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }

    @Override
    public void render(T entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        // Slime model renders at size 1 by default; scale to entity size
        poseStack.scale(0.999F, 0.999F, 0.999F);
        super.render(entity, yaw, partialTick, poseStack, buffer, light);
        poseStack.popPose();
    }
}
