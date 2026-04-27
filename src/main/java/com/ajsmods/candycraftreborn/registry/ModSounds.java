package com.ajsmods.candycraftreborn.registry;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CandyCraftMod.MODID);

    public static final RegistryObject<SoundEvent> NESSIE_AMBIENT = registerSound("entity.nessie.ambient");
    public static final RegistryObject<SoundEvent> NESSIE_HURT = registerSound("entity.nessie.hurt");
    public static final RegistryObject<SoundEvent> RECORD_1 = registerSound("music.record_1");
    public static final RegistryObject<SoundEvent> RECORD_2 = registerSound("music.record_2");
    public static final RegistryObject<SoundEvent> RECORD_3 = registerSound("music.record_3");
    public static final RegistryObject<SoundEvent> RECORD_4 = registerSound("music.record_4");

    private ModSounds() {}

    private static RegistryObject<SoundEvent> registerSound(String name) {
        ResourceLocation id = new ResourceLocation(CandyCraftMod.MODID, name);
        return SOUNDS.register(name.replace('.', '_'), () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
