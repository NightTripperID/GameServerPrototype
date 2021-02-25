package com.github.nighttripperid.littleengine.newstuff.util;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.nighttripperid.littleengine.newstuff.GameMap;
import com.github.nighttripperid.littleengine.newstuff.tilemapping.TileMap;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

@Slf4j
public class MapLoaderUtil {
    private MapLoaderUtil() {
    }

    public static void loadMapTilesFromJson(URL jsonUrl, GameMap gameMap) {
        log.info("Loading: {}{}", jsonUrl.getPath(), "...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jsonUrl.openStream()))) {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);
            Mapper mapper = new DozerBeanMapper();
            TileMap tileMap = mapper.map(jsonObject, TileMap.class);

            gameMap.setMapWidthInTiles(tileMap.getWidth());
            gameMap.setMapHeightInTiles(tileMap.getHeight());
            gameMap.setTileSize(tileMap.getTilewidth());
            gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize()) / Math.log(2)));

            gameMap.setTriggerTiles(new int[tileMap.getWidth() * tileMap.getHeight()]);
            tileMap.getLayers().forEach(layer -> {
                Integer[] mapTiles = new Integer[gameMap.getMapWidthInTiles() * gameMap.getMapHeightInTiles()];
                gameMap.getMapTileList().add(mapTiles);
                System.arraycopy(layer.getData(), 0, mapTiles, 0, mapTiles.length);
            });

            log.info("Loading {} successful!", jsonUrl.getPath());

        } catch (IOException | JsonException e) {
            log.info("Loading {} failed!", jsonUrl.getPath());
        }
    }
}
