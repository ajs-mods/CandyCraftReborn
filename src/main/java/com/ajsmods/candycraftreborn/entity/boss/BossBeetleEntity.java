package com.ajsmods.candycraftreborn.entity.boss;

import com.ajsmods.candycraftreborn.entity.GummyBallEntity;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BossBeetleEntity extends Monster {
    private static final EntityDataAccessor<Boolean> AWAKE =
            SynchedEntityData.defineId(BossBeetleEntity.class, EntityDataSerializers.BOOLEAN);

    private final ServerBossEvent bossEvent = new ServerBossEvent(
            this.getDisplayName(), BossEvent.BossBarColor.WHITE, BossEvent.BossBarOverlay.PROGRESS);

    private int attackTimer = 0;
    private int tornadoTimer = 0;
    private boolean inTornadoMode = false;
    private int tornadoCooldown = 0;

    public BossBeetleEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 80;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AWAKE, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0)
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    public boolean isAwake() { return this.entityData.get(AWAKE); }
    public void setAwake(boolean awake) { this.entityData.set(AWAKE, awake); }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        // ONLY damaged by GummyBallEntity
        if (source.getDirectEntity() instanceof GummyBallEntity) {
            return super.hurt(source, 8.0F);
        }
        return false;
    }

    private void fireGummyBall(LivingEntity target) {
        GummyBallEntity ball = new GummyBallEntity(ModEntities.GUMMY_BALL.get(), this, this.level());
        Vec3 dir = target.getEyePosition().subtract(this.getEyePosition());
        ball.shoot(dir.x, dir.y, dir.z, 1.6F, 2.0F);
        this.level().addFreshEntity(ball);
    }

    private void fireHomingGummyBall(LivingEntity target) {
        GummyBallEntity ball = new GummyBallEntity(ModEntities.GUMMY_BALL.get(), this, this.level());
        ball.setPos(this.getX() + (this.random.nextDouble() - 0.5) * 10,
                     this.getY() + 20,
                     this.getZ() + (this.random.nextDouble() - 0.5) * 10);
        Vec3 dir = target.position().subtract(ball.position()).normalize();
        ball.shoot(dir.x, dir.y, dir.z, 0.8F, 1.0F);
        this.level().addFreshEntity(ball);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        // Wake when player within 10 blocks
        if (!isAwake()) {
            AABB area = this.getBoundingBox().inflate(10.0);
            List<Player> nearby = this.level().getEntitiesOfClass(Player.class, area);
            if (!nearby.isEmpty()) setAwake(true);
            else return;
        }

        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) return;

        float hp = this.getHealth();

        // Below 150 HP: Rain of 50 homing gummy balls
        if (hp < 150 && attackTimer % 200 == 0) {
            for (int i = 0; i < 50; i++) {
                fireHomingGummyBall(target);
            }
        }

        // Below 250 HP: Tornado mode
        if (hp < 250 && !inTornadoMode && tornadoCooldown <= 0) {
            inTornadoMode = true;
            tornadoTimer = 200;
        }

        if (inTornadoMode) {
            tornadoTimer--;
            if (tornadoTimer % 10 == 0) {
                fireGummyBall(target);
            }
            if (tornadoTimer <= 0) {
                inTornadoMode = false;
                tornadoCooldown = 200;
            }
        } else {
            if (tornadoCooldown > 0) tornadoCooldown--;
            // Primary attack every 30 ticks
            if (attackTimer % 30 == 0) {
                fireGummyBall(target);
            }
        }

        attackTimer++;
    }

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
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setAwake(tag.getBoolean("Awake"));
        if (this.hasCustomName()) bossEvent.setName(this.getDisplayName());
    }
}
