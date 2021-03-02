package com.github.nighttripperid.littleengine.model.graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileMap {
    private static final Map<Integer, Sprite> TILE_MAP_SPRITES = new HashMap<>();
    public static final Map<Integer, Tile> TILE_MAP = new HashMap<>();
    public static final Tile VOID_TILE = new Tile(new Sprite(0xffff00ff,
            Tile.TileSize.X16.getValue(), Tile.TileSize.X16.getValue()), true, false);

    private TileMap() {
    }

    public static void putTileMapSprites(SpriteSheet spriteSheet, int tileSize, int widthInTiles, int heightInTiles) {
        for(int y = 0, i = 0; y < heightInTiles; y++) {
            for(int x = 0; x < widthInTiles; x++, i++) {
                TILE_MAP_SPRITES.put(i, new Sprite(spriteSheet, tileSize, x, y));
            }
        }
    }

    public static void populateTileMap(List<TILED_TileMap.Tile> TILED_tiles) {
        TILE_MAP_SPRITES.forEach((key, tileMapSprite) -> {
            TILED_tiles.forEach(tile -> {
                boolean solid = tile.getObjectgroup() == null ? false : true;
                TILE_MAP.put(key, new Tile(TILE_MAP_SPRITES.get(key), solid, false));
            });
        });
    }
}