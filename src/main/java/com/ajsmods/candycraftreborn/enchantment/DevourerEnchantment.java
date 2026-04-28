package com.ajsmods.candycraftreborn.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

/**
 * Devourer enchantment — applies to swords. When the wielder kills a mob,
 * they are healed. The healing amount scales with enchantment level.
 * Behavior is applied via a LivingDeathEvent handler in {@link DevourerEnchantment#register}.
 */
public class DevourerEnchantment extends Enchantment {

    public DevourerEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int level) {
        return 10 + (level - 1) * 8;
    }

    @Override
    public int getMaxCost(int level) {
        return getMinCost(level) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
