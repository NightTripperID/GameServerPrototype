package demo.mob.projectile.axe;

import com.sun.istack.internal.NotNull;
import demo.mob.projectile.Projectile;
import demo.spritesheets.Sprites;
import gamestate.GameState;

public class Axe extends Projectile {

    public Axe(double x, double y, double angle) {
        super(0xff00ff, x, y, 16, 16, 1, 1, true, false, angle);

        sprite = Sprites.AXE;

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
    }

    @Override
    public void update() {
        angle += (getxDir() > 0 ? .1 : -.1);
        move();
    }
}
