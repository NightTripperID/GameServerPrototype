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
package com.github.nighttripperid.littleengine.model.graphics;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TileMapGFX {
    private final Map<Integer, Sprite> tileMapSprites = new HashMap<>();
    private final Map<Integer, Tile> tileMap = new HashMap<>();

    public static final Tile VOID_TILE = new Tile(new Sprite(0xffff00ff,
            Tile.TileSize.X16.getValue(),
            Tile.TileSize.X16.getValue()),
            true,
            false);

    public void setTileMap(SpriteSheet spriteSheet,
                           List<TILED_TileMap.Tile> TILED_tiles,
                           int tileSize) {
        for(int y = 0, i = 0; y < spriteSheet.sheetH_P / tileSize; y++) {
            for(int x = 0; x < spriteSheet.sheetW_P / tileSize; x++, i++) {
                tileMapSprites.put(i, new Sprite(spriteSheet, tileSize, x, y));
            }
        }

        tileMapSprites.forEach((tiledKey, tileMapSprite) -> TILED_tiles.forEach(tile -> {
            boolean solid = tile.getObjectgroup() != null;
            tileMap.put(tiledKey, new Tile(tileMapSprites.get(tiledKey), solid, false));
        }));
    }
}
