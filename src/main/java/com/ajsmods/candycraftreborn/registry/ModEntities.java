package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Cow;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CandyCraftMod.MODID);

    public static final RegistryObject<EntityType<Cow>> CANDY_CRITTER = ENTITIES.register("candy_critter",
            () -> EntityType.Builder.of(Cow::new, MobCategory.CREATURE)
                    .sized(0.9F, 1.4F)
                    .build(CandyCraftMod.MODID + ":candy_critter"));

    private ModEntities() {
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(CANDY_CRITTER.get(), Cow.createAttributes().build());
    }
}
