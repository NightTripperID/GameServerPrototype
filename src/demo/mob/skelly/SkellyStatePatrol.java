package demo.mob.skelly;

import demo.mob.Mob;
import demo.mob.MobState;
import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;

import java.util.Random;

public class SkellyStatePatrol extends MobState {

    private int count;
    private Random random = new Random();

    private AnimSprite skellyUp = new AnimSprite(SpriteSheets.SKELLY_UP, 16, 16, 4);
    private AnimSprite skellyDown = new AnimSprite(SpriteSheets.SKELLY_DOWN, 16, 16, 4);

    private static final int MOVE_SPEED = 1;

    public SkellyStatePatrol(Mob mob, GameState gameState) {
        super(mob, gameState);
        skellyUp.setFrameRate(10);
        skellyDown.setFrameRate(10);
    }

    @Override
    public void update() {
        mob.getCurrSprite().update();

        if(count++ == 60 * 2) {
            count = 0;

            if(mob.getxSpeed() == MOVE_SPEED || mob.getySpeed() == MOVE_SPEED) {
                mob.setxSpeed(0);
                mob.setySpeed(0);
            } else {
                mob.setxSpeed(random.nextInt() > 0 ? MOVE_SPEED : 0);
                if(mob.getxSpeed() == MOVE_SPEED)
                    mob.setySpeed(1);
                mob.setxDir(random.nextInt() > 0 ? 1 : -1);
                mob.setyDir(random.nextInt() > 0 ? 1 : -1);
            }
        }

        if(mob.getySpeed() == MOVE_SPEED)
            mob.setCurrSprite(mob.getyDir() == -1 ? skellyUp : skellyDown);

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);
    }
}
