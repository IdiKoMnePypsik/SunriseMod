package com.monkeybiznec.sunrise.common.entity;

import com.monkeybiznec.sunrise.client.sound_event.SunriseSoundEvents;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.task.RandomMoveTask;
import com.monkeybiznec.sunrise.common.entity.ai.behavior.IDynamicCamera;
import com.monkeybiznec.sunrise.common.entity.ai.behavior.IMouseInput;
import com.monkeybiznec.sunrise.common.entity.base.BaseRideableAnimal;
import com.monkeybiznec.sunrise.common.entity.navigation.SmartGroundNavigation;
import com.monkeybiznec.sunrise.util.MiscUtils;
import com.monkeybiznec.sunrise.util.RandomSelector;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class Elephant extends BaseRideableAnimal implements IDynamicCamera {
    private final TaskManager<Elephant> taskManager;
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState frAnimationState = new AnimationState();
    public int idleAnimationTimeout = 0;
    public int frAnimationTimeout = 0;
    private final RandomSelector<SoundEvent> STEP_SOUNDS = new RandomSelector<SoundEvent>()
            .addWeight(SunriseSoundEvents.ELEPHANT_STEP_0.get(), 1.0F)
            .addWeight(SunriseSoundEvents.ELEPHANT_STEP_1.get(), 1.0F)
            .addWeight(SunriseSoundEvents.ELEPHANT_STEP_2.get(), 1.0F);
    public float tailRot;
    public float prevTailRot;

    public Elephant(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.xpReward = 10;
        this.taskManager = new TaskManager<>(this);
        this.taskManager.actionScheduler.addAction(entity -> {
            if (entity.getTarget() == null) {
                this.level().broadcastEntityEvent(this, (byte) 101);
            }
        }, 35, this.getRandom().nextIntBetweenInclusive(140, 300) + 450);
        this.setMaxUpStep(2.0F);
        this.registerAI();
    }

    public void registerAI() {
        this.taskManager.addTask(new RandomMoveTask<>(this.taskManager, this, 1,  Task.TaskImportance.MEDIUM));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 40.0F)
                .add(Attributes.FOLLOW_RANGE, 48.0F)
                .add(Attributes.ATTACK_DAMAGE, 4.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.1F)
                .add(Attributes.ARMOR, 0.2F);
    }

    @Override
    protected void updateWalkAnimation(float pPartialTicks) {
        float f = this.getPose() == Pose.STANDING ? Math.min(pPartialTicks * 6.0F, 1.0F) : 0.0F;
        this.walkAnimation.update(f, 0.5F);
    }

    private void setupAnimations() {
        if (this.level().isClientSide()) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = 52;
                this.idleAnimationState.start(this.tickCount);
            } else {
                --this.idleAnimationTimeout;
            }
            if (!MiscUtils.isEntityMoving(this, 0.05F) && this.frAnimationTimeout == 50) {
                this.frAnimationState.start(this.tickCount);
            }
            if (this.frAnimationTimeout > 0) {
                --this.frAnimationTimeout;
            }
        }
    }

    @Override
    public void handleEntityEvent(byte pId) {
        switch (pId) {
            case 101:
                if (this.frAnimationTimeout <= 0) {
                    this.frAnimationTimeout = 50;
                }
                break;
            default:
                super.handleEntityEvent(pId);
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.taskManager.update();
        this.setupAnimations();
        this.prevTailRot = this.tailRot;
        this.tailRot += (-(this.yBodyRot - this.yBodyRotO) - this.tailRot) * 0.15F;
    }

    @Override
    protected float rotateSpeed() {
        return 0.1F;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.3F;
    }

    @Override
    protected void positionRider(@NotNull Entity pPassenger, @NotNull MoveFunction pCallback) {
        if (this.isPassengerOfSameVehicle(pPassenger)) {
            pCallback.accept(pPassenger, this.getX(), this.getY() + this.getPassengersRidingOffset(), this.getZ());
        } else {
            super.positionRider(pPassenger, pCallback);
        }
    }

    @Override
    protected boolean canRide() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected float nextStep() {
        return super.nextStep() + 0.1F;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pBlockPos, @NotNull BlockState pBlockState) {
        this.playSound(STEP_SOUNDS.getRandomByWeight(), 1.0F, 1.0F);
    }

    @Override
    public float getMaxCameraTilt() {
        return 35.0F;
    }

    @Override
    public float getCameraTiltSpeed() {
        return 5.0F;
    }
}
