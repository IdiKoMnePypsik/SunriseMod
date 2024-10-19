package com.monkeybiznec.sunrise.common.entity.goal.cheetah;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.goal.MeleeAttackGoal;
import com.monkeybiznec.sunrise.common.entity.navigation.SmartGroundNavigation;
import com.monkeybiznec.sunrise.common.item.SunriseItems;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CheetahMeleeAttackGoal<T extends PathfinderMob> extends MeleeAttackGoal<T> {
    private final Cheetah cheetah;

    public CheetahMeleeAttackGoal(T pMob, int pMaxDelayBetweenAttack, float pReachDistance) {
        super(pMob, pMaxDelayBetweenAttack, pReachDistance);
        this.cheetah = (Cheetah) pMob;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public void tick() {
        SmartGroundNavigation navigator = (SmartGroundNavigation) this.cheetah.getNavigation();
        LivingEntity target = this.cheetah.getTarget();
        if (target != null) {
            Vec3 vec3 = target.position();
            float distanceToTarget = this.cheetah.distanceTo(target);
            this.cheetah.lookAt(EntityAnchorArgument.Anchor.EYES, vec3);
            navigator.moveToPos(vec3, 0.7F);
            if (distanceToTarget > 2.0F) {
                Cheetah.DATA_RUNNING_TICK.set(this.cheetah, 10);
            }
            if (distanceToTarget < 9.5F && distanceToTarget > 6.5F) {
                if (Cheetah.DATA_JUMP_COOLDOWN.get(this.cheetah) <= 0 && !this.cheetah.isInFluidType() && this.cheetah.onGround()) {
                    this.cheetah.jumpToPos(this.cheetah, target.position(), onJump -> {
                        this.cheetah.playSound(SoundEvents.GOAT_LONG_JUMP);
                        if (this.cheetah.getRandom().nextInt(3) == 0) {
                            ItemEntity itemEntity = new ItemEntity(this.cheetah.level(), this.cheetah.getX(), this.cheetah.getY(), this.cheetah.getZ(), new ItemStack(SunriseItems.CHEETAH_CLAW.get()));
                            itemEntity.setThrower(this.cheetah.getUUID());
                            itemEntity.setPickUpDelay(40);
                            this.cheetah.level().addFreshEntity(itemEntity);
                        }
                    });
                    Cheetah.DATA_JUMP_COOLDOWN.set(this.cheetah, 200);
                    Cheetah.DATA_JUMPING.set(this.cheetah, true);
                }
            }
        }
        super.tick();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.delayBetweenAttack = 0;
        super.start();
    }

    @Override
    public void onAttack() {
        if (this.cheetah.getTarget() != null) {
            this.cheetah.doHurtTarget(this.cheetah.getTarget());
        }
        super.onAttack();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.cheetah.getHealth() > 5.0F && !this.cheetah.isBaby() && this.cheetah.canAttackTarget(this.cheetah.getTarget()) && Cheetah.CALM.get(this.cheetah) <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.cheetah.getHealth() > 5.0F && !this.cheetah.isBaby() && this.cheetah.canAttackTarget(this.cheetah.getTarget()) && Cheetah.CALM.get(this.cheetah) <= 0;
    }
}
