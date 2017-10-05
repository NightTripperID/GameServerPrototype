package demo.tile;

import entity.Renderable;
import entity.Updatable;
import graphics.Sprite;

public abstract class Tile implements Updatable, Renderable {

    public int x, y;

    public static final int WIDTH = 16;
    public static final int HEIGHT = 16;

    private Sprite sprite;

    private boolean solid;

    public Tile(Sprite sprite, boolean solid) {
        this.sprite = sprite;
        this.solid = solid;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isSolid() {
        return solid;
    }
}
