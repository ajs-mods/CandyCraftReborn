package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"unchecked", "rawtypes"})
public class NessieRenderer extends MobRenderer {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/nessie.png");

    public NessieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DolphinModel(ctx.bakeLayer(ModelLayers.DOLPHIN)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(net.minecraft.world.entity.Entity entity) {
        return TEXTURE;
    }
}
