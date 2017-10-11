package demo.tile;

import demo.spritesheets.TileSprites;
import graphics.Sprite;

public class Tiles {

    public static Tile voidTile = new DemoTile(new Sprite(0x0000ff, DemoTile.SIZE, DemoTile.SIZE), true);
    public static Tile dirt = new DemoTile(TileSprites.DIRT, false);
    public static Tile cactus = new DemoTile(TileSprites.CACTUS, true);
    public static Tile grave = new DemoTile(TileSprites.GRAVE, true);
    public static Tile skelly = new DemoTile(TileSprites.SKELLY, true);
    public static Tile cross = new DemoTile(TileSprites.CROSS, true);
    public static Tile pillarTop = new DemoTile(TileSprites.PILLAR_TOP, true);
    public static Tile pillarSide = new DemoTile(TileSprites.PILLAR_SIDE, true);
    public static Tile stoneGravelFloor = new DemoTile(TileSprites.STONE_GRAVEL_FLOOR, false);
    public static Tile stoneTileFloor = new DemoTile(TileSprites.STONE_TILE_FLOOR, false);
    public static Tile stoneThreshold = new DemoTile(TileSprites.STONE_GRAVEL_FLOOR, false, true);
    public static Tile stoneDoorway = new DemoTile(TileSprites.STONE_DOORWAY, true, true);
    public static Tile stoneStairsDown = new DemoTile(TileSprites.STONE_STAIRS_DOWN, false, true);
    public static Tile stoneStairsUp = new DemoTile(TileSprites.STONE_STAIRS_UP, false, true);
    public static Tile dungeonDoorLocked = new DemoTile(TileSprites.DUNGEON_DOOR_LOCKED, true, true);
    public static Tile dungeonDoorOpen = new DemoTile(TileSprites.DUNGEON_DOOR_OPEN, true, true);

    private Tiles() {
    }
}
