package com.monkeybiznec.sunrise.common.entity.ai.behavior;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityActionScheduler<T extends Entity> {
    private int tickCounter;
    private final T entity;
    private final List<ActionSchedule <T>> actions = new ArrayList<>();

    public EntityActionScheduler(T pEntity) {
        this.entity = pEntity;
    }

    public EntityActionScheduler<T> addAction(IAction<T> pAction, int pChance, int pInterval) {
        this.actions.add(new ActionSchedule <>(pAction, pChance, pInterval));
        return this;
    }

    public void update() {
        this.tickCounter++;
        for (ActionSchedule <T> config : this.actions) {
            if (this.tickCounter % config.interval == 0) {
                if (this.entity.level().getRandom().nextInt(100) < config.chance) {
                    config.action.action(this.entity);
                }
            }
        }
    }

    @FunctionalInterface
    public interface IAction<T extends Entity> {
        void action(T pEntity);
    }

    private record ActionSchedule<T extends Entity>(IAction<T> action, int chance, int interval) {}
}
