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
}
