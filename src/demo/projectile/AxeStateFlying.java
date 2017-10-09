package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;
import graphics.Screen;
import server.Server;

public class AxeStateFlying extends ProjectileState {

    public AxeStateFlying(@NotNull Mob mob, @NotNull GameState gameState) {
        super(mob, gameState);
    }

    @Override
    public void update() {

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        if (mob.x + mob.getWidth() - gameState.getScrollX() < 0
                || mob.x - gameState.getScrollX() > gameState.getScreenWidth()
                || mob.y + mob.getHeight() - gameState.getScrollY() < 0
                || mob.y - gameState.getScrollY() > gameState.getScreenHeight()
                || mob.tileCollision((int) mob.xa, (int) mob.ya)) {

            gameState.removeEntity(mob);
        }

        mob.x += mob.xa;
        mob.y += mob.ya;
    }
}
