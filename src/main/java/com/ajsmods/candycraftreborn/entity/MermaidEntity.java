package com.ajsmods.candycraftreborn.entity;

import com.ajsmods.candycraftreborn.registry.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.phys.Vec3;

public class MermaidEntity extends Monster implements RangedAttackMob {

    public MermaidEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.0, 20, 30, 15.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.4));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_DAMAGE, 3.0);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float power) {
        CandyArrowEntity arrow = new CandyArrowEntity(ModEntities.CANDY_ARROW.get(), this, this.level());
        double dx = target.getX() - this.getX();
        double dy = target.getY(0.3333333333333333) - arrow.getY();
        double dz = target.getZ() - this.getZ();
        double dist = Math.sqrt(dx * dx + dz * dz);
        arrow.shoot(dx, dy + dist * 0.2, dz, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.level().addFreshEntity(arrow);
    }

    @Override
    public void tick() {
        super.tick();
        // Flop on land: move slower and apply random movement
        if (!this.level().isClientSide && !this.isInWater() && this.onGround()) {
            Vec3 m = this.getDeltaMovement();
            this.setDeltaMovement(m.x * 0.5, m.y, m.z * 0.5);
        }
        // Better water movement
        if (this.isInWater()) {
            Vec3 m = this.getDeltaMovement();
            this.setDeltaMovement(m.x * 1.1, m.y * 1.05, m.z * 1.1);
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean checkSpawnObstruction(LevelReader level) {
        return true;
    }
}
