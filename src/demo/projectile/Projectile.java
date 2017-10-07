package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import graphics.Screen;
import graphics.Sprite;

public abstract class Projectile extends Mob {


    private double angle;

    protected Projectile(double x, double y, int width, int height, double angle) {
        super(x, y, 1, 1, width, height);
        this.angle = angle;
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(),
                Sprite.rotate(currSprite.getSprite(), angle));
    }
}
