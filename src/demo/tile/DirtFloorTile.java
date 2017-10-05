package demo.tile;

import graphics.Screen;
import graphics.Sprite;

public class DirtFloorTile extends Tile {

    public DirtFloorTile(Sprite sprite) {
        super(sprite, false);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x, y, Tile.WIDTH, Tile.HEIGHT, getSprite().pixels);

    }
}
