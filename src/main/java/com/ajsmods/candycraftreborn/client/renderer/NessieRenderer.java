package com.ajsmods.candycraftreborn.client.renderer;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.client.model.SafeDolphinModel;
import com.ajsmods.candycraftreborn.entity.NessieEntity;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NessieRenderer extends MobRenderer<NessieEntity, SafeDolphinModel<NessieEntity>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CandyCraftMod.MODID, "textures/entity/nessie.png");

    public NessieRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SafeDolphinModel<>(ctx.bakeLayer(ModelLayers.DOLPHIN)), 0.7F);
    }

    @Override
    public ResourceLocation getTextureLocation(NessieEntity entity) {
        return TEXTURE;
    }
}
