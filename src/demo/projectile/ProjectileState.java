package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;

public abstract class ProjectileState extends MobState {

    public ProjectileState(@NotNull Mob mob) {
        super(mob);
    }
}
