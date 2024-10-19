package com.monkeybiznec.sunrise.common.event;

import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class TaskEvent extends Event {
    private final Task<?> task;

    public TaskEvent(Task<? extends LivingEntity> pTask) {
        this.task = pTask;
    }

    public Task<? extends LivingEntity> getTask() {
        return this.task;
    }

    public static class Started extends TaskEvent {
        public Started(Task<?> pTask) {
            super(pTask);
        }
    }

    public static class Complete extends TaskEvent {
        public Complete(Task<?> pTask) {
            super(pTask);
        }
    }
}
