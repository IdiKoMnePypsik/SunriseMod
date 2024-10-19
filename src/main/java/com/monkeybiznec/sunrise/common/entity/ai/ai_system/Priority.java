package com.monkeybiznec.sunrise.common.entity.ai.ai_system;

public class Priority {
    private final int priorityLevel;
    private final float urgency;

    public Priority(int pPriorityLevel, float pUrgency) {
        this.priorityLevel = pPriorityLevel;
        this.urgency = pUrgency;
    }
    public int getPriorityLevel() {
        return this.priorityLevel;
    }

    public float getUrgency() {
        return this.urgency;
    }

    public float getOverallScore() {
        return this.priorityLevel + this.urgency;
    }
}
