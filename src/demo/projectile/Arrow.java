package demo.projectile;

import com.sun.istack.internal.NotNull;
import demo.spritesheets.ProjectileSprites;
import gamestate.GameState;

public class Arrow extends Projectile {

    public Arrow(double x, double y, double angle) {
        super(x, y, 16, 16, angle);

        currSprite = ProjectileSprites.ARROW;

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

        currState = new ArrowStateFlying(this, gameState);
    }
}
