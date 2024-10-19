package com.monkeybiznec.sunrise.common.entity.ai.goal;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.ai.control.SearchType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class MultiAttackableTargetGoal <T extends LivingEntity> extends TargetGoal {
    private float searchRange;
    private final SearchType searchType;
    private Predicate<Mob> startCondition = condition -> true;
    private final List<Class<? extends LivingEntity>> targetClasses;
    private TargetingConditions targetingConditions = TargetingConditions.forCombat();
    @Nullable
    private LivingEntity target;
    private Predicate<LivingEntity> validTarget = entity -> true;

    public MultiAttackableTargetGoal(Mob pMob, boolean pMustSee, SearchType pSearchType, List<Class<? extends LivingEntity>> pTargetClasses) {
        super(pMob, pMustSee);
        this.searchType = pSearchType;
        this.targetClasses = pTargetClasses;
    }

    public MultiAttackableTargetGoal<T> setSearchRange(int pSearchRange) {
        if (this.searchType == SearchType.CUSTOM) {
            this.searchRange = pSearchRange;
        }
        return this;
    }

    public MultiAttackableTargetGoal<T> setStartCondition(Predicate<Mob> pCondition) {
        this.startCondition = pCondition;
        return this;
    }

    public MultiAttackableTargetGoal<T> setTargetingConditions(TargetingConditions pTargetingConditions) {
        this.targetingConditions = pTargetingConditions;
        return this;
    }

    public MultiAttackableTargetGoal<T> setValidTargetCondition(Predicate<LivingEntity> pValidTarget) {
        this.validTarget = pValidTarget;
        return this;
    }

    private void findNearestTarget() {
        AABB searchArea = this.mob.getBoundingBox().inflate(this.searchRange);
        for (Class<? extends LivingEntity> targetClass : this.targetClasses) {
            LivingEntity foundTarget = null;
            if (targetClass != Player.class && targetClass != ServerPlayer.class) {
                foundTarget = this.mob.level().getNearestEntity(this.mob.level().getEntitiesOfClass(targetClass, searchArea, entity -> {
                    return this.validTarget.test(entity);
                }), this.targetingConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
            } else {
                foundTarget = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
                if (foundTarget != null && !this.validTarget.test(foundTarget)) {
                    foundTarget = null;
                }
            }
            if (foundTarget != null) {
                this.target = foundTarget;
                break;
            }
        }
    }

    @Override
    public boolean canUse() {
        if (this.startCondition != null && !this.startCondition.test(this.mob)) {
            return false;
        }
        this.findNearestTarget();
        return this.target != null;
    }

    @Override
    public void start() {
        super.start();
        if (this.searchType == SearchType.DEFAULT) {
            this.searchRange = (float) this.getFollowDistance();
        }
        if (this.target != null) {
            this.mob.setTarget(this.target);
        }
    }

    @Override
    public void stop() {
        super.stop();
        this.target = null;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.target == null) {
            return false;
        }
        if (!this.validTarget.test(this.target)) {
            this.target = null;
            return false;
        }
        return super.canContinueToUse() && Cheetah.CALM.get(this.mob) <= 0;
    }
}
