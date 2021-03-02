package com.github.nighttripperid.littleengine.staticutil;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;
import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.graphics.TileMapGFX;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

@Slf4j
public class MapLoader {
    private MapLoader() {
    }

    public static void loadTileMap(String pngPath, String jsonPath, GameMap gameMap) {

        URL jsonUrl = MapLoader.class.getClassLoader().getResource(jsonPath);
        log.info("Loading: {}{}", jsonUrl.getPath(), "...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jsonUrl.openStream()))) {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);
            Mapper mapper = new DozerBeanMapper();
            TILED_TileMap tiled_TileMap = mapper.map(jsonObject, TILED_TileMap.class);

            gameMap.setTiled_TileMap(tiled_TileMap);
            gameMap.setTileSize(tiled_TileMap.getTilewidth());
            gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize()) / Math.log(2)));

            tiled_TileMap.getLayers().forEach(layer ->
                gameMap.getTileMapLookups().put(layer.getName(), layer.getData())
            );

            log.info("Loading {} successful!", jsonUrl.getPath());

        } catch (IOException | JsonException e) {
            log.error("Loading {} failed!", jsonUrl.getPath());
        }

        loadTileMapGFX(pngPath, gameMap);
    }

    private static void loadTileMapGFX(String pngPath, GameMap gameMap) {
        SpriteSheet spriteSheet = new SpriteSheet(pngPath, gameMap.getTileSize(), gameMap.getTileSize());
        List<TILED_TileMap.Tile> TILED_tiles = gameMap.getTiled_TileMap().getTilesets().stream()
                .findFirst().orElse(null).getTiles();
        gameMap.setTileMapGFX(new TileMapGFX());
        gameMap.getTileMapGFX().setTileMap(spriteSheet, TILED_tiles, gameMap.getTileSize());
    }
}
