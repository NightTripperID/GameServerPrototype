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

    public abstract void update();

    protected void commitMove(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        if (!mob.tileCollision((int) xa, (int) ya)) {
            mob.x += xa;
            mob.y += ya;
        }
    }
}
