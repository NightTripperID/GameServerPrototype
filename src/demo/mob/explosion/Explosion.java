package demo.mob.explosion;

import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Explosion extends Mob {

    private int count;

    public Explosion(int col, double x, double y) {
        super(col, x, y, 1, 1, 16, 16, 1, 0, true, false);
        currSprite = new AnimSprite(SpriteSheets.EXPLOSION, 16, 16, 3);
        currSprite.setFrameRate(5);
    }

    @Override
    public void update() {
        super.update();

        if(currSprite.getFrame() < 3)
            currSprite.update();
        if(currSprite.getFrame() == 3)
            if(count++ == 10)
                setRemoved(true);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }
}
