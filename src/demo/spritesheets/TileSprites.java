package demo.spritesheets;

import demo.tile.DemoTile;
import graphics.AnimSprite;
import graphics.Sprite;
import graphics.SpriteSheet;

public class TileSprites {

    public static final Sprite CACTUS = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 1);
    public static final Sprite CROSS = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 4);
    public static final Sprite DIRT = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 8, 2);
    public static final Sprite DUNGEON_DOOR_LOCKED = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 4);
    public static final Sprite DUNGEON_DOOR_OPEN = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 5);
    public static final Sprite DUNGEON_GATE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 7, 2);
    public static final Sprite EARTH_TOP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 8, 0);
    public static final Sprite EARTH_SIDE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 8, 1);
    public static final Sprite FLOOR_SWITCH_UP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 6);
    public static final Sprite FLOOR_SWITCH_DOWN = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 6);
    public static final Sprite GRAVE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 2);
    public static final Sprite LAVA_GRATE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 8, 3);
    public static final Sprite OBELISK_TOP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 7, 0);
    public static final Sprite OBELISK_BOTTOM = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 7, 1);
    public static final Sprite PILLAR_TOP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 0);
    public static final Sprite PILLAR_SIDE = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 1);
    public static final Sprite SAND = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 0);
    public static final Sprite SKELLY = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 11, 3);
    public static final Sprite STONE_DOORWAY = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 3);
    public static final Sprite STONE_GRAVEL_FLOOR = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 2);
    public static final Sprite STONE_STAIRS_DOWN = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 4);
    public static final Sprite STONE_STAIRS_UP = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 10, 5);
    public static final Sprite STONE_TILE_FLOOR = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 9, 3);
    public static final Sprite WIDE_DOOR_CENTER = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 7, 4);
    public static final Sprite WIDE_DOOR_LEFT = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 7, 3);
    public static final Sprite WIDE_DOOR_RIGHT = new Sprite(SpriteSheets.MAIN_SHEET, DemoTile.SIZE, 7, 5);

    public static final AnimSprite LAVA = new AnimSprite(SpriteSheets.LAVA, 16, 16,2);
    public static final AnimSprite WATER = new AnimSprite(SpriteSheets.WATER, 16, 16, 2);

    private TileSprites(){
    }
}
