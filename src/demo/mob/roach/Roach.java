package demo.mob.roach;

import demo.mob.Mob;
import demo.mob.treasure.Doorkey;
import demo.spritesheets.SpriteSheets;
import entity.Entity;
import graphics.AnimSprite;
import graphics.Screen;
import graphics.Sprite;

public class Roach extends Mob {

    private AnimSprite roachUp = new AnimSprite(SpriteSheets.ROACH_UP, 16, 16, 4);
    private AnimSprite roachDown = new AnimSprite(SpriteSheets.ROACH_DOWN, 16, 16, 4);
    private AnimSprite roachLeft = new AnimSprite(SpriteSheets.ROACH_LEFT, 16, 16, 4);
    private AnimSprite roachRight = new AnimSprite(SpriteSheets.ROACH_RIGHT, 16, 16, 4);

    private Sprite doorkeySprite;

    private int count;
    private static final int MAX_COUNT = 60 * 1;

    private static final int MOVE_SPEED = 3;

    private boolean hasDoorkey;

    public Roach(int col, double x, double y) {
        super(col, x, y, 1, 1, 16, 16, 3, 1, false, true);
        roachUp.setFrameRate(5);
        roachDown.setFrameRate(5);
        roachLeft.setFrameRate(5);
        roachRight.setFrameRate(5);
        setxSpeed(0);
        setySpeed(MOVE_SPEED);
        currSprite = roachDown;
    }

    public Roach(int col, double x, double y, boolean hasDoorkey) {
        this(col, x, y);
        this.hasDoorkey = hasDoorkey;
        doorkeySprite = new Sprite(SpriteSheets.MAIN_SHEET, 8, 8, 7, 0);
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();
        if(count++ == MAX_COUNT) {
            count = 0;
            switch(randomDirection()) {
                case UP:
                    setyDir(-1);
                    setxSpeed(0);
                    setySpeed(MOVE_SPEED);
                    currSprite = roachUp;
                    break;
                case DOWN:
                    setyDir(1);
                    setxSpeed(0);
                    setySpeed(MOVE_SPEED);
                    currSprite = roachDown;
                    break;
                case LEFT:
                    setxDir(-1);
                    setxSpeed(MOVE_SPEED);
                    setySpeed(0);
                    currSprite = roachLeft;
                    break;
                case RIGHT:
                    setxDir(1);
                    setxSpeed(MOVE_SPEED);
                    setySpeed(0);
                    currSprite = roachRight;
                    break;
            }
        }

        xa = getxSpeed() * getxDir();
        ya = getySpeed() * getyDir();

        commitMove(xa, ya);

        if(getHealth() <= 0) {
            if(hasDoorkey) {
                Entity doorKey = new Doorkey(0xffff00, x, y);
                doorKey.initialize(gameState);
                gameState.addEntity(doorKey);
            }
        }
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX() - 4, y - gameState.getScrollY() - 4, currSprite.getSprite());

        if(hasDoorkey)
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), doorkeySprite);
    }

    private Direction randomDirection() {
        switch(random.nextInt(4)) {
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return Direction.UP;
        }
    }
}