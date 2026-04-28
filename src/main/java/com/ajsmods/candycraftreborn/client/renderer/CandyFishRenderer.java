package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CandyFishRenderer extends MobRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/candy_fish.png");

    public CandyFishRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new CodModel(ctx.bakeLayer(ModelLayers.COD)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.world.entity.Entity entity) {
        return TEXTURE;
    }
}
