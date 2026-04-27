package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.item.SugarPillItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CandyCraftMod.MODID);

    public static final RegistryObject<Item> PEPPERMINT = ITEMS.register("peppermint",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build())));

    public static final RegistryObject<Item> CARAMEL_SHARD = ITEMS.register("caramel_shard",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SOUR_GEM = ITEMS.register("sour_gem",
        () -> new Item(new Item.Properties().fireResistant()));

    public static final RegistryObject<Item> LICORICE = ITEMS.register("licorice",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(6).saturationMod(0.6F).alwaysEat().build())));

    public static final RegistryObject<Item> CANDY_CANE = ITEMS.register("candy_cane",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).alwaysEat().build())));

    public static final RegistryObject<Item> LOLLIPOP = ITEMS.register("lollipop",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).alwaysEat().build())));

    public static final RegistryObject<Item> GUMMY = ITEMS.register("gummy",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(4).saturationMod(0.5F).build())));

    public static final RegistryObject<Item> HOT_GUMMY = ITEMS.register("hot_gummy",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.7F).build())));

    public static final RegistryObject<Item> COTTON_CANDY = ITEMS.register("cotton_candy",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.2F).alwaysEat().build())));

    public static final RegistryObject<Item> HONEY_SHARD = ITEMS.register("honey_shard",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HONEYCOMB = ITEMS.register("honeycomb",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHOCOLATE_COIN = ITEMS.register("chocolate_coin",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    public static final RegistryObject<Item> SUGAR_CRYSTAL = ITEMS.register("sugar_crystal",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<SugarPillItem> SUGAR_PILL = ITEMS.register("sugar_pill",
        () -> new SugarPillItem(new Item.Properties().stacksTo(16)));

    // ── Batch 2 items ────────────────────────────────────────────────────────
    public static final RegistryObject<Item> DRAGIBUS = ITEMS.register("dragibus",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.4F).alwaysEat().build())));

    public static final RegistryObject<Item> WAFFLE = ITEMS.register("waffle",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build())));

    public static final RegistryObject<Item> WAFFLE_NUGGET = ITEMS.register("waffle_nugget",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build())));

    public static final RegistryObject<Item> CARAMEL_BUCKET = ITEMS.register("caramel_bucket",
        () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PEZ = ITEMS.register("pez",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PEZ_DUST = ITEMS.register("pez_dust",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> NOUGAT_POWDER = ITEMS.register("nougat_powder",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> MARSHMALLOW_STICK = ITEMS.register("marshmallow_stick",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CRANBERRY_FISH = ITEMS.register("cranberry_fish",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).build())));

    public static final RegistryObject<Item> CRANBERRY_FISH_COOKED = ITEMS.register("cranberry_fish_cooked",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(7).saturationMod(0.8F).build())));

    public static final RegistryObject<Item> CANDIED_CHERRY = ITEMS.register("candied_cherry",
        () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(3).saturationMod(0.5F).alwaysEat().build())));

    public static final RegistryObject<Item> GUMMY_BALL = ITEMS.register("gummy_ball",
        () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CANDY_CRITTER_SPAWN_EGG = ITEMS.register("candy_critter_spawn_egg",
        () -> new ForgeSpawnEggItem(ModEntities.CANDY_CRITTER, 0xB26BFF, 0xFFF6BD, new Item.Properties()));

    // ── Marshmallow Tools ────────────────────────────────────────────────────
    public static final RegistryObject<Item> MARSHMALLOW_SWORD = ITEMS.register("marshmallow_sword",
        () -> new SwordItem(ModToolTiers.MARSHMALLOW, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> MARSHMALLOW_PICKAXE = ITEMS.register("marshmallow_pickaxe",
        () -> new PickaxeItem(ModToolTiers.MARSHMALLOW, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> MARSHMALLOW_AXE = ITEMS.register("marshmallow_axe",
        () -> new AxeItem(ModToolTiers.MARSHMALLOW, 6.0F, -3.2F, new Item.Properties()));
    public static final RegistryObject<Item> MARSHMALLOW_SHOVEL = ITEMS.register("marshmallow_shovel",
        () -> new ShovelItem(ModToolTiers.MARSHMALLOW, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> MARSHMALLOW_HOE = ITEMS.register("marshmallow_hoe",
        () -> new HoeItem(ModToolTiers.MARSHMALLOW, 0, -3.0F, new Item.Properties()));

    // ── Licorice Tools ───────────────────────────────────────────────────────
    public static final RegistryObject<Item> LICORICE_SWORD = ITEMS.register("licorice_sword",
        () -> new SwordItem(ModToolTiers.LICORICE, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_PICKAXE = ITEMS.register("licorice_pickaxe",
        () -> new PickaxeItem(ModToolTiers.LICORICE, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_AXE = ITEMS.register("licorice_axe",
        () -> new AxeItem(ModToolTiers.LICORICE, 6.0F, -3.1F, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_SHOVEL = ITEMS.register("licorice_shovel",
        () -> new ShovelItem(ModToolTiers.LICORICE, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_HOE = ITEMS.register("licorice_hoe",
        () -> new HoeItem(ModToolTiers.LICORICE, -1, -2.0F, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_SPEAR = ITEMS.register("licorice_spear",
        () -> new SwordItem(ModToolTiers.LICORICE, 4, -2.6F, new Item.Properties()));

    // ── Honey Tools ──────────────────────────────────────────────────────────
    public static final RegistryObject<Item> HONEY_SWORD = ITEMS.register("honey_sword",
        () -> new SwordItem(ModToolTiers.HONEY, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_PICKAXE = ITEMS.register("honey_pickaxe",
        () -> new PickaxeItem(ModToolTiers.HONEY, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_AXE = ITEMS.register("honey_axe",
        () -> new AxeItem(ModToolTiers.HONEY, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_SHOVEL = ITEMS.register("honey_shovel",
        () -> new ShovelItem(ModToolTiers.HONEY, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_HOE = ITEMS.register("honey_hoe",
        () -> new HoeItem(ModToolTiers.HONEY, -3, 0.0F, new Item.Properties()));

    // ── PEZ Tools ────────────────────────────────────────────────────────────
    public static final RegistryObject<Item> PEZ_SWORD = ITEMS.register("pez_sword",
        () -> new SwordItem(ModToolTiers.PEZ, 3, -2.4F, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_PICKAXE = ITEMS.register("pez_pickaxe",
        () -> new PickaxeItem(ModToolTiers.PEZ, 1, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_AXE = ITEMS.register("pez_axe",
        () -> new AxeItem(ModToolTiers.PEZ, 5.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_SHOVEL = ITEMS.register("pez_shovel",
        () -> new ShovelItem(ModToolTiers.PEZ, 1.5F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_HOE = ITEMS.register("pez_hoe",
        () -> new HoeItem(ModToolTiers.PEZ, -4, 0.0F, new Item.Properties()));

    // ── Weapons ──────────────────────────────────────────────────────────────
    public static final RegistryObject<Item> FORK = ITEMS.register("fork",
        () -> new SwordItem(ModToolTiers.LICORICE, 2, -2.2F, new Item.Properties()));
    public static final RegistryObject<Item> DRAGIBUS_STICK = ITEMS.register("dragibus_stick",
        () -> new Item(new Item.Properties().stacksTo(1).durability(64)));
    public static final RegistryObject<Item> JELLY_WAND = ITEMS.register("jelly_wand",
        () -> new Item(new Item.Properties().stacksTo(1).durability(128)));
    public static final RegistryObject<Item> JUMP_WAND = ITEMS.register("jump_wand",
        () -> new Item(new Item.Properties().stacksTo(1).durability(128)));
    public static final RegistryObject<Item> CARAMEL_BOW = ITEMS.register("caramel_bow",
        () -> new Item(new Item.Properties().stacksTo(1).durability(384)));
    public static final RegistryObject<Item> CARAMEL_CROSSBOW = ITEMS.register("caramel_crossbow",
        () -> new Item(new Item.Properties().stacksTo(1).durability(326)));

    // ── Licorice Armor ───────────────────────────────────────────────────────
    public static final RegistryObject<Item> LICORICE_HELMET = ITEMS.register("licorice_helmet",
        () -> new ArmorItem(ModArmorMaterials.LICORICE, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_PLATE = ITEMS.register("licorice_plate",
        () -> new ArmorItem(ModArmorMaterials.LICORICE, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_LEGGINGS = ITEMS.register("licorice_leggings",
        () -> new ArmorItem(ModArmorMaterials.LICORICE, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> LICORICE_BOOTS = ITEMS.register("licorice_boots",
        () -> new ArmorItem(ModArmorMaterials.LICORICE, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ── Honey Armor ──────────────────────────────────────────────────────────
    public static final RegistryObject<Item> HONEY_HELMET = ITEMS.register("honey_helmet",
        () -> new ArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_PLATE = ITEMS.register("honey_plate",
        () -> new ArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_LEGGINGS = ITEMS.register("honey_leggings",
        () -> new ArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> HONEY_BOOTS = ITEMS.register("honey_boots",
        () -> new ArmorItem(ModArmorMaterials.HONEY, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ── PEZ Armor ────────────────────────────────────────────────────────────
    public static final RegistryObject<Item> PEZ_HELMET = ITEMS.register("pez_helmet",
        () -> new ArmorItem(ModArmorMaterials.PEZ, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_PLATE = ITEMS.register("pez_plate",
        () -> new ArmorItem(ModArmorMaterials.PEZ, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_LEGGINGS = ITEMS.register("pez_leggings",
        () -> new ArmorItem(ModArmorMaterials.PEZ, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> PEZ_BOOTS = ITEMS.register("pez_boots",
        () -> new ArmorItem(ModArmorMaterials.PEZ, ArmorItem.Type.BOOTS, new Item.Properties()));

    // ── Special Armor ────────────────────────────────────────────────────────
    public static final RegistryObject<Item> JELLY_CROWN = ITEMS.register("jelly_crown",
        () -> new ArmorItem(ModArmorMaterials.JELLY_CROWN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> JELLY_BOOTS = ITEMS.register("jelly_boots",
        () -> new ArmorItem(ModArmorMaterials.JELLY_BOOTS, ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> WATER_MASK = ITEMS.register("water_mask",
        () -> new ArmorItem(ModArmorMaterials.WATER_MASK, ArmorItem.Type.HELMET, new Item.Properties()));

    // ── Keys ─────────────────────────────────────────────────────────────────
    public static final RegistryObject<Item> JELLY_KEY = ITEMS.register("jelly_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUGUARD_KEY = ITEMS.register("suguard_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SKY_KEY = ITEMS.register("sky_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BEETLE_KEY = ITEMS.register("beetle_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JELLY_SENTRY_KEY = ITEMS.register("jelly_sentry_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JELLY_BOSS_KEY = ITEMS.register("jelly_boss_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUGUARD_SENTRY_KEY = ITEMS.register("suguard_sentry_key",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUGUARD_BOSS_KEY = ITEMS.register("suguard_boss_key",
        () -> new Item(new Item.Properties().stacksTo(1)));

    // ── Emblems ──────────────────────────────────────────────────────────────
    public static final RegistryObject<Item> HONEY_EMBLEM = ITEMS.register("honey_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JELLY_EMBLEM = ITEMS.register("jelly_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SUGUARD_EMBLEM = ITEMS.register("suguard_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CRANBERRY_EMBLEM = ITEMS.register("cranberry_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GINGERBREAD_EMBLEM = ITEMS.register("gingerbread_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WATER_EMBLEM = ITEMS.register("water_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CHEWING_GUM_EMBLEM = ITEMS.register("chewing_gum_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> SKY_EMBLEM = ITEMS.register("sky_emblem",
        () -> new Item(new Item.Properties().stacksTo(1)));

    // ── Projectiles ──────────────────────────────────────────────────────────
    public static final RegistryObject<Item> HONEY_ARROW = ITEMS.register("honey_arrow",
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HONEY_BOLT = ITEMS.register("honey_bolt",
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DYNAMITE = ITEMS.register("dynamite",
        () -> new Item(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GLUE_DYNAMITE = ITEMS.register("glue_dynamite",
        () -> new Item(new Item.Properties().stacksTo(16)));

    // ── Music Discs ──────────────────────────────────────────────────────────
    public static final RegistryObject<Item> RECORD_1 = ITEMS.register("record_1",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RECORD_2 = ITEMS.register("record_2",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RECORD_3 = ITEMS.register("record_3",
        () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RECORD_4 = ITEMS.register("record_4",
        () -> new Item(new Item.Properties().stacksTo(1)));

    // ── Misc ─────────────────────────────────────────────────────────────────
    public static final RegistryObject<Item> LOLLIPOP_SEEDS = ITEMS.register("lollipop_seeds",
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRANBERRY_SCALE = ITEMS.register("cranberry_scale",
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHEWING_GUM = ITEMS.register("chewing_gum",
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MARSHMALLOW_FLOWER = ITEMS.register("marshmallow_flower",
        () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRENADINE_BUCKET = ITEMS.register("grenadine_bucket",
        () -> new Item(new Item.Properties().stacksTo(1)));

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
