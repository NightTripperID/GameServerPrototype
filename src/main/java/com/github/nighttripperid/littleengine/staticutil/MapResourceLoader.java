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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tileset;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;


@Slf4j
public class MapResourceLoader {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private MapResourceLoader() {
    }

    public static TILED_TileMap fetch_TILED_TileMap(String jsonPath) {
        URL url = MapResourceLoader.class.getClassLoader().getResource(jsonPath);
        assert url != null;
        TILED_TileMap tiled_TileMap = null;
        try {
            log.info("Loading: {}{}", url.getPath(), "...");
            tiled_TileMap = OBJECT_MAPPER.readValue(url, TILED_TileMap.class);
            log.info("Loading {} successful!", url.getPath());
        } catch (IOException e) {
            log.error("Loading: {} failed: {}: {}", url.getPath(), e.getClass().getSimpleName(), e.getMessage());
        }
        return tiled_TileMap;
    }

    public static Tileset fetchTileset(String pngPath, int tileSize) {
        URL url = MapResourceLoader.class.getClassLoader().getResource(pngPath);
        assert url != null;
        Tileset tileset = null;
        try {
            log.info("Loading: {}{}", url.getPath(), "...");
            BufferedImage image = ImageIO.read(url);
            log.info("Loading {} successful!", url.getPath());
            SpriteSheet spriteSheet = new SpriteSheet(image, tileSize, tileSize);
            tileset = new Tileset();
            tileset.setTileset(spriteSheet, tileSize);
        } catch (IOException e) {
            log.error("Loading {} failed: {}", url.getPath(), e.getMessage());
        }
        return tileset;
    }
}
