package entity;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import graphics.Screen;

public abstract class Entity  {

    protected GameState gameState;

    public void initialize(@NotNull GameState gameState) {
        this.gameState = gameState;
    }
}
