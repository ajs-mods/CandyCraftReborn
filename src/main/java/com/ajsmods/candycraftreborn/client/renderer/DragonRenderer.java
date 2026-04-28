package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeCowModel;
import com.ajsmods.candycraftreborn.entity.DragonEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DragonRenderer extends MobRenderer<DragonEntity, SafeCowModel<DragonEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/dragon.png");

    public DragonRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeCowModel<>(ctx.bakeLayer(ModelLayers.COW)), 1.5F);
    }

    @Override
    public void render(DragonEntity entity, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int light) {
        poseStack.pushPose();
        poseStack.scale(2.0F, 2.0F, 2.0F);
        super.render(entity, yaw, partialTick, poseStack, buffer, light);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DragonEntity entity) {
        return TEXTURE;
    }
}
