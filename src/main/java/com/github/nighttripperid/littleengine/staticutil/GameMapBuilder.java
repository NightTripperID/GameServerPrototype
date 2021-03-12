package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.tiles.TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tileset;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class GameMapBuilder {
    public static GameMap build(TILED_TileMap tiled_tileMap, Tileset tileset) {
        GameMap gameMap = new GameMap();
        gameMap.setTileset(tileset);
        gameMap.setTileSize(new PointDouble((double) tiled_tileMap.getTilewidth(),
                                            (double) tiled_tileMap.getTileheight()));
        gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize().x) / Math.log(2)));
        gameMap.setTileMap(buildTileMap(tiled_tileMap));
        gameMap.setTiled_TileMap(tiled_tileMap);
        return gameMap;
    }

    private static TileMap buildTileMap(TILED_TileMap tiled_tileMap) {
        TileMap tileMap = new TileMap();
        tileMap.setWidth_T(tiled_tileMap.getWidth());
        tileMap.setHeight_T(tiled_tileMap.getHeight());
        tiled_tileMap.getLayers().stream().filter(layer -> layer.getType().equals("tilelayer"))
                .collect(Collectors.toList()).forEach(layer -> tileMap.putLayer(layer.getId(), layer.getData())
        );
        tiled_tileMap.getTilesets().forEach(tileset ->
                tileset.getTiles().forEach(tile -> {
                    if (tile.getObjectgroup() != null) {
                        tile.getObjectgroup().getObjects().forEach(object ->
                                tileMap.putTileObject(tile.getId(), object)
                        );
                    }
                })
        );
        return tileMap;
    }
}
