package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeSheepModel;
import com.ajsmods.candycraftreborn.entity.WaffleSheepEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WaffleSheepRenderer extends MobRenderer<WaffleSheepEntity, SafeSheepModel<WaffleSheepEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/waffle_sheep.png");

    public WaffleSheepRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeSheepModel<>(ctx.bakeLayer(ModelLayers.SHEEP)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(WaffleSheepEntity entity) {
        return TEXTURE;
    }
}
