package com.ajsmods.candycraftreborn.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class YellowJellyEntity extends JellyEntity {
    public YellowJellyEntity(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.setJellySize(1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return JellyEntity.createAttributes();
    }

    @Override
    protected int getJumpDelay() { return 4; }

    @Override
    public void playerTouch(Player player) {
        if (!this.level().isClientSide) {
            player.hurt(this.damageSources().mobAttack(this), 6.0F);
            this.playSound(SoundEvents.SLIME_ATTACK, 1.0F, 1.0F);
        }
    }
}
