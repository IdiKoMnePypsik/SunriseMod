package com.monkeybiznec.sunrise.common.entity.ai.ai_system;

import com.monkeybiznec.sunrise.common.entity.ai.goal.MultiAttackableTargetGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

import java.util.function.Predicate;

public abstract class Task<T extends LivingEntity> {
    private final TaskManager<T> taskManager;
    protected T entity;
    protected Priority priority;
    protected final TaskImportance taskImportance;
    protected boolean completed;
    private Predicate<T> startCondition = condition -> true;

    public Task(TaskManager<T> pTaskManager, T pEntity, int pPriorityLevel, TaskImportance pTaskImportance) {
        this.taskManager = pTaskManager;
        this.entity = pEntity;
        this.priority = new Priority(pPriorityLevel, 0);
        this.taskImportance = pTaskImportance;
        this.completed = false;
    }

    public Task<T> setStartCondition(Predicate<T> pCondition) {
        this.startCondition = pCondition;
        return this;
    }

    public TaskManager<T> getTaskManager() {
        return this.taskManager;
    }

    public boolean isApplicable() {
        return this.startCondition == null || this.startCondition.test(this.entity);
    }

    public void start() {
        this.completed = false;
    }

    public abstract void update();

    public boolean isComplete() {
        return this.completed;
    }

    public void complete() {
        this.completed = true;
    }

    public abstract Task<T> decompose();

    public boolean canInterrupt() {
        return true;
    }

    public Priority getPriority(T pEntity) {
        float urgency = this.calculateUrgency(pEntity);
        return new Priority(this.priority.getPriorityLevel(), urgency);
    }

    public TaskImportance getTaskImportance() {
        return this.taskImportance;
    }

    protected abstract float calculateUrgency(T pEntity);

    public enum TaskImportance {
        HIGH(3),
        MEDIUM(2),
        LOW(1);

        private final int importanceLevel;

        TaskImportance(int pImportanceLevel) {
            this.importanceLevel = pImportanceLevel;
        }

        public int getImportanceLevel() {
            return this.importanceLevel;
        }
    }
}
