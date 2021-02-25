package com.github.nighttripperid.littleengine.newstuff.tilemapping;

import com.github.nighttripperid.littleengine.graphics.sprite.Sprite;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
public class Tile {

    private Sprite sprite;
    private boolean isSolid;
    private boolean isTrigger;
    private TileSize tileSize;

    /**
     * Creates a Tile object from a given Sprite and given properties.
     * @param sprite The sprite associated with the Tile.
     * @param isSolid Indicates whether or not the Tile is traversable by Entities.
     * @param isTrigger Indicates whetehr or not the Tile has an interactive event Trigger.
     */
    public Tile(Sprite sprite, boolean isSolid, boolean isTrigger) {
        this.sprite = sprite;
        this.isSolid = isSolid;
        this.isTrigger = isTrigger;
    }

    /**
     * The size of the tile in square dimensions. Limited to common and reasonable dimensions.
     */
    @Getter
    @AllArgsConstructor
    public enum TileSize {
        X8(8), X16(16), X32(32), X64(64), X128(128);

        private final int value;

    }
}
