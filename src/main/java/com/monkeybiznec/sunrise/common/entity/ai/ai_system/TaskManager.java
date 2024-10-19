package com.monkeybiznec.sunrise.common.entity.ai.ai_system;

import com.monkeybiznec.sunrise.common.entity.ai.behavior.EntityActionScheduler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskManager<T extends LivingEntity> {
    private final T entity;
    public Task<T> currentTask;
    public Task<T> subTask;
    public final List<Task<T>> taskQueue = new ArrayList<>();
    public final CopyOnWriteArrayList<Task<T>> parallelTasks = new CopyOnWriteArrayList<>();
    public final SensorManager<T> sensorManager;
    public final EntityActionScheduler<T> actionScheduler;

    public TaskManager(T pEntity) {
        this.entity = pEntity;
        this.sensorManager = new SensorManager<>();
        this.actionScheduler = new EntityActionScheduler<>(this.entity);
    }

    public TaskManager<T> addTask(Task<T> pTask) {
        this.taskQueue.add(pTask);
        return this;
    }

    public TaskManager<T> addParallelTask(Task<T> pTask) {
        this.parallelTasks.add(pTask);
        return this;
    }

    public SensorManager<T> addSensor(SensorType pSensorType, ISensor pSensor) {
        this.sensorManager.addSensor(pSensorType, pSensor);
        return this.sensorManager;
    }

    public Task<T> getTaskByIndex(int pIndex) {
        if (pIndex >= 0 && pIndex < this.taskQueue.size()) {
            return this.taskQueue.get(pIndex);
        }
        return null;
    }

    private void updateSubTask() {
        if (this.subTask != null) {
            this.subTask.update();
            if (this.subTask.isComplete()) {
                this.subTask.complete();
                this.subTask = null;
            }
        }
    }

    private void updateCurrentTask() {
        if (this.currentTask != null) {
            this.currentTask.update();
            if (this.currentTask.isComplete()) {
                this.currentTask.complete();
                this.currentTask = null;
            } else {
                Task<T> nextSubTask = this.currentTask.decompose();
                if (nextSubTask != null) {
                    this.switchToNextTask(nextSubTask, true);
                }
            }
        }
    }

    public void update() {
        this.actionScheduler.update();
        if (this.entity instanceof Mob mob && mob.isNoAi()) {
            return;
        }
        this.sensorManager.updateSensors();
        for (Task<T> task : this.parallelTasks) {
            if (task.isApplicable()) {
                task.update();
                if (task.isComplete()) {
                    task.complete();
                }
            }
        }
        this.updateSubTask();
        if (this.subTask == null) {
            Task<T> nextTask = this.selectNextTask();
            if (nextTask != null && this.shouldSwitchToTask(nextTask)) {
                this.switchToNextTask(nextTask, false);
            }
            this.updateCurrentTask();
        }
    }

    private boolean shouldSwitchToTask(Task<T> pNextTask) {
        return this.currentTask == null || (this.currentTask.canInterrupt() && pNextTask.getPriority(this.entity).getPriorityLevel() > this.currentTask.getPriority(this.entity).getPriorityLevel());
    }

    private void switchToNextTask(Task<T> pNextTask, boolean pSubTask) {
        if (!pSubTask) {
            if (this.currentTask != null) {
                this.currentTask.complete();
            }
            this.currentTask = pNextTask;
            this.currentTask.start();
        } else {
            if (this.subTask != null) {
                this.subTask.complete();
            }
            this.subTask = pNextTask;
            this.subTask.start();
        }
    }

    private Task<T> selectNextTask() {
        this.taskQueue.sort((task1, task2) -> {
            int priorityComparison = Integer.compare(task2.getPriority(this.entity).getPriorityLevel(), task1.getPriority(this.entity).getPriorityLevel());
            if (priorityComparison == 0) {
                return Integer.compare(task2.getTaskImportance().getImportanceLevel(), task1.getTaskImportance().getImportanceLevel());
            }
            return priorityComparison;
        });
        for (Task<T> task : this.taskQueue) {
            if (task.isApplicable()) {
                return task;
            }
        }
        return null;
    }
}
