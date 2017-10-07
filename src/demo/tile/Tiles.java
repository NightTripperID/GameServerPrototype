package demo.tile;

import demo.spritesheets.TileSprites;
import graphics.Sprite;

public class Tiles {

    public static Tile dirtTile = new DemoTile(TileSprites.dirt, false);
    public static Tile mudTile = new DemoTile(TileSprites.mud, true);
    public static Tile voidTile = new DemoTile(new Sprite(0x0000ff, DemoTile.SIZE, DemoTile.SIZE), true);

    private Tiles() {
    }
}
