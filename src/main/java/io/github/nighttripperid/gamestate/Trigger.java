package io.github.nighttripperid.gamestate;

import com.sun.istack.internal.NotNull;

/**
 * Object that represents a tile trigger with a runnable piece of code.
 */
public class Trigger {

    public final boolean active;
    public final Runnable runnable;

    /**
     * Creates a new Trigger with a given Runnable.
     * @param runnable The given runnable piece of code that executes when the Trigger is activated.
     * @param active Indicates whether or not the trigger is active (activates when user presses a button)
     *               Or passive (activates when user traverses over a trigger tile.
     */
    public Trigger(@NotNull Runnable runnable, boolean active) {
        this.runnable = runnable;
        this.active = active;
    }
}
