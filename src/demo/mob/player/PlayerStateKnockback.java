package demo.mob.player;

import demo.mob.Mob;
import demo.mob.MobState;
import demo.tile.DemoTile;
import gamestate.GameState;

public class PlayerStateKnockback extends PlayerState {

    private MobState lastPlayerState;
    private int distCount;
    private int timeCount;

    PlayerStateKnockback(Player player, GameState gameState, PlayerState lastPlayerState) {
        super(player, gameState);
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

        super.update();

        final int knockbackDist = 3 * DemoTile.SIZE;
        final int knockbackTime = 1 * 8; // about an eighth of a second

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);

        if(mob.xa != 0)
            distCount += Math.abs(mob.xa);
        if(mob.ya != 0)
            distCount += Math.abs(mob.ya);

        if(distCount >= knockbackDist || timeCount++ >= knockbackTime)
            mob.setCurrState(lastPlayerState);
    }
}
