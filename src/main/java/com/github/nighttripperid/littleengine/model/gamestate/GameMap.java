package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.graphics.Tile;
import com.github.nighttripperid.littleengine.model.graphics.TileMap;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameMap {

    private PointDouble scroll = new PointDouble();

    private int tileSize;
    private int tileBitShift;
    private int numLayers = 3;

    private final Map<String, Integer[]> mapTileHashMap = new HashMap<>();

    private TILED_TileMap tiled_TileMap;

    public  Tile getMapTileObject(Integer[] mapTiles, int x, int y) {

        if (x < 0 ||
                y < 0 ||
                x >= this.getTiled_TileMap().getWidth() ||
                y >= this.getTiled_TileMap().getHeight()) {

            return TileMap.VOID_TILE;

        } else {
            return TileMap.TILE_MAP.get(mapTiles[x + y * this.getTiled_TileMap().getWidth()] - 1);
        }
    }
}
