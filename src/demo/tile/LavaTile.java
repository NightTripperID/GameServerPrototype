package demo.tile;

import demo.spritesheets.Sprites;
import graphics.Sprite;

public class LavaTile extends DemoTile {

    public LavaTile() {
        super(null, true);
        Sprites.LAVA.setFrameRate(15);
    }

    public static void update() {
        Sprites.LAVA.update();
    }

    @Override
    public Sprite getSprite() {
        return Sprites.LAVA.getSprite();
    }
}