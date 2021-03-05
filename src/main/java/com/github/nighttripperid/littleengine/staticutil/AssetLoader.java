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
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.EntityData;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.tiles.TileMap;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tileset;
import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

// TODO: formulate a way to decouple entity loading from tilemap loading for sake of clarity
@Slf4j
public class AssetLoader {
    private AssetLoader() {
    }

    public static void loadAssets(String pngPath, String jsonPath, GameMap gameMap, EntityData entityData) {
        loadTileMap(jsonPath, gameMap, entityData);
        loadTileset(pngPath, gameMap);
    }

    private static void loadTileMap(String jsonPath, GameMap gameMap, EntityData entityData) {
        URL url = AssetLoader.class.getClassLoader().getResource(jsonPath);
        assert url != null;
        log.info("Loading: {}{}", url.getPath(), "...");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);
            Mapper mapper = new DozerBeanMapper();
            TILED_TileMap tiled_TileMap = mapper.map(jsonObject, TILED_TileMap.class);

            gameMap.setTileSize(tiled_TileMap.getTilewidth());
            gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize()) / Math.log(2)));
            gameMap.setTileMap(new TileMap());
            gameMap.getTileMap().setTiled_TileMap(tiled_TileMap);

            tiled_TileMap.getLayers().forEach(layer -> {
                if (layer.getType().equals("tilelayer"))
                    gameMap.getTileMap().putLayer(layer.getId(), layer.getData());
                if (layer.getType().equals("objectgroup")) {
                    layer.getObjects().forEach(object -> {
                        if (object.getType().equals("entity")) {
                            Map<String, String> properties = new HashMap<>();
                            object.getProperties().forEach(property -> {
                                properties.put(property.getName(), property.getValue());
                            });
                            try {
                                Class<?> clazz = Class.forName(properties.get("class"));
                                Entity entity = (Entity) clazz.newInstance();
                                entity.setPosition(new PointDouble(object.getX(), object.getY()));
                                entity.setSpriteKey(properties.get("filename"));
                                entityData.getEntities().add(entity);
                            } catch (ClassNotFoundException e) {
                                log.error("Error loading Class with name {}", properties.get("class"));
                            } catch (IllegalAccessException e) {
                                log.error("Error accessing Class to instantiate with name {}", properties.get("class"));
                            } catch (InstantiationException e) {
                                log.error("Error instantiating Class with name {}", properties.get("class") );
                            }
                        }
                    });
                }
            });

            log.info("Loading {} successful!", url.getPath());

        } catch (IOException | JsonException e) {
            log.error("Loading {} failed!", url.getPath());
        }
    }

    private static void loadTileset(String pngPath, GameMap gameMap) {
        URL url = AssetLoader.class.getClassLoader().getResource(pngPath);
        try {
            assert url != null;
            log.info("Loading: {}{}", url.getPath(), "...");
            BufferedImage image = ImageIO.read(url);
            log.info("Loading {} successful!", url.getPath());
            SpriteSheet spriteSheet = new SpriteSheet(image, gameMap.getTileSize(), gameMap.getTileSize());
            gameMap.setTileset(new Tileset());
            gameMap.getTileset().setTileset(spriteSheet, gameMap.getTileSize());
        } catch (IOException e) {
            log.error("Loading {} failed!", url.getPath());
        }
    }
}
