package demo.slime;

import demo.mob.Mob;
import demo.spritesheets.SlimeSprites;
import graphics.Screen;

public class Slime extends Mob {

    private static final int XDIR = 1;
    private static final int YDIR = 1;
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final int HEALTH = 2;
    private static final int DAMAGE = 1;
    private static final boolean FRIENDLY = false;

    public Slime(double x, double y) {
        super(x, y, XDIR, YDIR, WIDTH, HEIGHT, HEALTH, DAMAGE, FRIENDLY);
        currState = new SlimeStatePatrol(this, gameState);
        currSprite = SlimeSprites.SLIME;
        currSprite.setFrameRate(13);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }
}
