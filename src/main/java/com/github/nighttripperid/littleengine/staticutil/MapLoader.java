/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.nighttripperid.littleengine.staticutil;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;
import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.graphics.Tilemap;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

        URL url = MapLoader.class.getClassLoader().getResource(jsonPath);
        log.info("Loading: {}{}", url.getPath(), "...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);
            Mapper mapper = new DozerBeanMapper();
            TILED_TileMap tiled_TileMap = mapper.map(jsonObject, TILED_TileMap.class);

            gameMap.setTiled_TileMap(tiled_TileMap);
            gameMap.setTileSize(tiled_TileMap.getTilewidth());
            gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize()) / Math.log(2)));

            tiled_TileMap.getLayers().forEach(layer -> {
                if (layer.getType().equals("tilelayer"))
                gameMap.getTileMapLookups().put(layer.getId(), layer.getData());
            });

            log.info("Loading {} successful!", url.getPath());

        } catch (IOException | JsonException e) {
            log.error("Loading {} failed!", url.getPath());
        }

        loadTileMapGFX(pngPath, gameMap);
    }

    private static void loadTileMapGFX(String pngPath, GameMap gameMap) {

        URL url = MapLoader.class.getClassLoader().getResource(pngPath);
        try {
            log.info("Loading: {}{}", url.getPath(), "...");
            BufferedImage image = ImageIO.read(url);
            log.info("Loading {} successful!", url.getPath());
            SpriteSheet spriteSheet = new SpriteSheet(image, gameMap.getTileSize(), gameMap.getTileSize());
            List<TILED_TileMap.Tile> TILED_tiles = gameMap.getTiled_TileMap().getTilesets().stream()
                    .findFirst().orElse(null).getTiles();
            gameMap.setTilemap(new Tilemap());
            gameMap.getTilemap().setTileset(spriteSheet, TILED_tiles, gameMap.getTileSize());
        } catch (IOException e) {
            log.error("Loading {} failed!", url.getPath());
        }
    }
}
