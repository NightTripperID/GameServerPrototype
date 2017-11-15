package entity;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import graphics.Renderable;
import engine.Screen;
import update.Updatable;

import java.io.Serializable;

/**
 * Abstract object representing a game entity that is updated and rendered by a GameState
 */
public abstract class Entity implements Updatable, Renderable, Serializable, Comparable<Entity> {

    private static final long serialVersionUID = 201711110911L;

    protected GameState gameState;
    private boolean removed;

    private int renderPriority;
    private static final int MIN_RENDER_PRIORITY = 0;
    private static final int MAX_RENDER_PRIORITY =  3;

    /**
     * Associates the Entity with its containing GameState.
     * @param gameState The Entity's containing GameState.
     */
    public void initialize(@NotNull GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Update method that is executed with each of the Engine's update calls. (60 times per second)
     */
    public abstract void update();

    /**
     * Render method that that is executed with each of the Engine's Renderable calls (as fast as hardware allows).
     * @param screen the screen to render to.
     */
    public abstract void render(@NotNull Screen screen);


    /**
     * Returns whether Entity is marked for removal from the GameState.
     * @return true if the entity is marked for removal, false otherwise.
     */
    public boolean removed() {
        return removed;
    }

    /**
     * Sets whether the Entity should be removed on the GameState's next removeMarkedEntities() call.
     * @param removed true if the entity should be removed, false otherwise.
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /**
     * Sets the render priority. Valid values are 0, 1, 2, and 3. Entities with a lower priority are rendered first.
     * @param priority the specified priority.
     */
    public void setRenderPriority(int priority) {
        if(priority < MIN_RENDER_PRIORITY || priority > MAX_RENDER_PRIORITY)
            throw new IllegalArgumentException("render priority must be greater than - 1 and less than 4");
        this.renderPriority = priority;
    }

    /**
     * Compares the render priority of this entity to another entity.
     * @param entity the other entity to compare to.
     * @return less than 1 if this entity has a higher render priority,
     * 0 if they have the same prioriy, and 1 if the other entiy has a
     * higher priority.
     */
    @Override
    public int compareTo(Entity entity) {
        return this.renderPriority - entity.renderPriority;
    }
}
