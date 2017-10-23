package demo.mob.enemy.medusa;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.tile.DemoTile;
import gamestate.GameState;

public class MedusaStatePatrol extends MedusaState {

    private int count;

    MedusaStatePatrol(Mob mob, GameState gameState, Player player) {
        super(mob, gameState, player);
        mob.setxSpeed(0);
        mob.setySpeed(0);
    }

    @Override
    public void update() {
        mob.getCurrSprite().update();

        if(count++ == 60 * 2) {
            count = 0;

            if(mob.getxSpeed() > 0 || mob.getySpeed() > 0) {
                mob.setxSpeed(0);
                mob.setySpeed(0);
            } else {
                mob.setxSpeed(random.nextInt() > 0 ? 1 : 0);
                if(mob.getxSpeed() == 0)
                    mob.setySpeed(1);
                mob.setxDir(random.nextInt() > 0 ? 1 : -1);
                mob.setyDir(random.nextInt() > 0 ? 1 : -1);
            }
        }

        final int chargeDist = DemoTile.SIZE * 6;

        if(mob.x > player.x && mob.x < player.x + player.getWidth())
            if(Math.abs(mob.y - player.y) <= chargeDist)
                mob.setCurrState(new MedusaStateCharge(mob, gameState, player));

        if(mob.y > player.y && mob.y < player.y + player.getHeight())
            if(Math.abs(mob.x - player.x) <= chargeDist)
                mob.setCurrState(new MedusaStateCharge(mob, gameState, player));

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        if(mob.ya == -1)
            mob.setCurrSprite(medusaUp);
        else
            mob.setCurrSprite(medusaDown);

        commitMove(mob.xa, mob.ya);
    }
}
