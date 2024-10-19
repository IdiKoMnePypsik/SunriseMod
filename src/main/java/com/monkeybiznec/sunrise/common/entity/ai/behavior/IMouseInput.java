package com.monkeybiznec.sunrise.common.entity.ai.behavior;

@FunctionalInterface
public interface IMouseInput {
    void onMouseClick(int pButton);

    default boolean isActionDenied(int pActionId) {
        return false;
    }
}
