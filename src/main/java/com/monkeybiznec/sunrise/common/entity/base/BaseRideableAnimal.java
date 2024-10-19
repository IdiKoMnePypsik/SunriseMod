package com.monkeybiznec.sunrise.common.entity.base;

import com.monkeybiznec.sunrise.common.entity.ai.behavior.IMouseInput;
import com.monkeybiznec.sunrise.common.entity.navigation.SmartGroundNavigation;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseRideableAnimal extends Animal implements IMouseInput {
    public int tickControlled;

    public BaseRideableAnimal(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.navigation = new SmartGroundNavigation(this, 1.3F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getFirstPassenger() != null) {
            ++this.tickControlled;
        } else {
            this.tickControlled = 0;
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NotNull ServerLevel pServerLevel, @NotNull AgeableMob pAgeableMob) {
        return null;
    }

    @Contract("null->false")
    public boolean startRiding(@Nullable Player pPlayer) {
        if (pPlayer != null) {
            pPlayer.setYRot(this.getYRot());
            pPlayer.setXRot(this.getXRot());
            pPlayer.setYHeadRot(this.getYHeadRot());
            pPlayer.startRiding(this);
            return true;
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult interactAt(@NotNull Player pPlayer, @NotNull Vec3 pPosition, @NotNull InteractionHand pUsedHand) {
        if (this.canAddPassenger(pPlayer) && !this.isBaby() && this.canRide() && this.startRiding(pPlayer)) {
            return InteractionResult.SUCCESS;
        } else {
            return InteractionResult.FAIL;
        }
    }

    @Override
    protected float getRiddenSpeed(@NotNull Player pRider) {
        return 0.1F;
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        float forwardMovement = pRider.zza < 0.0F ? 0.0F : 1.0F;
        return new Vec3(pRider.xxa * 0.35F, 0.0, pRider.zza * 0.8F * forwardMovement);
    }

    protected abstract float rotateSpeed();

    @Override
    protected void tickRidden(@NotNull Player pRider, @NotNull Vec3 pTravelVector) {
        super.tickRidden(pRider, pTravelVector);
        if (pRider.xxa != 0.0F || pRider.zza != 0.0F) {
            float targetYaw = pRider.yHeadRotO + (pRider.yHeadRot - pRider.yHeadRotO) * Minecraft.getInstance().getPartialTick();
            float interpolatedRotation = Mth.rotLerp(this.rotateSpeed(), this.yRotO, targetYaw);
            this.yRotO = interpolatedRotation;
            this.yBodyRot = interpolatedRotation;
            this.yHeadRot = interpolatedRotation;
            this.setYRot(Mth.rotLerp(this.rotateSpeed(), this.getYRot(), pRider.getYRot()));
        }
    }

    @Override
    public LivingEntity getControllingPassenger() {
        return this.getFirstPassenger() instanceof Player player ? player : null;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.3F;
    }

    @Override
    public void onMouseClick(int pButton) {

    }

    @Override
    public boolean isActionDenied(int pActionId) {
        return pActionId == 1;
    }

    protected abstract boolean canRide();
}
