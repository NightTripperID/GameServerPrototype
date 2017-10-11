package demo.mob.slime;

import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;

import java.util.Random;

public class SlimeStatePatrol extends MobState {

    private int count;
    private Random random = new Random();

    SlimeStatePatrol(Mob mob, GameState gameState) {
        super(mob, gameState);
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

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);
    }
}
