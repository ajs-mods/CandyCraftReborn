package com.ajsmods.candycraftreborn;

import com.ajsmods.candycraftreborn.client.renderer.*;
import com.ajsmods.candycraftreborn.client.screen.AlchemyTableScreen;
import com.ajsmods.candycraftreborn.recipe.AlchemyRecipes;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModCreativeTabs;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import com.ajsmods.candycraftreborn.registry.ModMenus;
import com.ajsmods.candycraftreborn.registry.ModSounds;
import com.ajsmods.candycraftreborn.registry.ModStructures;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CandyCraftMod.MODID)
public class CandyCraftMod
{
    public static final String MODID = "candycraftreborn";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CandyCraftMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(ModEntities::registerAttributes);

        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModSounds.register(modEventBus);
        ModStructures.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("CandyCraft Reborn mod initialized");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Running common setup for {}", MODID);
        event.enqueueWork(AlchemyRecipes::init);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        LOGGER.info("Server starting with mod {}", MODID);
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        @SuppressWarnings({"unchecked", "rawtypes"})
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            // Passive mobs — custom retextured vanilla renderers
            event.registerEntityRenderer(ModEntities.CANDY_PIG.get(), CandyPigRenderer::new);
            event.registerEntityRenderer(ModEntities.WAFFLE_SHEEP.get(), (EntityRendererProvider) WaffleSheepRenderer::new);
            event.registerEntityRenderer(ModEntities.CANDY_FISH.get(), (EntityRendererProvider) CandyFishRenderer::new);
            event.registerEntityRenderer(ModEntities.CANDY_WOLF.get(), CandyWolfRenderer::new);

            // Hostile — custom retextured vanilla renderers
            event.registerEntityRenderer(ModEntities.CANDY_CREEPER.get(), CandyCreeperRenderer::new);
            event.registerEntityRenderer(ModEntities.COTTON_CANDY_SPIDER.get(), CottonCandySpiderRenderer::new);

            // Unique mobs — vanilla model-based renderers with custom textures
            event.registerEntityRenderer(ModEntities.BUNNY.get(), (EntityRendererProvider) BunnyRenderer::new);
            event.registerEntityRenderer(ModEntities.PINGOUIN.get(), (EntityRendererProvider) PingouinRenderer::new);
            event.registerEntityRenderer(ModEntities.BEETLE.get(), (EntityRendererProvider) BeetleRenderer::new);
            event.registerEntityRenderer(ModEntities.CANDY_BEE.get(), (EntityRendererProvider) CandyBeeRenderer::new);
            event.registerEntityRenderer(ModEntities.NESSIE.get(), (EntityRendererProvider) NessieRenderer::new);
            event.registerEntityRenderer(ModEntities.DRAGON.get(), (EntityRendererProvider) DragonRenderer::new);
            event.registerEntityRenderer(ModEntities.KING_BEETLE.get(), (EntityRendererProvider) KingBeetleRenderer::new);
            event.registerEntityRenderer(ModEntities.NOUGAT_GOLEM.get(), (EntityRendererProvider) NougatGolemRenderer::new);

            // Humanoid mobs
            event.registerEntityRenderer(ModEntities.SUGUARD.get(),
                    (EntityRendererProvider) ctx -> new CandyHumanoidRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/suguard.png")));
            event.registerEntityRenderer(ModEntities.MAGE_SUGUARD.get(),
                    (EntityRendererProvider) ctx -> new CandyHumanoidRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/mage_suguard.png")));
            event.registerEntityRenderer(ModEntities.GINGER_BREAD_MAN.get(),
                    (EntityRendererProvider) ctx -> new CandyHumanoidRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/ginger_bread_man.png")));
            event.registerEntityRenderer(ModEntities.MERMAID.get(),
                    (EntityRendererProvider) ctx -> new CandyHumanoidRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/mermaid.png")));

            // Jelly mobs — slime model
            event.registerEntityRenderer(ModEntities.JELLY.get(),
                    ctx -> new JellyRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/jelly.png")));
            event.registerEntityRenderer(ModEntities.YELLOW_JELLY.get(),
                    ctx -> new JellyRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/yellow_jelly.png")));
            event.registerEntityRenderer(ModEntities.RED_JELLY.get(),
                    ctx -> new JellyRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/red_jelly.png")));
            event.registerEntityRenderer(ModEntities.TORNADO_JELLY.get(),
                    ctx -> new JellyRenderer<>(ctx, new ResourceLocation(MODID, "textures/entity/tornado_jelly.png")));

            // Projectiles
            event.registerEntityRenderer(ModEntities.CANDY_ARROW.get(), (EntityRendererProvider) TippableArrowRenderer::new);
            event.registerEntityRenderer(ModEntities.DYNAMITE_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(ModEntities.GLUE_DYNAMITE_ENTITY.get(), ThrownItemRenderer::new);
            event.registerEntityRenderer(ModEntities.GUMMY_BALL.get(), ThrownItemRenderer::new);
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            LOGGER.info("Client setup for {}", MODID);
            event.enqueueWork(() -> MenuScreens.register(ModMenus.ALCHEMY_TABLE_MENU.get(), AlchemyTableScreen::new));
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
