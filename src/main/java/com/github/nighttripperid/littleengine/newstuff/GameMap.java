package com.github.nighttripperid.littleengine.newstuff;

import com.github.nighttripperid.littleengine.gamestate.Trigger;
import com.github.nighttripperid.littleengine.newstuff.tilemapping.Tile;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class GameMap {

    private double xScroll;
    private double yScroll;

    private int mapWidthInTiles;
    private int mapHeightInTiles;

    protected int tileSize;
    protected int tileBitShift;

    private List<Integer[]> mapTileList = new ArrayList<>();

    private int[] triggerTiles;

    private Map<Integer, Trigger> triggers = new HashMap<>();


    @Getter(AccessLevel.NONE)
    private TriFunction<Integer[], Integer, Integer, Tile> mapTileObjectGetter;

    public  Tile getMapTileObject(Integer[] mapTiles, int x, int y) {
        return mapTileObjectGetter.apply(mapTiles, x, y);
    }

    @FunctionalInterface
    public static interface TriFunction<T1, T2, T3, R> {
        R apply(T1 t1, T2 t2, T3 t3);
    }
}
