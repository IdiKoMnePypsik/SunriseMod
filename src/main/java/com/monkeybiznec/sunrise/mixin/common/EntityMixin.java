package com.monkeybiznec.sunrise.mixin.common;

import com.monkeybiznec.sunrise.common.effect.SunriseMobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Unique
    private Entity getSelf() {
        return ((Entity)(Object)this);
    }

    @Inject(method = "isSprinting", at = @At("HEAD"), cancellable = true)
    public void onIsSprinting(CallbackInfoReturnable<Boolean> cir) {
        Entity entity = this.getSelf();
        if (entity instanceof LivingEntity living && living.hasEffect(SunriseMobEffects.DYSPNEA.get())) {
            cir.setReturnValue(false);
        }
    }
}
