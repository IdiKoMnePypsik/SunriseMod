package com.monkeybiznec.sunrise.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomSelector<V> {
    private final Random random = new Random();
    private final Map<V, Integer> chanceMap = new HashMap<>();
    private final Map<V, Double> weightMap = new HashMap<>();

    public RandomSelector<V> addChance(V pElement, int pChance) {
        this.chanceMap.put(pElement, pChance);
        return this;
    }

    public RandomSelector<V> addWeight(V pElement, double pWeight) {
        this.weightMap.put(pElement, pWeight);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getRandomByPercent() {
        int total = this.chanceMap.values().stream().mapToInt(Integer::intValue).sum();
        if (total != 100) {
            throw new IllegalArgumentException("The sum of the odds must be equal to 100");
        }
        int randomValue = this.random.nextInt(100);
        int cumulative = 0;
        for (Map.Entry<V, Integer> entry : this.chanceMap.entrySet()) {
            cumulative += entry.getValue();
            if (randomValue < cumulative) {
                return (T) entry.getKey();
            }
        }
        throw new IllegalArgumentException("The odds are set incorrectly");
    }

    @SuppressWarnings("unchecked")
    public <T> T getRandomByWeight() {
        double totalWeight = this.weightMap.values().stream().mapToDouble(Double::doubleValue).sum();
        double randomValue = this.random.nextDouble() * totalWeight;
        double cumulativeWeight = 0.0;
        for (Map.Entry<V, Double> entry : this.weightMap.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (randomValue <= cumulativeWeight) {
                return (T) entry.getKey();
            }
        }
        throw new IllegalArgumentException("Incorrectly set weights");
    }
}
