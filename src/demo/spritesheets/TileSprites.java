package demo.spritesheets;

import demo.tile.DemoTile;
import graphics.Sprite;

public class TileSprites {

    public static Sprite dirt = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 11, 0);
    public static Sprite cactus = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 11, 1);
    public static Sprite grave = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 11, 2);
    public static Sprite skelly = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 11, 3);
    public static Sprite cross = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 11, 4);
    public static Sprite pillarTop = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 10, 0);
    public static Sprite pillarSide = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 10, 1);
    public static Sprite stoneFloor = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 10, 2);
    public static Sprite stoneDoorway = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, DemoTile.SIZE, 10, 3);

    private TileSprites(){
    }
}
