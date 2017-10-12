package demo.mob.skelly;

import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Skelly extends Mob {

    public Skelly(double x, double y) {
        super(x, y, 1, 1, 16, 16, 3, 1, false, true);
        currSprite = new AnimSprite(SpriteSheets.SKELLY_DOWN, 16, 4, 4);
        currSprite.setFrameRate(10);
        currState = new SkellyStatePatrol(this, gameState);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }
}