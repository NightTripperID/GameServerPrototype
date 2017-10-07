package demo.tile;

import com.sun.istack.internal.NotNull;
import graphics.Sprite;

public class DemoTile extends Tile {

    public static final int SIZE = 16;

    public DemoTile(@NotNull Sprite sprite, boolean solid) {
        super(sprite, solid);
    }
}
