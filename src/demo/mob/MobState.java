package demo.mob;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;

public abstract class MobState {

    protected Mob mob;
    protected GameState gameState;

    public MobState(@NotNull Mob mob, @NotNull GameState gameState) {
        this.mob = mob;
        this.gameState = gameState;
    }

    public abstract MobState update();
}
