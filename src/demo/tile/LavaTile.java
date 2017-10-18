package demo.tile;

import demo.spritesheets.TileSprites;
import graphics.Sprite;

public class LavaTile extends DemoTile {

    public LavaTile() {
        super(null, true);
        TileSprites.LAVA.setFrameRate(15);
    }

    public static void update() {
        TileSprites.LAVA.update();
    }

    @Override
    public Sprite getSprite() {
        return TileSprites.LAVA.getSprite();
    }
}