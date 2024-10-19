package com.monkeybiznec.sunrise.common.entity.goal.cheetah;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.tag.SunriseTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

public class CheetahNearestAttackableTargetGoal <T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final Cheetah cheetah;
    private final Class<T> targetClass;

    public CheetahNearestAttackableTargetGoal(Mob pMob, Class<T> pTargetClass, boolean pMustSee) {
        super(pMob, pTargetClass, pMustSee);
        this.cheetah = (Cheetah) pMob;
        this.targetClass = pTargetClass;
    }

    @Override
    public boolean canUse() {
        if (this.targetClass == Animal.class) {
            return super.canUse() && this.target != null && this.target.getType().is(SunriseTags.EntityTypes.TAG_CHEETAH_FOOD) && !this.cheetah.isBaby() && this.cheetah.canAttackTarget(this.target);
        } else if (this.targetClass == Player.class) {
            return super.canUse() && !this.cheetah.isBaby() && this.cheetah.canAttackTarget(this.target);
        } else {
            return super.canUse();
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.targetClass == Player.class) {
            return super.canContinueToUse() && !this.cheetah.isBaby() && this.cheetah.canAttackTarget(this.target);
        } else {
            return super.canContinueToUse();
        }
    }
}
