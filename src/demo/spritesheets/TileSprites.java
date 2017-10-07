package demo.spritesheets;

import demo.tile.DemoTile;
import graphics.Sprite;

public class TileSprites {

    public static Sprite dirt = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 11, 0);
    public static Sprite mud = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 10, 0);

    private TileSprites(){
    }
}
