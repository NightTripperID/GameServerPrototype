package com.github.nighttripperid.littleengine.model.gamestate;


import com.github.nighttripperid.littleengine.deprecated.gamestate.HashMaps;

public class Intent extends HashMaps {

    private Class<? extends GameState> gameStateClass;

    /**
     * Creates a new Intent object and sets the given GameState class metadata.
     * @param gameStateClass the GameState class metadata associated with the Intent.
     */
    public Intent(Class<? extends GameState> gameStateClass) {
        this.gameStateClass = gameStateClass;
    }

    /**
     * Returns the GameState class metadata associated with the Intent. This is used by the engine and should probably
     * never be called by the API user.
     * @return the GameState class metadata associated with the Intent.
     */
    public final Class<? extends GameState> getGameStateClass() {
        return gameStateClass;
    }
}
