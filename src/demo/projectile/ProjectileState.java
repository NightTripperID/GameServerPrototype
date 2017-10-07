package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;

public abstract class ProjectileState extends MobState {

    public ProjectileState(@NotNull Mob mob, @NotNull GameState gameState) {
        super(mob, gameState);
    }
}
