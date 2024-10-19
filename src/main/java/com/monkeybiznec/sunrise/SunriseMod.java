package com.monkeybiznec.sunrise;

import com.monkeybiznec.sunrise.client.SunriseClientProxy;
import com.monkeybiznec.sunrise.client.sound_event.SunriseSoundEvents;
import com.monkeybiznec.sunrise.common.SunriseCommonProxy;
import com.monkeybiznec.sunrise.common.effect.SunriseMobEffects;
import com.monkeybiznec.sunrise.common.entity.SunriseEntityTypes;
import com.monkeybiznec.sunrise.common.item.SunriseItems;
import com.monkeybiznec.sunrise.common.network.NetworkHandler;
import com.monkeybiznec.sunrise.common.potion.SunriseBrewingRecipe;
import com.monkeybiznec.sunrise.common.potion.SunrisePotions;
import com.monkeybiznec.sunrise.util.MiscUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;

@Mod(SunriseMod.MODID)
public class SunriseMod {
    public static final String MODID = "sunrise";
    public static SunriseCommonProxy PROXY = DistExecutor.safeRunForDist(() -> SunriseClientProxy::new, () -> SunriseCommonProxy::new);

    public SunriseMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        SunriseEntityTypes.ENTITY_TYPES.register(bus);
        SunriseItems.ITEMS.register(bus);
        SunriseMobEffects.MOB_EFFECTS.register(bus);
        SunrisePotions.POTIONS.register(bus);
        SunriseSoundEvents.SOUND_EVENTS.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        bus.addListener(this::onCommonSetup);
        bus.addListener(this::onClientSetup);
        bus.addListener(this::onAddCreativeTab);
        bus.addListener(NetworkHandler::registerPackets);
        PROXY.commonInitialize();
    }

    private void onAddCreativeTab(BuildCreativeModeTabContentsEvent pEvent) {
        if (pEvent.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            pEvent.accept(SunriseItems.CHEETAH_CLAW.get());
        }
        if (pEvent.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
             SunriseItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
                 if (item instanceof ForgeSpawnEggItem) {
                     pEvent.accept(item);
                 }
             });
        }
    }

    private void onCommonSetup(final FMLCommonSetupEvent pEvent) {
        pEvent.enqueueWork(() -> {
            BrewingRecipeRegistry.addRecipe(new SunriseBrewingRecipe(Ingredient.of(MiscUtils.createPotion(Potions.AWKWARD)), Ingredient.of(SunriseItems.CHEETAH_CLAW.get()), MiscUtils.createPotion(SunrisePotions.CHEETAH_SPEED_POTION)));
        });
    }

    private void onClientSetup(FMLClientSetupEvent pEvent) {
        pEvent.enqueueWork(() -> {
            PROXY.clientInitialize();
        });
    }
}
