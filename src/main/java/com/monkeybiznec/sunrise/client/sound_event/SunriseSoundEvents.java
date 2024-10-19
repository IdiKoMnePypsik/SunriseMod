package com.monkeybiznec.sunrise.client.sound_event;

import com.monkeybiznec.sunrise.SunriseMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SunriseSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SunriseMod.MODID);

    public static final RegistryObject<SoundEvent> OBORMOT;
    public static final RegistryObject<SoundEvent> ELEPHANT_STEP_0;
    public static final RegistryObject<SoundEvent> ELEPHANT_STEP_1;
    public static final RegistryObject<SoundEvent> ELEPHANT_STEP_2;

    static {
        OBORMOT = registerSoundEvents("obormot");
        ELEPHANT_STEP_0 = registerSoundEvents("elephant_step_0");
        ELEPHANT_STEP_1 = registerSoundEvents("elephant_step_1");
        ELEPHANT_STEP_2 = registerSoundEvents("elephant_step_2");
    }

    public static RegistryObject<SoundEvent> registerSoundEvents(String pSoundName) {
        return SOUND_EVENTS.register(pSoundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(SunriseMod.MODID, pSoundName)));
    }
}
