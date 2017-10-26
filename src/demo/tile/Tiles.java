package demo.tile;

import demo.spritesheets.Sprites;
import graphics.Sprite;

public class Tiles {

    public static Tile voidTile = new DemoTile(new Sprite(0x0000ff, DemoTile.SIZE, DemoTile.SIZE), true);

    public static Tile cactus = new DemoTile(Sprites.CACTUS, true);
    public static Tile cross = new DemoTile(Sprites.CROSS, true);
    public static Tile dirt = new DemoTile(Sprites.DIRT, false);
    public static Tile dirtThreshold = new DemoTile(Sprites.DIRT, false, true);
    public static Tile dungeonDoorLocked = new DemoTile(Sprites.DUNGEON_DOOR_LOCKED, true, true);
    public static Tile dungeonDoorOpen = new DemoTile(Sprites.DUNGEON_DOOR_OPEN, true, true);
    public static Tile dungeonGate = new DemoTile(Sprites.DUNGEON_GATE, true, true);
    public static Tile earthSide = new DemoTile(Sprites.EARTH_SIDE, true);
    public static Tile earthTop = new DemoTile(Sprites.EARTH_TOP, true);
    public static Tile floorSwitchUp = new DemoTile(Sprites.FLOOR_SWITCH_UP, false, true);
    public static Tile floorSwitchDown = new DemoTile(Sprites.FLOOR_SWITCH_DOWN, false);
    public static Tile grave = new DemoTile(Sprites.GRAVE, true, true);
    public static Tile lava = new LavaTile();
    public static Tile lavaGrate = new DemoTile(Sprites.LAVA_GRATE, false);
    public static Tile obeliskBottom = new DemoTile(Sprites.OBELISK_BOTTOM, true);
    public static Tile obeliskTop = new DemoTile(Sprites.OBELISK_TOP, true);
    public static Tile pillarTop = new DemoTile(Sprites.PILLAR_TOP, true);
    public static Tile pillarSide = new DemoTile(Sprites.PILLAR_SIDE, true);
    public static Tile purpleLava = new PurpleLavaTile();
    public static Tile sand = new DemoTile(Sprites.SAND, false);
    public static Tile scorchedSand = new DemoTile(Sprites.SCORCHED_SAND, false);
    public static Tile sign = new DemoTile(Sprites.SIGN, true, true);
    public static Tile skelly = new DemoTile(Sprites.SKELLY, true);
    public static Tile stoneDoorway = new DemoTile(Sprites.STONE_DOORWAY, true, true);
    public static Tile stoneGravelFloor = new DemoTile(Sprites.STONE_GRAVEL_FLOOR, false);
    public static Tile stoneSideR = new DemoTile(Sprites.STONE_SIDE_R, true);
    public static Tile stoneSideL = new DemoTile(Sprites.STONE_SIDE_L, true);
    public static Tile stoneStairsDown = new DemoTile(Sprites.STONE_STAIRS_DOWN, false, true);
    public static Tile stoneStairsUp = new DemoTile(Sprites.STONE_STAIRS_UP, false, true);
    public static Tile stoneThreshold = new DemoTile(Sprites.STONE_GRAVEL_FLOOR, false, true);
    public static Tile stoneTileFloor = new DemoTile(Sprites.STONE_TILE_FLOOR, false);
    public static Tile stoneTopR = new DemoTile(Sprites.STONE_TOP_R, true);
    public static Tile stoneTopL = new DemoTile(Sprites.STONE_TOP_L, true);
    public static Tile water = new WaterTile();
    public static Tile wideDoorCenter = new DemoTile(Sprites.WIDE_DOOR_CENTER, true, true);
    public static Tile wideDoorLeft = new DemoTile(Sprites.WIDE_DOOR_LEFT, true, true);
    public static Tile wideDoorRight = new DemoTile(Sprites.WIDE_DOOR_RIGHT, true, true);

    private Tiles() {
    }
}
