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
package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.graphics.Tile;
import com.github.nighttripperid.littleengine.model.graphics.Tileset;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameMap {

    private PointDouble scroll = new PointDouble();

    private int tileSize;
    private int tileBitShift;

    private final Map<Integer, Integer[]> tileMap = new HashMap<>(); // hashed by id

    private Tileset tileset;
    private TILED_TileMap tiled_TileMap;

    public  Tile getMapTileObject(int layerId, int x, int y) {

        if (x < 0 ||
                y < 0 ||
                x >= this.getTiled_TileMap().getWidth() ||
                y >= this.getTiled_TileMap().getHeight()) {

            return Tileset.VOID_TILE;

        } else {
            return this.getTileset().getTileset().get(
                    tileMap.get(layerId)[x + y * this.getTiled_TileMap().getWidth()] - 1
            );
        }
    }
}
