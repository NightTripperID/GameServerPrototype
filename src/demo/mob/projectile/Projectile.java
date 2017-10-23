package demo.mob.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import graphics.Screen;
import graphics.Sprite;

public abstract class Projectile extends Mob {

    protected double angle;

    protected Sprite sprite;

    protected Projectile(int col, double x, double y, int width, int height, int damage, int health,
               boolean friendly, boolean vulnerable, double angle) {
        super(col, x, y, 1, 1, width, height, health, damage, friendly, vulnerable);
        this.angle = angle;
    }

    @Override
    public void update() {
        move();
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(),
                Sprite.rotate(sprite, angle));
    }

    protected void move() {
        xa = getxSpeed() * getxDir();
        ya = getySpeed() * getyDir();

        if (x + getWidth() - gameState.getScrollX() < 0
                || x - gameState.getScrollX() > gameState.getScreenWidth()
                || y + getHeight() - gameState.getScrollY() < 0
                || y - gameState.getScrollY() > gameState.getScreenHeight()
                || tileCollision((int) xa, (int) ya)) {

            setRemoved(true);
        }

        x += xa;
        y += ya;
    }

    @Override
    public void runCollision(Mob mob) {
        if(friendly() != mob.friendly())
            if(mob.vulnerable()) {
                mob.assignDamage(getDamage());
                this.setRemoved(true);
            }
    }
}
