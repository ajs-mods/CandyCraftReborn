package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeBeeModel;
import com.ajsmods.candycraftreborn.entity.CandyBeeEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CandyBeeRenderer extends MobRenderer<CandyBeeEntity, SafeBeeModel<CandyBeeEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/candy_bee.png");

    public CandyBeeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeBeeModel<>(ctx.bakeLayer(ModelLayers.BEE)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(CandyBeeEntity entity) {
        return TEXTURE;
    }
}
