package com.github.nighttripperid.littleengine.model.gamestate;

import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.graphics.Tile;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Data
public class GameMap {

    private PointDouble scroll = new PointDouble();

    protected int tileSize;
    protected int tileBitShift;

    private final Map<String, Integer[]> mapTileHashMap = new HashMap<>();

    private TILED_TileMap tiled_TileMap;

    @Getter(AccessLevel.NONE)
    private TriFunction<Integer[], Integer, Integer, Tile> mapTileObjectGetter;

    @FunctionalInterface
    public static interface TriFunction<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);
    }

    public  Tile getMapTileObject(Integer[] mapTiles, int x, int y) {
        return mapTileObjectGetter.apply(mapTiles, x, y);
    }
}
