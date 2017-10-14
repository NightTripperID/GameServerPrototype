package demo.mob.projectile;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;
import graphics.Screen;
import graphics.Sprite;

public class Axe extends Projectile {

    public Axe(double x, double y, double angle) {
        super(x, y, 16, 16, 1, 1, true, false, angle);

        currSprite = new AnimSprite(SpriteSheets.ARROW, 16, 16, 1);

        double angleVelocity = Math.sqrt(8); // sqrt(2^2 + 2^2), i.e. hypotenuse from  pythagorean theorem

        double xSpeed = Math.cos(angle) * angleVelocity; // cos(angle) = adjacent / hypotenuse
        double ySpeed = Math.sin(angle) * angleVelocity; // sin(angle) = opposite / hypotenuse

        setxDir(xSpeed < 0 ? -1 : 1);
        setyDir(ySpeed < 0 ? -1 : 1);

        xSpeed = Math.abs(xSpeed);
        ySpeed = Math.abs(ySpeed);

        setxSpeed(xSpeed);
        setySpeed(ySpeed);
    }

    @Override
    public void initialize(@NotNull GameState gameState) {
        super.initialize(gameState);
        currState = new AxeStateFlying(this, gameState);
    }

    @Override
    public void update() {
        super.update();
        angle += (getxDir() > 0 ? .1 : -.1);
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(),
                Sprite.rotate(currSprite.getSprite(), angle));
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
