package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Spider;

public class CottonCandySpiderRenderer extends SpiderRenderer<Spider> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/cotton_candy_spider.png");

    public CottonCandySpiderRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(Spider entity) {
        return TEXTURE;
    }
}
