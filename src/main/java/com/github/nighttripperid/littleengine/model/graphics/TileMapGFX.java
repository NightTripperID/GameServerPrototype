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
        for(int y = 0, i = 0; y < spriteSheet.getSheetHeight() / tileSize; y++) {
            for(int x = 0; x < spriteSheet.getSheetWidth() / tileSize; x++, i++) {
                tileMapSprites.put(i, new Sprite(spriteSheet, tileSize, x, y));
            }
        }

        tileMapSprites.forEach((tiledKey, tileMapSprite) -> TILED_tiles.forEach(tile -> {
            boolean solid = tile.getObjectgroup() != null;
            tileMap.put(tiledKey, new Tile(tileMapSprites.get(tiledKey), solid, false));
        }));
    }
}
