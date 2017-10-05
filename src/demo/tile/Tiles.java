package demo.tile;

import demo.spritesheets.TileSprites;
import graphics.Sprite;

public class Tiles {

    public static Tile dirtTile = new Tile(TileSprites.dirt, false);
    public static Tile mudTile = new Tile(TileSprites.mud, false);
    public static Tile voidTile = new Tile(new Sprite(0x0000ff, 16, 16), true);

    private Tiles() {
    }
}
