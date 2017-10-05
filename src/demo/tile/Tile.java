package demo.tile;

import graphics.Screen;
import graphics.Sprite;

public class Tile {

    public int x, y;

    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    private Sprite sprite;

    private boolean solid;

    public Tile(Sprite sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    public void render(Screen screen, int x, int y) {
        screen.renderTile(x, y, this);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return solid;
    }
}
