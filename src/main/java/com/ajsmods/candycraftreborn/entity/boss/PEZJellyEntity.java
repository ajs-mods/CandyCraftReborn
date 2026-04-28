package com.ajsmods.candycraftreborn.entity.boss;

import com.ajsmods.candycraftreborn.registry.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class PEZJellyEntity extends Monster {
    private static final EntityDataAccessor<Boolean> AWAKE =
            SynchedEntityData.defineId(PEZJellyEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> JELLY_SIZE =
            SynchedEntityData.defineId(PEZJellyEntity.class, EntityDataSerializers.INT);

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS);

    private int jumpDelay = 0;

    public PEZJellyEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.moveControl = new PEZMoveControl(this);
        this.xpReward = 40;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AWAKE, false);
        this.entityData.define(JELLY_SIZE, 10);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PEZJumpGoal(this));
        this.goalSelector.addGoal(2, new PEZAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 200.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.6);
    }

    public boolean isAwake() { return this.entityData.get(AWAKE); }
    public void setAwake(boolean awake) { this.entityData.set(AWAKE, awake); }

    public int getJellySize() { return this.entityData.get(JELLY_SIZE); }
    public void setJellySize(int size) {
        this.entityData.set(JELLY_SIZE, size);
        this.refreshDimensions();
        float scale = size / 10.0F;
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(200.0 * scale);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(8.0 * scale);
        this.setHealth(this.getMaxHealth());
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        float scale = getJellySize() / 10.0F;
        return EntityDimensions.scalable(1.0F * scale, 1.0F * scale);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.isIndirect()) return false;
        if (!isAwake()) setAwake(true);
        return super.hurt(source, amount);
    }

    @Override
    public void die(DamageSource source) {
        if (!this.level().isClientSide && getJellySize() > 1) {
            PEZJellyEntity child = ModEntities.PEZ_JELLY.get().create(this.level());
            if (child != null) {
                child.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                child.setJellySize(getJellySize() - 1);
                child.setAwake(true);
                this.level().addFreshEntity(child);
            }
        }
        super.die(source);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (getJellySize() >= 5) {
            bossEvent.setVisible(true);
            bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        } else {
            bossEvent.setVisible(false);
        }

        if (!isAwake()) {
            this.heal(2.0F);
            this.getNavigation().stop();
            return;
        }

        // 20% chance per jump to spawn TornadoJelly
        if (this.onGround() && jumpDelay == 1 && this.random.nextFloat() < 0.2F) {
            var tornado = ModEntities.TORNADO_JELLY.get().create(this.level());
            if (tornado != null) {
                double ox = (this.random.nextDouble() - 0.5) * 4;
                double oz = (this.random.nextDouble() - 0.5) * 4;
                tornado.moveTo(this.getX() + ox, this.getY(), this.getZ() + oz, 0, 0);
                this.level().addFreshEntity(tornado);
            }
        }
        if (jumpDelay > 0) jumpDelay--;
    }

    public void onJump() { jumpDelay = 2; }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossEvent.removePlayer(player);
    }

    @Override
    public boolean removeWhenFarAway(double distance) { return false; }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Awake", isAwake());
        tag.putInt("JellySize", getJellySize());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAwake(tag.getBoolean("Awake"));
        if (tag.contains("JellySize")) setJellySize(tag.getInt("JellySize"));
        if (this.hasCustomName()) bossEvent.setName(this.getDisplayName());
    }

    // ── Inner AI ──────────────────────────────────────────────────────
    static class PEZMoveControl extends MoveControl {
        private final PEZJellyEntity jelly;
        PEZMoveControl(PEZJellyEntity jelly) { super(jelly); this.jelly = jelly; }

        @Override
        public void tick() {
            if (!jelly.isAwake()) return;
            super.tick();
        }
    }

    static class PEZJumpGoal extends Goal {
        private final PEZJellyEntity jelly;
        private int nextJump;
        PEZJumpGoal(PEZJellyEntity jelly) { this.jelly = jelly; this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE)); }
        @Override public boolean canUse() { return jelly.isAwake() && jelly.getTarget() != null; }
        @Override public void tick() {
            if (--nextJump <= 0 && jelly.onGround()) {
                nextJump = 20 + jelly.getRandom().nextInt(20);
                var target = jelly.getTarget();
                if (target != null) {
                    var dir = target.position().subtract(jelly.position()).normalize();
                    jelly.setDeltaMovement(dir.x * 0.6, 0.5, dir.z * 0.6);
                    jelly.hasImpulse = true;
                    jelly.onJump();
                }
            }
        }
    }

    static class PEZAttackGoal extends Goal {
        private final PEZJellyEntity jelly;
        PEZAttackGoal(PEZJellyEntity jelly) { this.jelly = jelly; this.setFlags(EnumSet.of(Flag.LOOK)); }
        @Override public boolean canUse() { return jelly.isAwake() && jelly.getTarget() != null; }
        @Override public void tick() {
            if (jelly.getTarget() != null) jelly.lookAt(jelly.getTarget(), 30.0F, 30.0F);
        }
    }
}
