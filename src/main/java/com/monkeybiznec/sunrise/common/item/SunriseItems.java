package com.monkeybiznec.sunrise.common.item;

import com.monkeybiznec.sunrise.SunriseMod;
import com.monkeybiznec.sunrise.client.sound_event.SunriseSoundEvents;
import com.monkeybiznec.sunrise.common.entity.SunriseEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class SunriseItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SunriseMod.MODID);

    public static final RegistryObject<Item> CHEETAH_CLAW;
    public static final RegistryObject<Item> MUSIC_DISC_OBORMOT;
    public static final RegistryObject<Item> CHEETAH_SPAWN_EGG;
    public static final RegistryObject<Item> ELEPHANT_SPAWN_EGG;

    static {
        CHEETAH_CLAW = ITEMS.register("cheetah_claw", () -> new Item(new Item.Properties().stacksTo(64)));
        MUSIC_DISC_OBORMOT = ITEMS.register("music_disc_obormot", () -> new RecordItem(7, SunriseSoundEvents.OBORMOT, new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 780));
        CHEETAH_SPAWN_EGG = registerSpawnEgg("cheetah", SunriseEntityTypes.CHEETAH, new Color(242, 203, 93), new Color(68, 39, 19));
        ELEPHANT_SPAWN_EGG = registerSpawnEgg("elephant", SunriseEntityTypes.ELEPHANT, new Color(148, 109, 98), new Color(179, 142, 122));
    }

    public static <T extends Mob> RegistryObject<Item> registerSpawnEgg(String pMobName, RegistryObject<EntityType<T>> pEntity, Color pBackgroundColor, Color pHighlightColor) {
        return ITEMS.register(pMobName + "_spawn_egg", () -> new ForgeSpawnEggItem(pEntity, pBackgroundColor.hashCode(), pHighlightColor.hashCode(), new Item.Properties()));
    }
}
