package com.monkeybiznec.sunrise.common.entity.goal;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public class MeleeAttackGoal <T extends PathfinderMob> extends Goal {
    private final T mob;
    public int delayBetweenAttack;
    public int maxDelayBetweenAttack;
    public final float reachDistance;
    private int attackAtTick;

    public MeleeAttackGoal(T pMob, int pMaxDelayBetweenAttack, float pReachDistance) {
        this.mob = pMob;
        this.maxDelayBetweenAttack = pMaxDelayBetweenAttack;
        this.reachDistance = pReachDistance;
    }

    @Override
    public void tick() {
        LivingEntity target = this.mob.getTarget();
        if (this.delayBetweenAttack > 0) {
            --this.delayBetweenAttack;
        }
        if (target != null) {
            if (this.enemyInAttackReach(target)) {
                if (this.delayBetweenAttack == 0) {
                    this.delayBetweenAttack = maxDelayBetweenAttack;
                    this.onAttack();
                }
            } else {
                this.ifNotInAttackRange();
            }
        }
        super.tick();
    }

    @Contract("null->false")
    public boolean enemyInAttackReach(@Nullable LivingEntity pTarget) {
        if (pTarget != null) {
            return this.mob.distanceTo(pTarget) < pTarget.getBbWidth() + this.mob.getBbWidth() + this.reachDistance;
        }
        return false;
    }

    public void onAttack() {
    }

    public void ifNotInAttackRange() {

    }

    public int attackAtTick() {
        return this.attackAtTick;
    }

    @Override
    public void start() {
        this.delayBetweenAttack = maxDelayBetweenAttack;
        super.start();
    }

    @Override
    public boolean canUse() {
        return this.mob.getTarget() != null && this.mob.getTarget().isAlive();
    }
}
