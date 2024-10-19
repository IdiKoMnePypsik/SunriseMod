package com.monkeybiznec.sunrise.client;

import com.monkeybiznec.sunrise.client.render.entity.CheetahRenderer;
import com.monkeybiznec.sunrise.client.render.entity.ElephantRenderer;
import com.monkeybiznec.sunrise.common.SunriseCommonProxy;
import com.monkeybiznec.sunrise.common.entity.SunriseEntityTypes;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

public class SunriseClientProxy extends SunriseCommonProxy {
    public static float currentRoll = 0.0F;

    @Override
    public void clientInitialize() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        this.registerEntityRender(SunriseEntityTypes.CHEETAH, CheetahRenderer::new);
        this.registerEntityRender(SunriseEntityTypes.ELEPHANT, ElephantRenderer::new);
    }

    private <T extends Entity> void registerEntityRender(RegistryObject<EntityType<T>> pEntityType, EntityRendererProvider<T> pRenderer) {
        EntityRenderers.register(pEntityType.get(), pRenderer);
    }
}
