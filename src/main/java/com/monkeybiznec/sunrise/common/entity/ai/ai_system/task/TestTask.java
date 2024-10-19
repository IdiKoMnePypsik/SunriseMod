package com.monkeybiznec.sunrise.common.entity.ai.ai_system.task;

import com.monkeybiznec.sunrise.common.entity.Cheetah;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.SensorType;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.Task;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.TaskManager;
import com.monkeybiznec.sunrise.common.entity.ai.ai_system.sensor.VisionSensor;
import org.jline.utils.Log;

public class TestTask extends Task<Cheetah> {
    public TestTask(TaskManager<Cheetah> pTaskManager, Cheetah pEntity, int pPriorityLevel, TaskImportance pTaskImportance) {
        super(pTaskManager, pEntity, pPriorityLevel, pTaskImportance);
    }

    @Override
    public void update() {
        /*
        VisionSensor<?> visionSensor = this.getTaskManager().getSensorManager().getSensor(SensorType.VISION, new VisionSensor<>(this.entity));
        visionSensor.getSensorData().forEach(entity -> {
            if (entity != this.entity) {
                Log.info("found entities");
            }
        });

         */
        if (this.entity.isInFluidType()) {
            this.complete();
        }
    }

    @Override
    public Task<Cheetah> decompose() {
        return new TestSubTask(this.getTaskManager(), this.entity, this.priority.getPriorityLevel(), this.taskImportance);
    }

    @Override
    protected float calculateUrgency(Cheetah pEntity) {
        return 1;
    }
}
