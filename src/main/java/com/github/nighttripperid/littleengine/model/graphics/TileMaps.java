package com.github.nighttripperid.littleengine.model.graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileMaps {
    private static final Map<String, Map<Integer, Sprite>> TILE_MAP_SPRITES = new HashMap<>();
    public static final Map<String, Map<Integer, Tile>> TILE_MAPS = new HashMap<>(); // hashed by GameMap.mapTileName
    public static final Tile VOID_TILE = new Tile(new Sprite(0xffff00ff,
            Tile.TileSize.X16.getValue(), Tile.TileSize.X16.getValue()), true, false);

    private TileMaps() {
    }

    public static void addTileMap(String key, SpriteSheet spriteSheet, int tileSize,
                                  List<TILED_TileMap.Tile> TILED_tiles) {

        TILE_MAP_SPRITES.put(key, new HashMap<>());
        for(int y = 0, i = 0; y < spriteSheet.getSheetHeight() / tileSize; y++) {
            for(int x = 0; x < spriteSheet.getSheetWidth() / tileSize; x++, i++) {
                TILE_MAP_SPRITES.get(key).put(i, new Sprite(spriteSheet, tileSize, x, y));
            }
        }

        TILE_MAPS.put(key, new HashMap<>());
        TILE_MAP_SPRITES.get(key).forEach((tiledKey, tileMapSprite) -> {
            TILED_tiles.forEach(tile -> {
                boolean solid = tile.getObjectgroup() != null;
                TILE_MAPS.get(key).put(tiledKey, new Tile(TILE_MAP_SPRITES.get(key).get(tiledKey), solid, false));
            });
        });
    }
}