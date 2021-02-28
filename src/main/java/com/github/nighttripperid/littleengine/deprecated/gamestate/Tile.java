/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.nighttripperid.littleengine.deprecated.gamestate;

import com.github.nighttripperid.littleengine.deprecated.engine.Screen;
import com.github.nighttripperid.littleengine.deprecated.graphics.sprite.Sprite;

/**
 * Square background object that is rendered to Screen.
 */
@Deprecated
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
