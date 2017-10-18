package demo.mob.medusa;

import demo.mob.Mob;
import demo.mob.player.Player;
import gamestate.GameState;

public class MedusaStateCharge extends MedusaState {

    private int count;

    MedusaStateCharge(Mob mob, GameState gameState, Player player) {
        super(mob, gameState, player);

        int dx = (int) (player.x - mob.x);
        int dy = (int) (player.y - mob.y);

        if(dx  < 0)
            mob.setxDir(-1);
        else
            mob.setxDir(1);

        if(dy < 0)
            mob.setyDir(-1);
        else
            mob.setyDir(1);

        final int chargeSpeed = 2;

        if(Math.abs(dx) < Math.abs(dy)) {
            mob.setxSpeed(0);
            mob.setySpeed(chargeSpeed);
        } else {
            mob.setxSpeed(chargeSpeed);
            mob.setySpeed(0);
        }
    }

    @Override
    public void update() {

        final int chargeTime = 60 * 1;

        if(count++ == chargeTime)
            mob.setCurrState(new MedusaStatePatrol(mob, gameState, player));

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        if(mob.ya == -1)
            mob.setCurrSprite(medusaUp);
        else
            mob.setCurrSprite(medusaDown);

        commitMove(mob.xa, mob.ya);
    }
}
