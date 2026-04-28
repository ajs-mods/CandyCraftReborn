package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeChickenModel;
import com.ajsmods.candycraftreborn.entity.PingouinEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PingouinRenderer extends MobRenderer<PingouinEntity, SafeChickenModel<PingouinEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/pingouin.png");

    public PingouinRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeChickenModel<>(ctx.bakeLayer(ModelLayers.CHICKEN)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(PingouinEntity entity) {
        return TEXTURE;
    }
}
