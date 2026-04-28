package com.ajsmods.candycraftreborn.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * Honey Glue enchantment — applies to armor. When the wearer is attacked,
 * the attacker is slowed. The slowness duration/amplifier scales with level.
 * Behavior is applied via a LivingHurtEvent handler in the event subscriber.
 */
public class HoneyGlueEnchantment extends Enchantment {

    public HoneyGlueEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.ARMOR, new EquipmentSlot[]{
                EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
        });
    }

    @Override
    public int getMinCost(int level) {
        return 15 + (level - 1) * 10;
    }

    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 25;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }
}
