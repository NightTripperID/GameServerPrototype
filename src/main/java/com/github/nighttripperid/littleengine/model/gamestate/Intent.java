package com.github.nighttripperid.littleengine.model.gamestate;

public class Intent extends HashMaps {

    private Class<? extends GameState> gameStateClass;

    public Intent(Class<? extends GameState> gameStateClass) {
        this.gameStateClass = gameStateClass;
    }

    public final Class<? extends GameState> getGameStateClass() {
        return gameStateClass;
    }
}
