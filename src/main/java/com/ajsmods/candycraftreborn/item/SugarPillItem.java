package com.ajsmods.candycraftreborn.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class SugarPillItem extends Item {
    public static final String TAG_EFFECTS = "AlchemyEffects";

    public SugarPillItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(TAG_EFFECTS)) {
            ListTag effects = tag.getList(TAG_EFFECTS, 8);
            for (int i = 0; i < effects.size(); i++) {
                String id = effects.getString(i);
                if (id.equals("minecraft:instant_health")) entity.heal(4.0F);
                else if (id.equals("minecraft:regeneration")) entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                else if (id.equals("minecraft:speed")) entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0));
                else if (id.equals("minecraft:haste")) entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 400, 0));
                else if (id.equals("minecraft:strength")) entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0));
                else if (id.equals("minecraft:night_vision")) entity.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 600, 0));
                else if (id.equals("minecraft:invisibility")) entity.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 400, 0));
                else if (id.equals("minecraft:water_breathing")) entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 400, 0));
                else if (id.equals("minecraft:fire_resistance")) entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 400, 0));
                else if (id.equals("minecraft:jump_boost")) entity.addEffect(new MobEffectInstance(MobEffects.JUMP, 400, 0));
                else if (id.equals("minecraft:resistance")) entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0));
                else if (id.equals("minecraft:absorption")) entity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 400, 0));
            }
        }
        stack.shrink(1);
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains(TAG_EFFECTS)) {
            ListTag effects = tag.getList(TAG_EFFECTS, 8);
            for (int i = 0; i < effects.size(); i++) {
                String id = effects.getString(i).replace("minecraft:", "");
                tooltip.add(Component.literal("+ " + id.replace("_", " ")).withStyle(ChatFormatting.LIGHT_PURPLE));
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public net.minecraft.world.item.UseAnim getUseAnimation(ItemStack stack) {
        return net.minecraft.world.item.UseAnim.EAT;
    }
}
