package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.PointInt;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tile;
import com.github.nighttripperid.littleengine.model.tiles.TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tileset;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GameMapBuilder {
    public static GameMap build(TILED_TileMap tiled_tileMap, SpriteSheet tilesetImage) {
        GameMap gameMap = new GameMap();
        gameMap.setTileSize(new PointDouble((double) tiled_tileMap.getTilewidth(),
                                            (double) tiled_tileMap.getTileheight()));
        gameMap.setTileset(buildTileset(tilesetImage, gameMap.getTileSize(), tiled_tileMap.getTilesets()));
        gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize().x) / Math.log(2)));
        gameMap.setTileMap(buildTileMap(tiled_tileMap, gameMap.getTileset()));
        gameMap.setTiled_TileMap(tiled_tileMap);
        return gameMap;
    }

    private static Tileset buildTileset(SpriteSheet tilesetImage, PointDouble tileSize,
                                        List<TILED_TileMap.Tileset> tiled_tilesets) {
        PointInt ts = new PointInt((int)(double)tileSize.x, (int)(double)tileSize.y);
        Map<Integer, Tile> tiles = new HashMap<>();
        for(int y = 0, i = 0; y < tilesetImage.sheetH_P / ts.y; y++) {
            for(int x = 0; x < tilesetImage.sheetW_P / ts.x; x++, i++) {
                Tile t = new Tile(new Sprite(tilesetImage, ts.x, ts.y, x, y), ts.x, ts.y);
                tiles.put(i, t);
            }
        }
        Tileset tileset = new Tileset(tiles, ts.x, ts.y);
        tiled_tilesets.forEach(tiled_tileset -> {
            if (tiled_tileset.getTiles() == null)
                return;
            tiled_tileset.getTiles().forEach(tile -> {
                if (tile.getObjectgroup() != null) {
                    tile.getObjectgroup().getObjects().forEach(object ->
                            tileset.getTileset().get(tile.getId()).getAttributes().add(object.getType()));
                }
            });
        });
        return tileset;
    }

    private static TileMap buildTileMap(TILED_TileMap tiled_tileMap, Tileset tileset) {
        TileMap tileMap = new TileMap();
        tileMap.setWidth_T(tiled_tileMap.getWidth());
        tileMap.setHeight_T(tiled_tileMap.getHeight());
        tiled_tileMap.getLayers().stream().filter(layer -> layer.getType().equals("tilelayer"))
                .collect(Collectors.toList()).forEach(layer -> tileMap.putLayer(layer.getId(), layer.getData())
        );
        return tileMap;
    }
}
