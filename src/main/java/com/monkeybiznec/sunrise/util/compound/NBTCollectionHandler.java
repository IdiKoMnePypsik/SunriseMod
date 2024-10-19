package com.monkeybiznec.sunrise.util.compound;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class NBTCollectionHandler {
    public static <T> void writeCollection(CompoundTag pCompound, String pName, Collection<T> collection, BiConsumer<CompoundTag, T> pEntryWriter) {
        ListTag listTag = new ListTag();
        for (T entry : collection) {
            CompoundTag entryTag = new CompoundTag();
            pEntryWriter.accept(entryTag, entry);
            listTag.add(entryTag);
        }
        pCompound.put(pName, listTag);
    }

    public static <T> List<T> readCollection(@NotNull CompoundTag pCompound, String pName, BiFunction<CompoundTag, String, T> pEntryReader) {
        List<T> collection = new ArrayList<>();
        if (pCompound.contains(pName, Tag.TAG_LIST)) {
            ListTag listTag = pCompound.getList(pName, Tag.TAG_COMPOUND);
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag entryTag = listTag.getCompound(i);
                T entry = pEntryReader.apply(entryTag, pName);
                collection.add(entry);
            }
        }
        return collection;
    }

    public static <K, V> void writeMap(@NotNull CompoundTag pCompound, String pName, Map<K, V> map, BiConsumer<CompoundTag, Map.Entry<K, V>> pEntryWriter) {
        ListTag listTag = new ListTag();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            CompoundTag entryTag = new CompoundTag();
            pEntryWriter.accept(entryTag, entry);
            listTag.add(entryTag);
        }
        pCompound.put(pName, listTag);
    }

    public static <K, V> Map<K, V> readMap(CompoundTag pCompound, String pName, BiFunction<CompoundTag, String, Map.Entry<K, V>> pEntryReader) {
        Map<K, V> map = new HashMap<>();
        if (pCompound.contains(pName, Tag.TAG_LIST)) {
            ListTag listTag = pCompound.getList(pName, Tag.TAG_COMPOUND);
            for (int i = 0; i < listTag.size(); i++) {
                CompoundTag entryTag = listTag.getCompound(i);
                Map.Entry<K, V> entry = pEntryReader.apply(entryTag, pName);
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }
}
