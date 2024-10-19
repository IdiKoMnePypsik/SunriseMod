package com.monkeybiznec.sunrise.util;

import com.monkeybiznec.sunrise.SunriseMod;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationUtils {
    public static ResourceLocation entityPath(String pPath) {
        return new ResourceLocation(SunriseMod.MODID, "textures/entities/" + pPath + ".png");
    }
}
