package com.monkeybiznec.sunrise.common.entity.ai.ai_system;

import java.util.HashMap;
import java.util.Map;

public class SensorData<K, V> {
    private final Map<K, V> data = new HashMap<>();

    public void addData(K pKey, V pValue) {
        this.data.put(pKey, pValue);
    }

    public V getData(K pKey) {
        return this.data.get(pKey);
    }

    public boolean hasData(K pKey) {
        return this.data.containsKey(pKey);
    }

    public void clear() {
        this.data.clear();
    }
}
