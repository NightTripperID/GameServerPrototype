package demo.tile;

import com.sun.istack.internal.NotNull;
import graphics.Screen;
import graphics.Sprite;

public class Tile {

    public int x, y;

    private Sprite sprite;

    private boolean solid;

    public Tile(@NotNull Sprite sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    public void render(@NotNull Screen screen, int x, int y) {
        screen.renderTile(x, y, this);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return solid;
    }

    public enum TileSize {
        X8(8), X16(16), X32(32), X64(64), X128(128);

        private final int size;

        TileSize(int size) {
            this.size = size;
        }

        public int get() {
            return size;
        }
    }
}
