package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeSilverfishModel;
import com.ajsmods.candycraftreborn.entity.BeetleEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BeetleRenderer extends MobRenderer<BeetleEntity, SafeSilverfishModel<BeetleEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/beetle.png");

    public BeetleRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeSilverfishModel<>(ctx.bakeLayer(ModelLayers.SILVERFISH)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(BeetleEntity entity) {
        return TEXTURE;
    }
}
