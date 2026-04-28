package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WolfRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;

public class CandyWolfRenderer extends WolfRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/candy_wolf.png");

    public CandyWolfRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(Wolf entity) {
        return TEXTURE;
    }
}
