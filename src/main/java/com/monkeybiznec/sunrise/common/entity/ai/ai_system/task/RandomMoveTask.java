package com.monkeybiznec.sunrise.common.entity.ai.ai_system.task;

import com.monkeybiznec.sunrise.common.entity.Elephant;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class RandomMoveTask <T extends PathfinderMob> extends Task<T> {
    private final Vec3 nextPos = null;

    public RandomMoveTask(TaskManager<T> pTaskManager, T pEntity, int pPriorityLevel, TaskImportance pTaskImportance) {
        super(pTaskManager, pEntity, pPriorityLevel, pTaskImportance);
    }

    @Override
    public boolean isApplicable() {
        if (this.entity.isVehicle()) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {

    }

    @Override
    public void complete() {
        super.complete();
    }

    @Override
    public Task<T> decompose() {
        return null;
    }

    @Override
    protected float calculateUrgency(T pEntity) {
        return 1;
    }
}
