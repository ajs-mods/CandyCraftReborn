package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.menu.AlchemyTableMenu;
import com.ajsmods.candycraftreborn.menu.SugarFactoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, CandyCraftMod.MODID);

    public static final RegistryObject<MenuType<AlchemyTableMenu>> ALCHEMY_TABLE_MENU =
            MENUS.register("alchemy_table", () -> IForgeMenuType.create(AlchemyTableMenu::new));

    public static final RegistryObject<MenuType<SugarFactoryMenu>> SUGAR_FACTORY_MENU =
            MENUS.register("sugar_factory", () -> IForgeMenuType.create(SugarFactoryMenu::new));

    private ModMenus() {
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
