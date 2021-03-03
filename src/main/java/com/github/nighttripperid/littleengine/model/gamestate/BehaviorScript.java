package com.github.nighttripperid.littleengine.model.gamestate;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Consumer;

public class BehaviorScript {
    @Getter(AccessLevel.NONE)
    private final Consumer<GameMap> script;

    public BehaviorScript(Consumer<GameMap> script) {
        this.script = script;
    }

    public void run(GameMap gameMap) {
        script.accept(gameMap);
    }
}
