package entity;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;

public abstract class Entity implements Updatable, Renderable  {

    protected GameState gameState;

    private boolean removed;

    public void initialize(@NotNull GameState gameState) {
        this.gameState = gameState;
    }

    public boolean removed() {
        return removed;
    }

    public void setRemoved(boolean b) {
        removed = b;
    }
}
