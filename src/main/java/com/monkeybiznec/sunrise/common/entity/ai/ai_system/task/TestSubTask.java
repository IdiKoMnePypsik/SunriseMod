package com.monkeybiznec.sunrise.common.entity.ai.ai_system.task;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;

public class TestSubTask extends Task<Cheetah> {
    public TestSubTask(TaskManager<Cheetah> pTaskManager, Cheetah pEntity, int pPriorityLevel, TaskImportance pTaskImportance) {
        super(pTaskManager, pEntity, pPriorityLevel, pTaskImportance);
    }

    @Override
    public void update() {
        this.entity.kill();
    }

    @Override
    public Task<Cheetah> decompose() {
        return null;
    }

    @Override
    protected float calculateUrgency(Cheetah pEntity) {
        return 0;
    }
}
