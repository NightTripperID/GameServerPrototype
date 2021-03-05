package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.graphics.Tile;
import com.github.nighttripperid.littleengine.model.graphics.Tileset;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class TileMap {

    @Setter
    private TILED_TileMap tiled_TileMap;

    private final Map<Integer, Integer[]> tileMap = new HashMap<>(); // hashed by layerId

        public Tile getTile(Tileset tileset, int layerId, int x, int y) {

        if (x < 0 ||
                y < 0 ||
                x >= tiled_TileMap.getWidth() ||
                y >= tiled_TileMap.getHeight()) {

            return Tileset.VOID_TILE;

        } else {
            return tileset.getTileset().get(
                    tileMap.get(layerId)[x + y * tiled_TileMap.getWidth()] - 1
            );
        }
    }

    public void putLayer(int layerId, Integer[] data) {
            tileMap.put(layerId, data);
    }

    public int getNumLayers() {
            return tileMap.size();
    }

    public boolean hasLayer(int layerId) {
            return tileMap.get(layerId) != null;
    }
}
