package demo.spritesheets;

import demo.tile.DemoTile;
import graphics.Sprite;

public class TileSprites {

    public static final Sprite DIRT = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 0);
    public static final Sprite CACTUS = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 1);
    public static final Sprite GRAVE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 2);
    public static final Sprite SKELLY = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 3);
    public static final Sprite CROSS = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 4);
    public static final Sprite PILLAR_TOP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 0);
    public static final Sprite PILLAR_SIDE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 1);
    public static final Sprite STONE_GRAVEL_FLOOR = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 2);
    public static final Sprite STONE_TILE_FLOOR = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 3);
    public static final Sprite STONE_DOORWAY = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 3);
    public static final Sprite STONE_STAIRS_DOWN = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 4);
    public static final Sprite STONE_STAIRS_UP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 5);
    public static final Sprite DUNGEON_DOOR_LOCKED = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 4);
    public static final Sprite DUNGEON_DOOR_OPEN = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 5);

    private TileSprites(){
    }
}
