package demo.tile;

import demo.spritesheets.Sprites;
import graphics.Sprite;

public class WaterTile extends DemoTile {

    public WaterTile() {
        super(null, true);
        Sprites.WATER.setFrameRate(15);
    }

    public static void update() {
        Sprites.WATER.update();
    }

    @Override
    public Sprite getSprite() {
        return Sprites.WATER.getSprite();
    }
}