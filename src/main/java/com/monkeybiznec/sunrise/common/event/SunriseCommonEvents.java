package com.monkeybiznec.sunrise.common.event;

import com.monkeybiznec.sunrise.SunriseMod;
import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.SunriseEntityTypes;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;

@Mod.EventBusSubscriber(modid = SunriseMod.MODID)
public class SunriseCommonEvents {
    @SubscribeEvent
    public static void onSpawnPlacementRegister(SpawnPlacementRegisterEvent pEvent) {
        pEvent.register(SunriseEntityTypes.CHEETAH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Cheetah::checkAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.AND);
    }



    @SubscribeEvent
    public static void test(TaskEvent.Complete event) {
        Log.info("asdasdasd");
    }
}
