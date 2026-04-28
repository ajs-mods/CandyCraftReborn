package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeRabbitModel;
import com.ajsmods.candycraftreborn.entity.BunnyEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BunnyRenderer extends MobRenderer<BunnyEntity, SafeRabbitModel<BunnyEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/bunny.png");

    public BunnyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeRabbitModel<>(ctx.bakeLayer(ModelLayers.RABBIT)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(BunnyEntity entity) {
        return TEXTURE;
    }
}
