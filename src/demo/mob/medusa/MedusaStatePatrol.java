package demo.mob.medusa;

import demo.mob.Mob;
import demo.mob.MobState;
import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;

import java.util.Random;

public class MedusaStatePatrol extends MobState {

    private int count;
    private Random random = new Random();

    private AnimSprite medusaUp = new AnimSprite(SpriteSheets.MEDUSA_UP, 16, 16, 2);
    private AnimSprite medusaDown = new AnimSprite(SpriteSheets.MEDUSA_DOWN, 16, 16,2);

    MedusaStatePatrol(Mob mob, GameState gameState) {
        super(mob, gameState);

        medusaUp.setFrameRate(20);
        medusaDown.setFrameRate(20);
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

        if(mob.ya == -1)
            mob.setCurrSprite(medusaUp);
        else
            mob.setCurrSprite(medusaDown);

        commitMove(mob.xa, mob.ya);
    }
}
