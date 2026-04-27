package com.ajsmods.candycraftreborn.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class TornadoJellyEntity extends JellyEntity {
    public float spinCount = 0.1F;

    public TornadoJellyEntity(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.setJellySize(1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return JellyEntity.createAttributes();
    }

    @Override
    public boolean fireImmune() { return true; }

    @Override
    public void tick() {
        super.tick();
        if (!this.onGround()) {
            this.spinCount += 50;
        } else {
            this.spinCount = 0;
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (!this.level().isClientSide) {
            if (player.hurt(this.damageSources().mobAttack(this), 6.0F)) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 1.0F,
                        true, Level.ExplosionInteraction.MOB);
                this.discard();
            }
        }
    }

    @Override
    public void die(net.minecraft.world.damagesource.DamageSource source) {
        int size = this.getJellySize();
        if (!this.level().isClientSide && size > 1) {
            int count = 2 + this.random.nextInt(3);
            for (int k = 0; k < count; k++) {
                TornadoJellyEntity child = (TornadoJellyEntity) this.getType().create(this.level());
                if (child != null) {
                    child.setJellySize(size / 2);
                    double ox = (k % 2 - 0.5) * size / 4.0;
                    double oz = (k / 2 - 0.5) * size / 4.0;
                    child.moveTo(this.getX() + ox, this.getY() + 0.5, this.getZ() + oz,
                            this.random.nextFloat() * 360.0F, 0.0F);
                    this.level().addFreshEntity(child);
                }
            }
        }
        super.die(source);
    }
}
