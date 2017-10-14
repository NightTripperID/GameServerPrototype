package demo.mob.slime;

import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Slime extends Mob {

    private int count;

    private Mob player;

    public Slime(double x, double y, Mob player) {
        super(x, y, 1, 1, 16, 16, 2, 1, false, true);
        currSprite = new AnimSprite(SpriteSheets.SLIME, 16, 16, 4);
        currSprite.setFrameRate(13);
        this.player = player;
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();

        if (count++ % 2 == 1) {
            count = 0;
            move();
        }
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
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

        commitMove(xa, ya);
    }
}
