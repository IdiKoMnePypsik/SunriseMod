package com.monkeybiznec.sunrise.client.model;

import com.monkeybiznec.sunrise.SunriseMod;
import com.monkeybiznec.sunrise.client.model.entity.CheetahModel;
import com.monkeybiznec.sunrise.client.model.entity.ElephantModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = SunriseMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class SunriseModelLayers {
    private static final Map<String, Supplier<LayerDefinition>> layerDefinitions = new HashMap<>();
    private static final Map<String, ModelLayerLocation> modelLayerLocations = new HashMap<>();

    static {
        registerLayer("cheetah", CheetahModel::createBodyLayer);
        registerLayer("elephant", ElephantModel::createBodyLayer);
    }

    private static void registerLayer(String pName, Supplier<LayerDefinition> pLayerDefinitionSupplier) {
        modelLayerLocations.put(pName, new ModelLayerLocation(new ResourceLocation(SunriseMod.MODID, pName + "_layer"), "main"));
        layerDefinitions.put(pName, pLayerDefinitionSupplier);
    }

    public static ModelLayerLocation get(String pName) {
        return modelLayerLocations.get(pName);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions pEvent) {
        layerDefinitions.forEach((pName, pDefinitionSupplier) -> {
            ModelLayerLocation layerLocation = modelLayerLocations.get(pName);
            if (layerLocation != null) {
                pEvent.registerLayerDefinition(layerLocation, pDefinitionSupplier);
            }
        });
    }
}
