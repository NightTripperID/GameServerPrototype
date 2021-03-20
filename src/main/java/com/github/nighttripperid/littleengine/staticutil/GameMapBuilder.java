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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;
import com.github.nighttripperid.littleengine.model.physics.VectorF2D;
import com.github.nighttripperid.littleengine.model.physics.VectorI2D;
import com.github.nighttripperid.littleengine.model.scene.GameMap;
import com.github.nighttripperid.littleengine.model.tiles.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class GameMapBuilder {
    public static GameMap build(TILED_TileMap tiled_tileMap, SpriteSheet tilesetImage) {
        GameMap gameMap = new GameMap();
        gameMap.setTileSize(new VectorF2D((float) tiled_tileMap.getTilewidth(),
                                            (float) tiled_tileMap.getTileheight()));
        gameMap.setTileBitShift((int) (Math.log(gameMap.getTileSize().x) / Math.log(2)));
        gameMap.setTileset(buildTileset(tilesetImage, gameMap.getTileSize(), tiled_tileMap.getTilesets()));
        gameMap.setTileMap(buildTileMap(tiled_tileMap));
        gameMap.setTiled_TileMap(tiled_tileMap);
        return gameMap;
    }

    private static Tileset buildTileset(SpriteSheet tilesetImage, VectorF2D tileSize,
                                        List<TILED_TileMap.Tileset> tiled_tilesets) {
        VectorI2D ts = new VectorI2D((int)(float)tileSize.x, (int)(float)tileSize.y);
        Map<Integer, Tile> tiles = new HashMap<>();
        for(int y = 0, i = 0; y < tilesetImage.sheetH_P / ts.y; y++) {
            for(int x = 0; x < tilesetImage.sheetW_P / ts.x; x++, i++) {
                Tile t = new BasicTile(i, new Sprite(tilesetImage, ts.x, ts.y, x, y), ts.x, ts.y);
                tiles.put(i, t);
            }
        }

        tiled_tilesets.forEach(tiled_tileset -> {
            if (tiled_tileset.getTiles() == null)
                return;
            tiled_tileset.getTiles().forEach(tiled_t -> {
                if (tiled_t.getObjectgroup() != null) {
                    tiled_t.getObjectgroup().getObjects().forEach(object -> {
                        if (object.getProperties() != null) {
                            Map<String, String> properties = new HashMap<>();
                            object.getProperties().forEach(property ->
                                    properties.put(property.getName(), property.getValue()));
                            try {
                                List<?> attributes =
                                        ObjectMapperW.getObjectMapper()
                                                .readValue(properties.get("attributes"), List.class);
                                attributes.forEach(attribute ->
                                    tiles.get(tiled_t.getId()).getAttributes().add((String) attribute)
                                );
                                properties.remove("attributes");
                            } catch (JsonProcessingException e) {
                                log.error("Error loading tileset attributes: {}", e.getMessage());
                            }
                            if (tiles.get(tiled_t.getId()).getAttributes().contains("dynamic")) {
                                DynamicTile dt = new DynamicTile(tiles.get(tiled_t.getId()));
                                Class<?> clazz = dt.getClass();
                                properties.keySet().forEach(fieldName -> {
                                    try {
                                        Field f = clazz.getDeclaredField(fieldName);
                                        f.setAccessible(true);
                                        f.set(dt, properties.get(fieldName));
                                    } catch (NoSuchFieldException e) {
                                        log.error("Could not find field \"{}\" in class \"{}\".",
                                                fieldName, clazz.getName());
                                    } catch (IllegalAccessException e) {
                                        log.error("Error accessing Class to instantiate with name \"{}\". " +
                                                "make sure class is public.", clazz.getName());
                                    }
                                });
                                dt.onCreate();
                                tiles.put(tiled_t.getId(), dt);
                            }
                        }
                    });
                }
            });
        });

        return new Tileset(tiles, ts.x, ts.y);
    }

    private static TileMap buildTileMap(TILED_TileMap tiled_tileMap) {
        TileMap tileMap = new TileMap();
        tileMap.setWidth_T(tiled_tileMap.getWidth());
        tileMap.setHeight_T(tiled_tileMap.getHeight());
        tiled_tileMap.getLayers().stream().filter(layer -> layer.getType().equals("tilelayer"))
                .collect(Collectors.toList()).forEach(layer -> {
                    for (int i = 0; i < layer.getData().length; i++) {
                        layer.getData()[i] = Math.max(0, layer.getData()[i] - 1);
                    }
                    tileMap.putLayer(layer.getId(), layer.getData());
                }
        );
        return tileMap;
    }
}
