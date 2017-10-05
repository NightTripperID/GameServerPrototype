package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.projectile.Arrow;

public class PlayerStateShooting extends PlayerState {

    private PlayerState lastPlayerState;

    private double angle;

    PlayerStateShooting(@NotNull Player player, @NotNull PlayerState lastPlayerState, double angle) {
        super(player);
        this.lastPlayerState = lastPlayerState;
        this.angle = angle;
    }

    @Override
    public MobState update() {

        Arrow arrow = new Arrow(mob.x, mob.y, angle);

        mob.getGameState().addEntity(arrow);

        return lastPlayerState;
    }
}
