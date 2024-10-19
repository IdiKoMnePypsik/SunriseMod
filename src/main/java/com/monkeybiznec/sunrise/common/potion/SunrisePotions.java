package com.monkeybiznec.sunrise.common.potion;

import com.monkeybiznec.sunrise.SunriseMod;
import com.monkeybiznec.sunrise.common.effect.SunriseMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SunrisePotions {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, SunriseMod.MODID);

    public static final RegistryObject<Potion> CHEETAH_SPEED_POTION;

    static {
        CHEETAH_SPEED_POTION = POTIONS.register("cheetah_speed_potion", () -> new Potion(new MobEffectInstance(SunriseMobEffects.CHEETAH_SPEED.get(), 3600, 0)));
    }
}
