package demo.mob.enemy.dinodrac;

import demo.mob.treasure.Doorkey;
import demo.zone.Zone;
import demo.mob.enemy.Enemy;
import demo.mob.player.Player;
import demo.mob.projectile.bolt.BlueBolt;
import demo.mob.projectile.bolt.GreenBolt;
import demo.mob.projectile.bolt.YellowBolt;
import demo.spritesheets.SpriteSheets;
import entity.Entity;
import gamestate.GameState;
import graphics.AnimSprite;

public class DinoDrac extends Enemy {

    private AnimSprite dinoDracUp = new AnimSprite(SpriteSheets.DRAC_UP, 16, 16, 4);
    private AnimSprite dinoDracDown = new AnimSprite(SpriteSheets.DRAC_DOWN, 16, 16, 4);
    private AnimSprite dinoDracLeft = new AnimSprite(SpriteSheets.DRAC_LEFT, 16, 16, 4);
    private AnimSprite dinoDracRight = new AnimSprite(SpriteSheets.DRAC_RIGHT, 16, 16, 4);

    private Player player;

    private static final int MOVE_SPEED = 1;

    private int moveCount;
    private int shootCount;

    public DinoDrac(int col, double x, double y) {
        super(col, x, y, 1, 1, 16, 16, 10, 1, true);

        final int frameRate = 15;

        dinoDracUp.setFrameRate(frameRate);
        dinoDracDown.setFrameRate(frameRate);
        dinoDracLeft.setFrameRate(frameRate);
        dinoDracRight.setFrameRate(frameRate);
        currSprite = dinoDracDown;

        direction = Direction.DOWN;
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        player = ((Zone) gameState).getPlayer();
    }

    @Override
    public void update() {
        super.update();

        currSprite.update();

        if(moveCount++ % 2 == 1) {
            moveCount = 0;
            move();
        }

        if(shootCount++ == 60 * 2) {
            shootCount = 0;
            shoot();
        }
    }

    private void move() {
        double dx = x - player.x;
        double dy = y - player.y;

        if (Math.abs(dx) < Math.abs(dy)) {
            // faces up or down tracks, player horizontally
            if (y < player.y) {
                direction = Direction.DOWN;
                setCurrSprite(dinoDracDown);
                trackHorizontally();
            } else  if (y > player.y) {
                direction = Direction.UP;
                setCurrSprite(dinoDracUp);
                trackHorizontally();
            }

        } else if(Math.abs(dx) > Math.abs(dy)) {
            // faces left or right, tracks player vertically
            if (x < player.x) {
                direction = Direction.RIGHT;
                setCurrSprite(dinoDracRight);
                trackVertically();
            } else if (x > player.x) {
                direction = Direction.LEFT;
                setCurrSprite(dinoDracLeft);
                trackVertically();
            }
        }

        xa = getxSpeed() * getxDir();
        ya = getySpeed() * getyDir();

        commitMove(xa, ya);
    }

    private void shoot() {

        DeltaPoint green = new DeltaPoint();
        DeltaPoint blue = new DeltaPoint();
        DeltaPoint yellow = new DeltaPoint();

        final int offset = 10;

        switch(direction) {
            case UP:
                green.set(x - offset, y - offset);
                blue.set(x, y - offset);
                yellow.set(x + offset, y - offset);
                break;
            case DOWN:
                green.set(x - offset, y + offset);
                blue.set(x, y + offset);
                yellow.set(x + offset, y + offset);
                break;
            case LEFT:
                green.set(x - offset, y - offset);
                blue.set(x - offset, y);
                yellow.set(x - offset, y + offset);
                break;
            case RIGHT:
                green.set(x + offset, y - offset);
                blue.set(x + offset, y);
                yellow.set(x + offset, y + offset);
                break;
        }

        double angle = Math.atan2(green.y, green.x);
        Entity greenBolt = new GreenBolt(x, y, angle);
        greenBolt.initialize(gameState);
        gameState.addEntity(greenBolt);

        angle = Math.atan2(blue.y, blue.x);
        Entity blueBolt = new BlueBolt(x, y, angle);
        blueBolt.initialize(gameState);
        gameState.addEntity(blueBolt);

        angle = Math.atan2(yellow.y, yellow.x);
        Entity yellowBolt = new YellowBolt(x, y, angle);
        yellowBolt.initialize(gameState);
        gameState.addEntity(yellowBolt);
    }

    private void trackHorizontally() {
        setySpeed(0);

        if (x < player.x) {
            setxSpeed(MOVE_SPEED);
            setxDir(1);
        } else if (x > player.x) {
            setxSpeed(MOVE_SPEED);
            setxDir(-1);
        } else {
            setxSpeed(0);
        }
    }

    private void trackVertically() {
        setxSpeed(0);

        if (y < player.y) {
            setyDir(1);
            setySpeed(MOVE_SPEED);
        }
        else if (y > player.y) {
            setyDir(-1);
            setySpeed(MOVE_SPEED);
        } else {
            setySpeed(0);
        }
    }

    private class DeltaPoint {
        double x;
        double y;

        void set(double dx, double dy) {
            this.x = dx - DinoDrac.this.x;
            this.y = dy - DinoDrac.this.y;
        }
    }

    @Override
    protected void dropItem() {
        Entity doorkey = new Doorkey(0xffff0000, x, y);
        doorkey.initialize(gameState);
        gameState.addEntity(doorkey);
    }
}
