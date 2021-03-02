package com.github.nighttripperid.littleengine.model.graphics;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class TileMapGFX {
    public static final Tile VOID_TILE = new Tile(new Sprite(0xffff00ff,
            Tile.TileSize.X16.getValue(), Tile.TileSize.X16.getValue()), true, false);
    private Map<Integer, Sprite> TILE_MAP_SPRITES = new HashMap<>();
    private Map<Integer, Tile> TILE_MAP = new HashMap<>();

    public void setTileMap(SpriteSheet spriteSheet,
                           List<TILED_TileMap.Tile> TILED_tiles,
                           int tileSize) {
        for(int y = 0, i = 0; y < spriteSheet.getSheetHeight() / tileSize; y++) {
            for(int x = 0; x < spriteSheet.getSheetWidth() / tileSize; x++, i++) {
                TILE_MAP_SPRITES.put(i, new Sprite(spriteSheet, tileSize, x, y));
            }
        }

        TILE_MAP_SPRITES.forEach((tiledKey, tileMapSprite) -> {
            TILED_tiles.forEach(tile -> {
                boolean solid = tile.getObjectgroup() != null;
                TILE_MAP.put(tiledKey, new Tile(TILE_MAP_SPRITES.get(tiledKey), solid, false));
            });
        });
    }
}
