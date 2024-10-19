package com.monkeybiznec.sunrise.util.compound;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public enum CompoundType {
    INTEGER {
        @Override
        public Integer get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.getInt(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.putInt(pName, (Integer) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.INT);
        }
    },
    FLOAT {
        @Override
        public Float get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.getFloat(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.putFloat(pName, (Float) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.FLOAT);
        }
    },
    STRING {
        @Override
        public String get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.getString(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.putString(pName, (String) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.STRING);
        }
    },
    BOOLEAN {
        @Override
        public Boolean get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.getBoolean(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.putBoolean(pName, (Boolean) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.BOOLEAN);
        }
    },
    UUID {
        @Override
        public UUID get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.getUUID(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.putUUID(pName, (UUID) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.OPTIONAL_UUID);
        }
    },
    LONG {
        @Override
        public Long get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.getLong(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.putLong(pName, (Long) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.LONG);
        }
    },
    COMPOUND {
        @Override
        public Tag get(@NotNull CompoundTag pCompound, String pName) {
            return pCompound.get(pName);
        }

        @Override
        public void set(@NotNull CompoundTag pCompound, String pName, Object pValue) {
            pCompound.put(pName, (Tag) pValue);
        }

        @Override
        public EntityDataAccessor<?> createAccessor(Class<? extends Entity> entityClass) {
            return SynchedEntityData.defineId(entityClass, EntityDataSerializers.COMPOUND_TAG);
        }
    };

    public abstract Object get(@NotNull CompoundTag pCompound, String pName);

    public abstract void set(@NotNull CompoundTag pCompound, String pName, Object pValue);

    public abstract EntityDataAccessor<?> createAccessor(Class<? extends Entity> pEntityClass);
}
