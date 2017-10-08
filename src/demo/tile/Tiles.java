package demo.tile;

import demo.spritesheets.TileSprites;
import graphics.Sprite;

public class Tiles {

    public static Tile voidTile = new DemoTile(new Sprite(0x0000ff, DemoTile.SIZE, DemoTile.SIZE), true);
    public static Tile dirtTile = new DemoTile(TileSprites.dirt, false);
    public static Tile cactusTile = new DemoTile(TileSprites.cactus, true);
    public static Tile graveTile = new DemoTile(TileSprites.grave, true);
    public static Tile skellytile = new DemoTile(TileSprites.skelly, true);
    public static Tile crossTile = new DemoTile(TileSprites.cross, true);
    public static Tile pillarTopTile = new DemoTile(TileSprites.pillarTop, true);
    public static Tile pillarSideTile = new DemoTile(TileSprites.pillarSide, true);
    public static Tile stoneFloorTile = new DemoTile(TileSprites.stoneFloor, false);
    public static Tile stoneThresholdTile = new DemoTile(TileSprites.stoneFloor, false, true);
    public static Tile stoneDoorwayTile = new DemoTile(TileSprites.stoneDoorway, true, true);
    public static Tile stoneStairsDownTile = new DemoTile(TileSprites.stoneStairsDown, false, true);

    private Tiles() {
    }
}
