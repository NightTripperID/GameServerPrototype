package demo.mob.enemy.skelly;

import demo.mob.enemy.Enemy;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Skelly extends Enemy {

    private Player player;

    private AnimSprite skellyUp = new AnimSprite(SpriteSheets.SKELLY_UP, 16, 16, 4);
    private AnimSprite skellyDown = new AnimSprite(SpriteSheets.SKELLY_DOWN, 16, 16, 4);
    private AnimSprite skellyLeft = new AnimSprite(SpriteSheets.SKELLY_LEFT, 16, 16,4);
    private AnimSprite skellyRight = new AnimSprite(SpriteSheets.SKELLY_RIGHT, 16, 16,4);

    private int count;

    public Skelly(int col, double x, double y, Player player) {
        super(col, x, y, 1, 1, 16, 16, 3, 1, true);
        final int frameRate = 10;
        skellyUp.setFrameRate(frameRate);
        skellyDown.setFrameRate(frameRate);
        skellyLeft.setFrameRate(frameRate);
        skellyRight.setFrameRate(frameRate);
        currSprite = skellyDown;
        this.player = player;
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();

        if(count++ % 2 == 1) {
            move();
            count = 0;
        }
    }

    private void move() {
        if (x == player.x)
            setxSpeed(0);
        else
            setxSpeed(1);

        if (y == player.y)
            setySpeed(0);
        else
            setySpeed(1);

        if (x > player.x)
            setxDir(-1);
        else if (x < player.x)
            setxDir(1);

        if (y > player.y)
            setyDir(-1);
        else if (y < player.y)
            setyDir(1);

        xa = getxSpeed() * getxDir();
        ya = getySpeed() * getyDir();

        if(ya < 0)
            setCurrSprite(skellyUp);
        if(ya > 0)
            setCurrSprite(skellyDown);
        if(xa < 0)
            setCurrSprite(skellyLeft);
        if(xa > 0)
            setCurrSprite(skellyRight);

        commitMove(xa, ya);
    }
}