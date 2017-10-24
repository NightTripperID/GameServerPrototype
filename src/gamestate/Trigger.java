package gamestate;

import com.sun.istack.internal.NotNull;

public class Trigger {

    public final boolean active;
    public final Runnable runnable;

    public Trigger(@NotNull Runnable runnable, boolean active) {
        this.runnable = runnable;
        this.active = active;
    }
}
