package com.ajsmods.candycraftreborn;

import com.ajsmods.candycraftreborn.client.NoopRenderer;
import com.ajsmods.candycraftreborn.client.screen.AlchemyTableScreen;
import com.ajsmods.candycraftreborn.recipe.AlchemyRecipes;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModCreativeTabs;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import com.ajsmods.candycraftreborn.registry.ModMenus;
import com.ajsmods.candycraftreborn.registry.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.*;
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
        @SuppressWarnings("unchecked")
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            // Passive mobs that extend vanilla types directly
            event.registerEntityRenderer(ModEntities.CANDY_PIG.get(), PigRenderer::new);
            event.registerEntityRenderer(ModEntities.WAFFLE_SHEEP.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.CANDY_FISH.get(), NoopRenderer::new);
            // Hostile extending vanilla types
            event.registerEntityRenderer(ModEntities.CANDY_CREEPER.get(), CreeperRenderer::new);
            event.registerEntityRenderer(ModEntities.COTTON_CANDY_SPIDER.get(), SpiderRenderer::new);
            // Everything else uses invisible placeholder until custom models are added
            // These will spawn but render as invisible - use /summon to test AI
            event.registerEntityRenderer(ModEntities.BUNNY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.PINGOUIN.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.BEETLE.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.SUGUARD.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.CANDY_BEE.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.JELLY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.YELLOW_JELLY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.RED_JELLY.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.TORNADO_JELLY.get(), NoopRenderer::new);
            // Wave 2 mobs
            event.registerEntityRenderer(ModEntities.CANDY_WOLF.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.GINGER_BREAD_MAN.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.NOUGAT_GOLEM.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.MAGE_SUGUARD.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.MERMAID.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.NESSIE.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.DRAGON.get(), NoopRenderer::new);
            event.registerEntityRenderer(ModEntities.KING_BEETLE.get(), NoopRenderer::new);
            // Projectiles
            event.registerEntityRenderer(ModEntities.CANDY_ARROW.get(), NoopRenderer::new);
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
