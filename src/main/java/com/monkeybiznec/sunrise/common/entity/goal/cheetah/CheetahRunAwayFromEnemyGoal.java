package com.monkeybiznec.sunrise.common.entity.goal.cheetah;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.navigation.SmartGroundNavigation;
import com.monkeybiznec.sunrise.util.MiscUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

public class CheetahRunAwayFromEnemyGoal extends Goal {
    private final Cheetah cheetah;

    public CheetahRunAwayFromEnemyGoal(Cheetah pCheetah) {
        this.cheetah = pCheetah;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public void start() {
        this.cheetah.getNavigation().stop();
        super.start();
    }

    @Override
    public void tick() {
        LivingEntity target = this.cheetah.getTarget();
        SmartGroundNavigation navigator = (SmartGroundNavigation) this.cheetah.getNavigation();
        List<PathfinderMob> possibleEnemyList = MiscUtils.getEntitiesAroundSelf(PathfinderMob.class, this.cheetah, 35.0F, true);
        if (navigator.isDone()) {
            if (possibleEnemyList != null) {
                for (PathfinderMob possibleEnemy : possibleEnemyList) {
                    if (possibleEnemy.getTarget() != null && possibleEnemy.getTarget() == this.cheetah) {
                        if (this.cheetah.distanceTo(possibleEnemy) < 25.0F) {
                            navigator.moveToPos(posAwayFrom(possibleEnemy.position()), 0.6F);
                        }
                    }
                }
            }
            Player nearestPlayer = this.cheetah.level().getNearestPlayer(this.cheetah, 35.0F);
            if (nearestPlayer != null && nearestPlayer.isAlive()) {
                navigator.moveToPos(posAwayFrom(nearestPlayer.position()), 0.6F);
            }
            if (target != null && target.isAlive()) {
                if (this.cheetah.distanceTo(target) < 25.0F) {
                    navigator.moveToPos(posAwayFrom(target.position()), 0.6F);
                }
            }
        }
        Cheetah.DATA_RUNNING_TICK.set(this.cheetah, 5);
        super.tick();
    }

    public Vec3 posAwayFrom(Vec3 pPos) {
        Vec3 posAway = LandRandomPos.getPosAway(this.cheetah, 25, 13, pPos);
        if (posAway != null) {
            return posAway;
        }
        return Vec3.ZERO;
    }

    @Override
    public boolean canContinueToUse() {
        return this.cheetah.getHealth() <= 5.0F;
    }

    @Override
    public boolean canUse() {
        return this.cheetah.getHealth() <= 5.0F;
    }
}
