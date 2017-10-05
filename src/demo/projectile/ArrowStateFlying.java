package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;

public class ArrowStateFlying extends ProjectileState {

    public ArrowStateFlying(@NotNull Mob mob) {
        super(mob);
    }

    @Override
    public MobState update() {

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        mob.x += mob.xa;
        mob.y += mob.ya;

        if (mob.x < 0
                || mob.xa + mob.getWidth() > mob.getGameState().getScreenWidth()
                || mob.y < 0
                || mob.ya + mob.getHeight() > mob.getGameState().getScreenHeight()) {

            mob.getGameState().removeEntity(mob);
        }

        return this;
    }
}
