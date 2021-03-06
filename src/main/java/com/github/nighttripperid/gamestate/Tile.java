package com.github.nighttripperid.gamestate;

import com.github.nighttripperid.engine.Screen;
import com.github.nighttripperid.graphics.Sprite;

/**
 * Square background object that is rendered to Screen.
 */
public class Tile {

    private Sprite sprite;
    private boolean solid;
    private boolean trigger;

    /**
     * Creates a Tile object from a given Sprite and given properties.
     * @param sprite The sprite associated with the Tile.
     * @param solid Indicates whether or not the Tile is traversable by Entities.
     * @param trigger Indicates whetehr or not the Tile has an interactive event Trigger.
     */
    public Tile(Sprite sprite, boolean solid, boolean trigger) {
        this.sprite = sprite;
        this.solid = solid;
        this.trigger = trigger;
    }

    /**
     * Renders the Tile onto screen.
     * @param screen The screen on which to Renderable.
     * @param x The x map coordinate on which to Renderable (in pixel precision).
     * @param y The y map coordinate on which to Renderable (in pixel precision).
     */
    public void render(Screen screen, int x, int y) {
        screen.renderTile(x, y, this);
    }

    /**
     * Returns the Sprite referenced by this Tile object.
     * @return A Sprite object.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Returns whether this Tile is a solid object (i.e. cannot be traversed by game objects)
     * @return true if solid, false otherwise
     */
    public boolean solid() {
        return solid;
    }

    /**
     * Returns whether this Tile contains an interactive Trigger (an event that occurs when the Tile is interacted with)
     * @return true if contains a trigger, false otherwise
     */
    public boolean trigger() {
        return trigger;
    }

    /**
     * The size of the tile in square dimensions. Limited to common and reasonable dimensions.
     */
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
