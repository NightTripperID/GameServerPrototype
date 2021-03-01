package com.github.nighttripperid.littleengine.model.gamestate;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.function.Consumer;

public class UpdateScript {
    @Getter(AccessLevel.NONE)
    private final Consumer<GameMap> updateScript;

    public UpdateScript(Consumer<GameMap> updateScript) {
        this.updateScript = updateScript;
    }

    public void run(GameMap gameMap) {
        updateScript.accept(gameMap);
    }
}
