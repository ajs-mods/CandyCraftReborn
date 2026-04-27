package com.ajsmods.candycraftreborn;

import com.ajsmods.candycraftreborn.client.screen.AlchemyTableScreen;
import com.ajsmods.candycraftreborn.registry.ModBlockEntities;
import com.ajsmods.candycraftreborn.registry.ModBlocks;
import com.ajsmods.candycraftreborn.registry.ModCreativeTabs;
import com.ajsmods.candycraftreborn.registry.ModEntities;
import com.ajsmods.candycraftreborn.registry.ModItems;
import com.ajsmods.candycraftreborn.registry.ModMenus;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.world.level.block.Blocks;
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
import net.minecraftforge.registries.ForgeRegistries;
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

        MinecraftForge.EVENT_BUS.register(this);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        LOGGER.info("CandyCraft Reborn mod initialized");
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("Running common setup for {}", MODID);

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
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
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.CANDY_CRITTER.get(), CowRenderer::new);
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
