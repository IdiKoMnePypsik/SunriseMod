package com.monkeybiznec.sunrise.common.effect;

import com.monkeybiznec.sunrise.SunriseMod;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class SunriseMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, SunriseMod.MODID);

    public static final RegistryObject<MobEffect> CHEETAH_SPEED;
    public static final RegistryObject<MobEffect> DYSPNEA;

    static {
        CHEETAH_SPEED = MOB_EFFECTS.register("cheetah_speed", () -> new CheetahSpeedMobEffect(MobEffectCategory.BENEFICIAL, new Color(253,184,80).hashCode()));
        DYSPNEA = MOB_EFFECTS.register("dyspnea", () -> new DyspneaMobEffect(MobEffectCategory.HARMFUL, new Color(229,109,209).hashCode()));
    }
}
