package entity;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import graphics.Screen;

/**
 * Abstract object representing a game entity that is updated and rendered by a GameState
 */
public abstract class Entity {

    protected GameState gameState;

    private boolean removed;

    /**
     * Associates the Entity with its containing GameState.
     * @param gameState The Entity's containing GameState.
     */
    public void initialize(@NotNull GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Update method that is executed with each of the Server's update calls. (60 times per second)
     */
    public abstract void update();

    /**
     * Render method that that is executed with each of the Server's render calls (as fast as hardware allows).
     * @param screen
     */
    public abstract void render(@NotNull Screen screen);


    /**
     * Returns whether Entity is marked for removal from the GameState.
     * @return
     */
    public boolean removed() {
        return removed;
    }

    /**
     * Sets whether the Entity should be removed on the GameState's next removeMarkedEntities() call.
     * @param b
     */
    public void setRemoved(boolean b) {
        removed = b;
    }
}
