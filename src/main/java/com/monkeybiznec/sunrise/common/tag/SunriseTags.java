package com.monkeybiznec.sunrise.common.tag;

import com.monkeybiznec.sunrise.SunriseMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class SunriseTags {
    public static class EntityTypes {
        public static final TagKey<EntityType<?>> TAG_CHEETAH_FOOD;

        static {
            TAG_CHEETAH_FOOD = registerEntityTypeTag("cheetah_food");
        }

        public static TagKey<EntityType<?>> registerEntityTypeTag(String pTagName) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(SunriseMod.MODID, pTagName));
        }
    }
}
