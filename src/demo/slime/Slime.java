package demo.slime;

import demo.mob.Mob;
import demo.spritesheets.SlimeSprites;
import graphics.Screen;

public class Slime extends Mob {

    public Slime(double x, double y) {
        super(x, y, 1, 1, 16, 16);

        currState = new SlimeStatePatrol(this, gameState);
        currSprite = SlimeSprites.SLIME;
        currSprite.setFrameRate(13);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }
}
