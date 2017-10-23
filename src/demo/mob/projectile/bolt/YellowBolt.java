package demo.mob.projectile.bolt;

import demo.spritesheets.Sprites;

public class YellowBolt extends Bolt {
    public YellowBolt(double x, double y, double angle) {
        super(x, y, angle);
        sprite = Sprites.YELLOW_BOLT;
    }
}
