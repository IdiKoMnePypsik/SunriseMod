package com.monkeybiznec.sunrise.common.entity;

import com.monkeybiznec.sunrise.SunriseMod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class SunriseEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SunriseMod.MODID);

    public static final RegistryObject<EntityType<Cheetah>> CHEETAH;
    public static final RegistryObject<EntityType<Elephant>> ELEPHANT;

    static {
        CHEETAH = ENTITY_TYPES.register("cheetah", () -> EntityType.Builder.of(Cheetah::new, MobCategory.CREATURE).sized(0.8F, 1.2F).build("cheetah"));
        ELEPHANT = ENTITY_TYPES.register("elephant", () -> EntityType.Builder.of(Elephant::new, MobCategory.CREATURE).sized(2.6F, 3.2F).build("elephant"));
    }
}
