package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import entity.Updatable;
import graphics.Screen;
import graphics.Sprite;

public abstract class Projectile extends Mob {

    double angle;

    Projectile(double x, double y, int width, int height, double angle, int damage, int health, boolean friendly) {
        super(x, y, 1, 1, width, height, health, damage, friendly);
        this.angle = angle;
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(),
                Sprite.rotate(currSprite.getSprite(), angle));
    }
}
