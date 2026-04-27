package com.ajsmods.candycraftreborn.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RedJellyEntity extends JellyEntity {
    public RedJellyEntity(EntityType<? extends Mob> type, Level level) {
        super(type, level);
        this.setJellySize(2);
        this.setRemainingFireTicks(-1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return JellyEntity.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 6.0);
    }

    @Override
    public boolean fireImmune() { return true; }

    @Override
    public void playerTouch(Player player) {
        if (!this.level().isClientSide) {
            if (player.hurt(this.damageSources().mobAttack(this), 6.0F)) {
                this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F,
                        Level.ExplosionInteraction.MOB);
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
                RedJellyEntity child = (RedJellyEntity) this.getType().create(this.level());
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
