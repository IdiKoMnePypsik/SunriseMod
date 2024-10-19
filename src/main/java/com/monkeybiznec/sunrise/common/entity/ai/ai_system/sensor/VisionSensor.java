package com.monkeybiznec.sunrise.common.entity.ai.ai_system.sensor;

import com.monkeybiznec.sunrise.common.entity.ai.ai_system.ISensor;
import com.monkeybiznec.sunrise.util.MiscUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class VisionSensor<T extends LivingEntity> implements ISensor {
    private final T entity;
    private double visionRange;
    private List<LivingEntity> visibleEntities = new ArrayList<>();

    public VisionSensor(T pEntity) {
        this.entity = pEntity;
    }

    public VisionSensor<T> setVisionRange(double pVisionRange) {
        this.visionRange = pVisionRange;
        return this;
    }

    @Override
    public void update() {
        AABB visionArea = new AABB(this.entity.position(), this.entity.position()).inflate(this.visionRange);
        this.visibleEntities.clear();
        List<LivingEntity> allEntities = this.entity.level().getEntitiesOfClass(LivingEntity.class, visionArea);
        for (LivingEntity target : allEntities) {
            if (isEntityVisible(target)) {
                this.visibleEntities.add(target);
            }
        }
    }

    @Contract("null->false")
    private boolean isEntityVisible(@Nullable LivingEntity pTarget) {
        return pTarget != null && pTarget != this.entity && pTarget.isAlive() && this.entity.hasLineOfSight(pTarget);
    }

    @Override
    public List<LivingEntity> getSensorData() {
        return new ArrayList<>(this.visibleEntities);
    }
}