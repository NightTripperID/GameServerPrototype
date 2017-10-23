package demo.tile;

import demo.spritesheets.Sprites;
import graphics.Sprite;

public class PurpleLavaTile extends DemoTile {

    public PurpleLavaTile() {
        super(null, true);
        Sprites.PURPLE_LAVA.setFrameRate(15);
    }

    public static void update() {
        Sprites.PURPLE_LAVA.update();
    }

    @Override
    public Sprite getSprite() {
        return Sprites.PURPLE_LAVA.getSprite();
    }
}
