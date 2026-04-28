package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.entity.*;
import com.ajsmods.candycraftreborn.entity.boss.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CandyCraftMod.MODID);

    // ── Passive ──────────────────────────────────────────────────────────────
    public static final RegistryObject<EntityType<CandyPigEntity>> CANDY_PIG = ENTITIES.register("candy_pig",
            () -> EntityType.Builder.of(CandyPigEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 0.9F).build(CandyCraftMod.MODID + ":candy_pig"));

    public static final RegistryObject<EntityType<WaffleSheepEntity>> WAFFLE_SHEEP = ENTITIES.register("waffle_sheep",
            () -> EntityType.Builder.of(WaffleSheepEntity::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.3F).build(CandyCraftMod.MODID + ":waffle_sheep"));

    public static final RegistryObject<EntityType<BunnyEntity>> BUNNY = ENTITIES.register("bunny",
            () -> EntityType.Builder.of(BunnyEntity::new, MobCategory.CREATURE)
                    .sized(0.5F, 0.4F).build(CandyCraftMod.MODID + ":bunny"));

    public static final RegistryObject<EntityType<PingouinEntity>> PINGOUIN = ENTITIES.register("pingouin",
            () -> EntityType.Builder.of(PingouinEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.0F).build(CandyCraftMod.MODID + ":pingouin"));

    public static final RegistryObject<EntityType<CandyFishEntity>> CANDY_FISH = ENTITIES.register("candy_fish",
            () -> EntityType.Builder.of(CandyFishEntity::new, MobCategory.WATER_CREATURE)
                    .sized(0.95F, 0.95F).build(CandyCraftMod.MODID + ":candy_fish"));

    // ── Hostile ──────────────────────────────────────────────────────────────
    public static final RegistryObject<EntityType<CandyCreeperEntity>> CANDY_CREEPER = ENTITIES.register("candy_creeper",
            () -> EntityType.Builder.of(CandyCreeperEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.7F).build(CandyCraftMod.MODID + ":candy_creeper"));

    public static final RegistryObject<EntityType<CottonCandySpiderEntity>> COTTON_CANDY_SPIDER = ENTITIES.register("cotton_candy_spider",
            () -> EntityType.Builder.of(CottonCandySpiderEntity::new, MobCategory.MONSTER)
                    .sized(1.4F, 0.9F).build(CandyCraftMod.MODID + ":cotton_candy_spider"));

    public static final RegistryObject<EntityType<BeetleEntity>> BEETLE = ENTITIES.register("beetle",
            () -> EntityType.Builder.of(BeetleEntity::new, MobCategory.MONSTER)
                    .sized(1.0F, 0.8F).build(CandyCraftMod.MODID + ":beetle"));

    public static final RegistryObject<EntityType<SuguardEntity>> SUGUARD = ENTITIES.register("suguard",
            () -> EntityType.Builder.of(SuguardEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F).build(CandyCraftMod.MODID + ":suguard"));

    public static final RegistryObject<EntityType<CandyBeeEntity>> CANDY_BEE = ENTITIES.register("candy_bee",
            () -> EntityType.Builder.of(CandyBeeEntity::new, MobCategory.MONSTER)
                    .sized(0.8F, 1.0F).build(CandyCraftMod.MODID + ":candy_bee"));

    // ── Jelly ────────────────────────────────────────────────────────────────
    public static final RegistryObject<EntityType<JellyEntity>> JELLY = ENTITIES.register("jelly",
            () -> EntityType.Builder.of(JellyEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 0.6F).build(CandyCraftMod.MODID + ":jelly"));

    public static final RegistryObject<EntityType<YellowJellyEntity>> YELLOW_JELLY = ENTITIES.register("yellow_jelly",
            () -> EntityType.Builder.of(YellowJellyEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 0.6F).build(CandyCraftMod.MODID + ":yellow_jelly"));

    public static final RegistryObject<EntityType<RedJellyEntity>> RED_JELLY = ENTITIES.register("red_jelly",
            () -> EntityType.Builder.of(RedJellyEntity::new, MobCategory.MONSTER)
                    .sized(1.0F, 1.0F).build(CandyCraftMod.MODID + ":red_jelly"));

    public static final RegistryObject<EntityType<TornadoJellyEntity>> TORNADO_JELLY = ENTITIES.register("tornado_jelly",
            () -> EntityType.Builder.of(TornadoJellyEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 0.6F).build(CandyCraftMod.MODID + ":tornado_jelly"));

    // ── Additional Passive / Neutral ─────────────────────────────────────────
    public static final RegistryObject<EntityType<CandyWolfEntity>> CANDY_WOLF = ENTITIES.register("candy_wolf",
            () -> EntityType.Builder.of(CandyWolfEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.85F).build(CandyCraftMod.MODID + ":candy_wolf"));

    public static final RegistryObject<EntityType<GingerBreadManEntity>> GINGER_BREAD_MAN = ENTITIES.register("ginger_bread_man",
            () -> EntityType.Builder.of(GingerBreadManEntity::new, MobCategory.CREATURE)
                    .sized(0.3F, 0.9F).build(CandyCraftMod.MODID + ":ginger_bread_man"));

    public static final RegistryObject<EntityType<NougatGolemEntity>> NOUGAT_GOLEM = ENTITIES.register("nougat_golem",
            () -> EntityType.Builder.of(NougatGolemEntity::new, MobCategory.MISC)
                    .sized(0.65F, 0.75F).build(CandyCraftMod.MODID + ":nougat_golem"));

    // ── Additional Hostile ───────────────────────────────────────────────────
    public static final RegistryObject<EntityType<MageSuguardEntity>> MAGE_SUGUARD = ENTITIES.register("mage_suguard",
            () -> EntityType.Builder.of(MageSuguardEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 0.9F).build(CandyCraftMod.MODID + ":mage_suguard"));

    public static final RegistryObject<EntityType<MermaidEntity>> MERMAID = ENTITIES.register("mermaid",
            () -> EntityType.Builder.of(MermaidEntity::new, MobCategory.MONSTER)
                    .sized(0.95F, 1.0F).build(CandyCraftMod.MODID + ":mermaid"));

    // ── Mounts ───────────────────────────────────────────────────────────────
    public static final RegistryObject<EntityType<NessieEntity>> NESSIE = ENTITIES.register("nessie",
            () -> EntityType.Builder.of(NessieEntity::new, MobCategory.CREATURE)
                    .sized(1.2F, 1.6F).build(CandyCraftMod.MODID + ":nessie"));

    public static final RegistryObject<EntityType<DragonEntity>> DRAGON = ENTITIES.register("dragon",
            () -> EntityType.Builder.of(DragonEntity::new, MobCategory.CREATURE)
                    .sized(3.0F, 2.2F).build(CandyCraftMod.MODID + ":dragon"));

    public static final RegistryObject<EntityType<KingBeetleEntity>> KING_BEETLE = ENTITIES.register("king_beetle",
            () -> EntityType.Builder.of(KingBeetleEntity::new, MobCategory.CREATURE)
                    .sized(3.0F, 2.0F).build(CandyCraftMod.MODID + ":king_beetle"));

    // ── Bosses ────────────────────────────────────────────────────────────────
    public static final RegistryObject<EntityType<JellyQueenEntity>> JELLY_QUEEN = ENTITIES.register("jelly_queen",
            () -> EntityType.Builder.of(JellyQueenEntity::new, MobCategory.MONSTER)
                    .sized(1.8F, 2.4F).fireImmune().build(CandyCraftMod.MODID + ":jelly_queen"));

    public static final RegistryObject<EntityType<PEZJellyEntity>> PEZ_JELLY = ENTITIES.register("pez_jelly",
            () -> EntityType.Builder.of(PEZJellyEntity::new, MobCategory.MONSTER)
                    .sized(1.0F, 1.0F).fireImmune().build(CandyCraftMod.MODID + ":pez_jelly"));

    public static final RegistryObject<EntityType<KingSlimeEntity>> KING_SLIME = ENTITIES.register("king_slime",
            () -> EntityType.Builder.of(KingSlimeEntity::new, MobCategory.MONSTER)
                    .sized(2.0F, 2.0F).fireImmune().build(CandyCraftMod.MODID + ":king_slime"));

    public static final RegistryObject<EntityType<BossSuguardEntity>> BOSS_SUGUARD = ENTITIES.register("boss_suguard",
            () -> EntityType.Builder.of(BossSuguardEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F).fireImmune().build(CandyCraftMod.MODID + ":boss_suguard"));

    public static final RegistryObject<EntityType<BossBeetleEntity>> BOSS_BEETLE = ENTITIES.register("boss_beetle",
            () -> EntityType.Builder.of(BossBeetleEntity::new, MobCategory.MONSTER)
                    .sized(3.0F, 2.5F).fireImmune().build(CandyCraftMod.MODID + ":boss_beetle"));

    // ── Projectiles ──────────────────────────────────────────────────────────
    public static final RegistryObject<EntityType<CandyArrowEntity>> CANDY_ARROW = ENTITIES.register("candy_arrow",
            () -> EntityType.Builder.<CandyArrowEntity>of(CandyArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
                    .build(CandyCraftMod.MODID + ":candy_arrow"));

    public static final RegistryObject<EntityType<DynamiteEntity>> DYNAMITE_ENTITY = ENTITIES.register("dynamite_entity",
            () -> EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                    .build(CandyCraftMod.MODID + ":dynamite_entity"));

    public static final RegistryObject<EntityType<GlueDynamiteEntity>> GLUE_DYNAMITE_ENTITY = ENTITIES.register("glue_dynamite_entity",
            () -> EntityType.Builder.<GlueDynamiteEntity>of(GlueDynamiteEntity::new, MobCategory.MISC)
                    .sized(0.05F, 0.05F).clientTrackingRange(4).updateInterval(10)
                    .build(CandyCraftMod.MODID + ":glue_dynamite_entity"));

    public static final RegistryObject<EntityType<GummyBallEntity>> GUMMY_BALL = ENTITIES.register("gummy_ball",
            () -> EntityType.Builder.<GummyBallEntity>of(GummyBallEntity::new, MobCategory.MISC)
                    .sized(0.45F, 0.45F).clientTrackingRange(4).updateInterval(10)
                    .build(CandyCraftMod.MODID + ":gummy_ball"));

    private ModEntities() {}

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        // Passive
        event.put(CANDY_PIG.get(), CandyPigEntity.createAttributes().build());
        event.put(WAFFLE_SHEEP.get(), WaffleSheepEntity.createAttributes().build());
        event.put(BUNNY.get(), BunnyEntity.createAttributes().build());
        event.put(PINGOUIN.get(), PingouinEntity.createAttributes().build());
        event.put(CANDY_FISH.get(), CandyFishEntity.createAttributes().build());
        // Hostile
        event.put(CANDY_CREEPER.get(), CandyCreeperEntity.createAttributes().build());
        event.put(COTTON_CANDY_SPIDER.get(), CottonCandySpiderEntity.createAttributes().build());
        event.put(BEETLE.get(), BeetleEntity.createAttributes().build());
        event.put(SUGUARD.get(), SuguardEntity.createAttributes().build());
        event.put(CANDY_BEE.get(), CandyBeeEntity.createAttributes().build());
        // Jelly
        event.put(JELLY.get(), JellyEntity.createAttributes().build());
        event.put(YELLOW_JELLY.get(), YellowJellyEntity.createAttributes().build());
        event.put(RED_JELLY.get(), RedJellyEntity.createAttributes().build());
        event.put(TORNADO_JELLY.get(), TornadoJellyEntity.createAttributes().build());
        // Additional passive/neutral
        event.put(CANDY_WOLF.get(), CandyWolfEntity.createAttributes().build());
        event.put(GINGER_BREAD_MAN.get(), GingerBreadManEntity.createAttributes().build());
        event.put(NOUGAT_GOLEM.get(), NougatGolemEntity.createAttributes().build());
        // Additional hostile
        event.put(MAGE_SUGUARD.get(), MageSuguardEntity.createAttributes().build());
        event.put(MERMAID.get(), MermaidEntity.createAttributes().build());
        // Mounts
        event.put(NESSIE.get(), NessieEntity.createAttributes().build());
        event.put(DRAGON.get(), DragonEntity.createAttributes().build());
        event.put(KING_BEETLE.get(), KingBeetleEntity.createAttributes().build());
        // Bosses
        event.put(JELLY_QUEEN.get(), JellyQueenEntity.createAttributes().build());
        event.put(PEZ_JELLY.get(), PEZJellyEntity.createAttributes().build());
        event.put(KING_SLIME.get(), KingSlimeEntity.createAttributes().build());
        event.put(BOSS_SUGUARD.get(), BossSuguardEntity.createAttributes().build());
        event.put(BOSS_BEETLE.get(), BossBeetleEntity.createAttributes().build());
    }
}
