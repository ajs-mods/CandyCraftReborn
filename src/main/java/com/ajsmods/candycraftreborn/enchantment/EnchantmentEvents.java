package com.ajsmods.candycraftreborn.enchantment;

import com.ajsmods.candycraftreborn.CandyCraftMod;
import com.ajsmods.candycraftreborn.registry.ModEnchantments;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CandyCraftMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EnchantmentEvents {

    /**
     * Devourer: when a player kills a mob while holding a weapon with this
     * enchantment, heal the player (2 HP per level).
     */
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.DEVOURER.get(), player);
            if (level > 0) {
                player.heal(2.0F * level);
            }
        }
    }

    /**
     * Honey Glue: when a player wearing armor with this enchantment is hurt,
     * apply Slowness to the attacker. Duration = 40 ticks * level (2s / 4s),
     * amplifier = level - 1.
     */
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof LivingEntity attacker) {
            int level = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.HONEY_GLUE.get(), player);
            if (level > 0) {
                attacker.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40 * level, level - 1));
            }
        }
    }
}
