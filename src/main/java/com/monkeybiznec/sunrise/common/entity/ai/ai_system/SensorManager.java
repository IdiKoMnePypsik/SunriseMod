package com.monkeybiznec.sunrise.common.entity.ai.ai_system;

import net.minecraft.world.entity.LivingEntity;

import java.util.EnumMap;
import java.util.Optional;

public class SensorManager<T extends LivingEntity> {
    private final EnumMap<SensorType, ISensor> sensors = new EnumMap<>(SensorType.class);

    public void addSensor(SensorType pSensorType, ISensor pSensor) {
        this.sensors.put(pSensorType, pSensor);
    }

    @SuppressWarnings("unchecked")
    public <S extends ISensor> S getSensor(SensorType pSensorType, S defaultSensor) {
        ISensor foundSensor = this.sensors.get(pSensorType);
        if (foundSensor != null) {
            return (S) foundSensor;
        }
        return defaultSensor;
    }

    public void updateSensors() {
        for (ISensor sensor : this.sensors.values()) {
            sensor.update();
        }
    }

    public Object getSensorData(SensorType pSensorType) {
        ISensor sensor = this.sensors.get(pSensorType);
        return Optional.ofNullable(sensor).map(ISensor::getSensorData);
    }
}
