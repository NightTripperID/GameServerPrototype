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
package com.github.nighttripperid.littleengine.model.tiles;

import com.github.nighttripperid.littleengine.model.graphics.Sprite;
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
     * @param isTrigger Indicates whether or not the Tile has an interactive event Trigger.
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
