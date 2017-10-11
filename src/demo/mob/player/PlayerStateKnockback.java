package demo.mob.player;

import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;

public class PlayerStateKnockback extends PlayerState {

    private MobState lastPlayerState;
    private int count;

    PlayerStateKnockback(Mob mob, GameState gameState, PlayerState lastPlayerState) {
        super(mob, gameState);
        this.lastPlayerState = lastPlayerState;

        final int knockbackSpeed = 7;

        if (mob.direction == Mob.Direction.UP) {
            mob.setySpeed(knockbackSpeed);
            mob.setyDir(1);
        }

        else if (mob.direction == Mob.Direction.DOWN) {
            mob.setySpeed(knockbackSpeed);
            mob.setyDir(-1);
        }

        else if (mob.direction == Mob.Direction.LEFT) {
            mob.setxSpeed(knockbackSpeed);
            mob.setxDir(1);
        }

        else if (mob.direction == Mob.Direction.RIGHT) {
            mob.setxSpeed(knockbackSpeed);
            mob.setxDir(-1);
        }
    }

    @Override
    public void update() {

        final int knockbackDistance = 48;

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);

        if(mob.xa != 0)
            count += Math.abs(mob.xa);
        if(mob.ya != 0)
            count += Math.abs(mob.ya);

        if(count >= knockbackDistance)
            mob.setCurrState(lastPlayerState);
    }
}
