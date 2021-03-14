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
import com.github.nighttripperid.littleengine.model.graphics.SpriteMaps;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tileset {
    public final Tile VOID_TILE;

    private final Map<Integer, Tile> tileset; // hashed by tile id
    private final List<DynamicTile> dynamicTiles = new ArrayList<>();

    @Getter
    private final SpriteMaps spriteMaps = new SpriteMaps();

    public Tileset(Map<Integer, Tile> tileset, int tileW, int tileH) {
        VOID_TILE = new BasicTile(0, new Sprite(0xffff00ff, tileW, tileH), tileW, tileH);
        VOID_TILE.getAttributes().add("solid");
        this.tileset = tileset;
        this.tileset.values().forEach(tile -> {
            if ((tile instanceof DynamicTile)) {
                dynamicTiles.add((DynamicTile) tile);
            }
        });
    }

    public List<DynamicTile> getDynamicTiles() {
        return new ArrayList<>(dynamicTiles);
    }

    public Tile getTile(int tileId) {
        return tileset.get(tileId);
    }
}
