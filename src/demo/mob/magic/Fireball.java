package demo.mob.magic;

import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Fireball extends Mob {

    private int pointCount;
    private static final int MAX_POINT_COUNT = 200;

    private Mob player;

    public Fireball(double x, double y, Mob player) {
        super(0xff00ff, x, y, 1, 1, 16, 16, 1, 1, true, false);
        this.player = player;
        currSprite = new AnimSprite(SpriteSheets.MAGIC_1, getWidth(), getHeight(), 4);
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();

        double x;
        double y;
        double angle;

       // Space between the spirals
        double a = .6, b = .6;

        angle = 0.2 * pointCount;
        x = (a + b * angle) * Math.cos(angle);
        y = (a + b * angle) * Math.sin(angle);

        this.x += x + player.xa;
        this.y += y + player.ya;

        if(pointCount++ == MAX_POINT_COUNT)
            setRemoved(true);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }
}
