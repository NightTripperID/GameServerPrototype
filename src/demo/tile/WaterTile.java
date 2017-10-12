package demo.tile;

import demo.spritesheets.TileSprites;
import graphics.Sprite;

public class WaterTile extends DemoTile {

    public WaterTile() {
        super(null, true, false);
        TileSprites.WATER.setFrameRate(15);
    }

    public static void update() {
        TileSprites.WATER.update();
    }

    @Override
    public Sprite getSprite() {
        return TileSprites.WATER.getSprite();
    }
}
