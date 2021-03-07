package com.github.nighttripperid.littleengine.model.entity;

import com.github.nighttripperid.littleengine.component.GameStateStackController;

import java.util.function.Consumer;

public class GameStateTransition {
    private final Consumer<GameStateStackController> transition;

    public GameStateTransition(Consumer<GameStateStackController> transition) {
        this.transition = transition;
    }

    public void perform(GameStateStackController gameStateStackController) {
        transition.accept(gameStateStackController);
    }
}
