package com.monkeybiznec.sunrise.common.entity.ai.ai_system.task;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;
import net.minecraft.world.phys.Vec3;

public class TestTaskTwo extends Task<Cheetah> {
    public TestTaskTwo(TaskManager<Cheetah> pTaskManager, Cheetah pEntity, int pPriorityLevel, TaskImportance pTaskImportance) {
        super(pTaskManager, pEntity, pPriorityLevel, pTaskImportance);
    }

    @Override
    public void update() {
        this.entity.setDeltaMovement(new Vec3(0.0F, 0.050F, 0.0F));
    }

    @Override
    public Task<Cheetah> decompose() {
        return null;
    }

    @Override
    protected float calculateUrgency(Cheetah pEntity) {
        return 1;
    }
}
