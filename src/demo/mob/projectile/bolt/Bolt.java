package demo.mob.projectile.bolt;

import demo.mob.projectile.Projectile;

abstract class Bolt extends Projectile {

    Bolt(double x, double y, double angle) {
        super(0xff00ff, x, y, 8, 8, 1, 1, false, false, angle);

        double angleVelocity = Math.sqrt(2); // sqrt(1^2 + 1^2), i.e. hypotenuse from pythagorean theorem

        double xSpeed = Math.cos(angle) * angleVelocity; // cos(angle) = adjacent / hypotenuse
        double ySpeed = Math.sin(angle) * angleVelocity; // sin(angle) = opposite / hypotenuse

        setxDir(xSpeed < 0 ? -1 : 1);
        setyDir(ySpeed < 0 ? -1 : 1);

        xSpeed = Math.abs(xSpeed);
        ySpeed = Math.abs(ySpeed);

        setxSpeed(xSpeed);
        setySpeed(ySpeed);
    }
}
