package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CandyCreeperEntity extends Creeper {

    private static final EntityDataAccessor<Integer> MEGA_FUSE =
            SynchedEntityData.defineId(CandyCreeperEntity.class, EntityDataSerializers.INT);

    private boolean megaMode = false;

    public CandyCreeperEntity(EntityType<? extends Creeper> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MEGA_FUSE, -1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Creeper.createAttributes()
                .add(Attributes.MAX_HEALTH, 5.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) return false;
        return super.canBeAffected(effect);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ModItems.LOLLIPOP.get()) && !megaMode) {
            if (!player.getAbilities().instabuild) stack.shrink(1);
            if (!this.level().isClientSide) {
                megaMode = true;
                this.entityData.set(MEGA_FUSE, 60);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isInvulnerable() {
        return megaMode || super.isInvulnerable();
    }

    @Override
    public void tick() {
        super.tick();
        int fuse = this.entityData.get(MEGA_FUSE);
        if (fuse > 0) {
            this.entityData.set(MEGA_FUSE, fuse - 1);

            // Particles during countdown
            if (this.level().isClientSide) {
                for (int i = 0; i < 3; i++) {
                    this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                            this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5),
                            0.0, 0.05, 0.0);
                }
            }
        } else if (fuse == 0 && !this.level().isClientSide) {
            // Mega explosion
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 6.0F, Level.ExplosionInteraction.MOB);
            this.spawnAtLocation(new ItemStack(Items.COOKIE, 32));
            this.discard();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("MegaMode", megaMode);
        tag.putInt("MegaFuse", this.entityData.get(MEGA_FUSE));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        megaMode = tag.getBoolean("MegaMode");
        this.entityData.set(MEGA_FUSE, tag.getInt("MegaFuse"));
    }
}
