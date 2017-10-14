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
    private AnimSprite skellyLeft = new AnimSprite(SpriteSheets.SKELLY_LEFT, 16, 16,4);
    private AnimSprite skellyRight = new AnimSprite(SpriteSheets.SKELLY_RIGHT, 16, 16,4);

    public SkellyStatePatrol(Mob mob, GameState gameState) {
        super(mob, gameState);
        final int frameRate = 10;
        skellyUp.setFrameRate(frameRate);
        skellyDown.setFrameRate(frameRate);
        skellyLeft.setFrameRate(frameRate);
        skellyRight.setFrameRate(frameRate);
    }

    @Override
    public void update() {
//        mob.getCurrSprite().update();
//
//        if(count++ == 60 * 2) {
//            count = 0;
//
//            final int moveSpeed = 1;
//
//            if(mob.getxSpeed() == moveSpeed || mob.getySpeed() == moveSpeed) {
//                mob.setxSpeed(0);
//                mob.setySpeed(0);
//            } else {
//                mob.setxSpeed(random.nextInt() > 0 ? moveSpeed : 0);
//                mob.setySpeed(random.nextInt() > 0 ? moveSpeed : 0);
//
//                mob.setxDir(random.nextInt() > 0 ? 1 : -1);
//                mob.setyDir(random.nextInt() > 0 ? 1 : -1);
//            }
//        }
//
//        mob.xa = mob.getxSpeed() * mob.getxDir();
//        mob.ya = mob.getySpeed() * mob.getyDir();
//
//        if(mob.ya < 0)
//            mob.setCurrSprite(skellyUp);
//        if(mob.ya > 0)
//            mob.setCurrSprite(skellyDown);
//        if(mob.xa < 0)
//            mob.setCurrSprite(skellyLeft);
//        if(mob.xa > 0)
//            mob.setCurrSprite(skellyRight);
//
//        commitMove(mob.xa, mob.ya);
    }
}
