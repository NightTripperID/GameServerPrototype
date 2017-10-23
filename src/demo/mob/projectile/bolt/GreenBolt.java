package demo.mob.projectile.bolt;

import demo.spritesheets.Sprites;

public class GreenBolt extends Bolt {
    public GreenBolt(double x, double y, double angle) {
        super(x, y, angle);
        sprite = Sprites.GREEN_BOLT;
    }
}
