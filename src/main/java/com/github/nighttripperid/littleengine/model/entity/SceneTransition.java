package com.github.nighttripperid.littleengine.model.entity;

import com.github.nighttripperid.littleengine.component.SceneStackController;

import java.util.function.Consumer;

public class SceneTransition {
    private final Consumer<SceneStackController> transition;

    public SceneTransition(Consumer<SceneStackController> transition) {
        this.transition = transition;
    }

    public void perform(SceneStackController sceneStackController) {
        transition.accept(sceneStackController);
    }
}
