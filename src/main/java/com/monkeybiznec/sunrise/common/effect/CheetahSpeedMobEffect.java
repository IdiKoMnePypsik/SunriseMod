package com.monkeybiznec.sunrise.common.effect;

import com.monkeybiznec.sunrise.util.MiscUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class CheetahSpeedMobEffect extends MobEffect {
    private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");
    private static final double MAX_SPEED_BOOST = 0.1;
    private static final double SPEED_INCREMENT = 0.0007f;

    protected CheetahSpeedMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity pLivingEntity, int pAmplifier) {
        MobEffectInstance effect = pLivingEntity.getEffect(this);
        if (effect != null) {
            if (effect.getDuration() <= 1) {
                if (!pLivingEntity.level().isClientSide) {
                    pLivingEntity.addEffect(new MobEffectInstance(SunriseMobEffects.DYSPNEA.get(), 600, 1, true, false));
                }
            }
            Vec3 movement = pLivingEntity.getDeltaMovement();
            if (Math.sqrt(movement.x * movement.x + movement.z * movement.z) > 0.1 && pLivingEntity.getDeltaMovement().y <= 0.0F) {
                double speedBoost = calculateSpeedBoost(pLivingEntity, pAmplifier);
                applySpeedModifier(pLivingEntity, speedBoost);
            } else {
                removeSpeedModifier(pLivingEntity);
            }
        }
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    private double calculateSpeedBoost(LivingEntity entity, int amplifier) {
        AttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute == null) return 0;
        AttributeModifier existingModifier = speedAttribute.getModifier(SPEED_MODIFIER_UUID);
        double currentBoost = (existingModifier != null) ? existingModifier.getAmount() : 0;
        double newBoost = currentBoost + SPEED_INCREMENT * (amplifier + 1);
        return Math.min(newBoost, MAX_SPEED_BOOST);
    }

    private void applySpeedModifier(LivingEntity entity, double speedBoost) {
        AttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            AttributeModifier existingModifier = speedAttribute.getModifier(SPEED_MODIFIER_UUID);
            if (existingModifier != null) {
                speedAttribute.removeModifier(existingModifier);
            }
            AttributeModifier speedModifier = new AttributeModifier(SPEED_MODIFIER_UUID, "Cheetah speed boost", speedBoost, AttributeModifier.Operation.ADDITION);
            speedAttribute.addTransientModifier(speedModifier);
        }
    }

    private void removeSpeedModifier(LivingEntity entity) {
        AttributeInstance speedAttribute = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            AttributeModifier existingModifier = speedAttribute.getModifier(SPEED_MODIFIER_UUID);
            if (existingModifier != null) {
                speedAttribute.removeModifier(existingModifier);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
