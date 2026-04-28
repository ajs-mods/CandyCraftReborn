package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.client.model.SheepModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"unchecked", "rawtypes"})
public class WaffleSheepRenderer extends MobRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/waffle_sheep.png");

    public WaffleSheepRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SheepModel(ctx.bakeLayer(ModelLayers.SHEEP)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.world.entity.Entity entity) {
        return TEXTURE;
    }
}
