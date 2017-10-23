package demo.mob.projectile.bolt;

import demo.spritesheets.Sprites;

public class BlueBolt extends Bolt {
    public BlueBolt(double x, double y, double angle) {
        super(x, y, angle);
        sprite = Sprites.BLUE_BOLT;
    }
}
