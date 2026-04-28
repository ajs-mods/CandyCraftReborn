package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeSpiderModel;
import com.ajsmods.candycraftreborn.entity.KingBeetleEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class KingBeetleRenderer extends MobRenderer<KingBeetleEntity, SafeSpiderModel<KingBeetleEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/king_beetle.png");

    public KingBeetleRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeSpiderModel<>(ctx.bakeLayer(ModelLayers.SPIDER)), 1.5F);
    }

    @Override
    public void render(KingBeetleEntity entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.scale(2.5F, 2.5F, 2.5F);
        super.render(entity, yaw, partialTick, poseStack, buffer, light);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(KingBeetleEntity entity) {
        return TEXTURE;
    }
}
