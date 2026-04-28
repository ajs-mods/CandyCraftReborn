package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeIronGolemModel;
import com.ajsmods.candycraftreborn.entity.NougatGolemEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NougatGolemRenderer extends MobRenderer<NougatGolemEntity, SafeIronGolemModel<NougatGolemEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/nougat_golem.png");

    public NougatGolemRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeIronGolemModel<>(ctx.bakeLayer(ModelLayers.IRON_GOLEM)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(NougatGolemEntity entity) {
        return TEXTURE;
    }
}
