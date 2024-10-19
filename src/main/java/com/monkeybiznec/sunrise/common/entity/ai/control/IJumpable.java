package com.monkeybiznec.sunrise.common.entity.ai.control;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface IJumpable {
    default void jumpToPos(@NotNull Entity pEntity, Vec3 pPos, Consumer<IJumpable> pOnJump) {
        Vec3 jumpDirection = new Vec3(pPos.x - pEntity.getX(), 0.0D, pPos.z - pEntity.getZ());
        if (jumpDirection.lengthSqr() > 1.0E-7D) {
            jumpDirection = jumpDirection.normalize().scale(0.9D).add(pEntity.getDeltaMovement().scale(0.5D));
        } else {
            jumpDirection = Vec3.ZERO;
        }
        pEntity.setDeltaMovement(jumpDirection.x * this.getJumpPower(), 0.32F * this.getJumpPower() + 0.2F, jumpDirection.z * this.getJumpPower());
        if (pOnJump != null) {
            pOnJump.accept(this);
        }
    }

    float getJumpPower();
}
