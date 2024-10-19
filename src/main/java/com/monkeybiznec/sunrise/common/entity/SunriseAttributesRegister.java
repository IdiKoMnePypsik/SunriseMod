package com.monkeybiznec.sunrise.common.entity;

import com.monkeybiznec.sunrise.SunriseMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = SunriseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SunriseAttributesRegister {
    @SubscribeEvent
    public static void onRegisterAttributes(EntityAttributeCreationEvent pEvent) {
        pEvent.put(SunriseEntityTypes.CHEETAH.get(), Cheetah.createAttributes().build());
        pEvent.put(SunriseEntityTypes.ELEPHANT.get(), Elephant.createAttributes().build());
    }
}
