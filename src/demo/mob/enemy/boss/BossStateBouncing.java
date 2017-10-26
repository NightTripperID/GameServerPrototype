package demo.mob.enemy.boss;

import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;

public class BossStateBouncing extends BossState {

    private int bouceDelay;
    private int bounceCount;

    private final int maxZspeed = 3;
    private double currZspeed = maxZspeed;

    private double zSpeed;

    private int zDir;

    public BossStateBouncing(Boss boss, GameState gameState) {
        super(boss, gameState);
        mob.setxSpeed(1);
        mob.setySpeed(1);
        zSpeed = currZspeed;
        mob.setCurrSprite(new AnimSprite(SpriteSheets.BOSS, 32, 32, 2, 35));
    }

    @Override
    public void update() {
        mob.getCurrSprite().update();
        if (bouceDelay++ % 2 == 0) {
            bounce();
        }
        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = (mob.getySpeed() * mob.getyDir() + (zSpeed * zDir));
        commitMove(mob.xa, mob.ya);
    }

    private void bounce() {
        zSpeed = currZspeed;
        currZspeed += 0.1 * zDir;
        if ((int) currZspeed == 0)
            zDir = 1;
        if ((int) currZspeed == maxZspeed) {
            zDir = -1;
            mob.setxDir(random.nextInt(2) == 0 ? -1 : 1);
            if (++bounceCount == 5)
                mob.setCurrState(new BossStateSpawning((Boss) mob, gameState));
        }
    }

    protected void commitMove(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        if (mob.tileCollision((int) xa, (int) ya)) {
            if(xa != 0) {
                mob.setxDir(mob.getxDir() * -1);
                xa = mob.getxSpeed() * mob.getxDir();
            }
            if(ya != 0) {
                mob.setyDir(mob.getyDir() * -1);
                ya = mob.getySpeed() * mob.getyDir();
            }
        }

        mob.x += xa;
        mob.y += ya;
    }
}
