package demo.mob.enemy.boss;

import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;

public class BossStateBouncing extends BossState {

    private int bouceDelay;
    private int bounceCount;

    private final int maxYspeed = 3;
    private float currYSpeed = maxYspeed;

    private double yStart;

    public BossStateBouncing(Boss boss, GameState gameState) {
        super(boss, gameState);
        yStart = mob.y;
        mob.setxSpeed(1);
        mob.setySpeed(currYSpeed);
        mob.setCurrSprite(new AnimSprite(SpriteSheets.BOSS, 32, 32, 2, 35));
    }

    @Override
    public void update() {
        mob.getCurrSprite().update();
        if(bouceDelay++ % 2 == 0) {
            bounce();
        }
        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();
        commitMove(mob.xa, mob.ya);
    }

    private void bounce() {
        mob.setySpeed(currYSpeed);
        currYSpeed += 0.1 * mob.getyDir();
        if((int) currYSpeed == 0)
            mob.setyDir(1);
        if((int) currYSpeed == maxYspeed) {
            mob.setyDir(-1);
            mob.y = yStart;
            if(++bounceCount == 5)
                mob.setCurrState(new BossStateSpawning((Boss) mob, gameState));
        }
    }

    public void commitMove(double xa, double ya) {
        if (mob.tileCollision((int) xa, (int) ya)) {
            mob.setxDir(mob.getxDir() * -1);
            xa = mob.getxSpeed() * mob.getxDir();
        }

        mob.x += xa;
        mob.y += ya;
    }
}
