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
package com.github.nighttripperid.littleengine.model.graphics;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class SpriteSheet {

    private URL url;
    public final int spriteWidth; // pixel precision
    public final int spriteHeight;

    @Getter
    private int sheetWidth; // pixel precision
    @Getter
    private int sheetHeight;

    @Getter
    public int[] pixelBuffer;

    @Getter
    private Sprite[] sprites;

    public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
        url = getClass().getClassLoader().getResource(path);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        load();
    }

    public SpriteSheet(SpriteSheet sheet, int xOfsInTiles, int yOfsInTiles, int spriteSheetWidthInTiles,
                       int spriteSheetHeightInTiles, int spriteWidth, int spriteHeight) {
        int x = xOfsInTiles * spriteWidth;
        int y = yOfsInTiles * spriteHeight;
        int w = spriteSheetWidthInTiles * spriteWidth;
        int h = spriteSheetHeightInTiles * spriteHeight;
        this.spriteWidth = w;
        this.spriteHeight = h;
        pixelBuffer = new int[w * h];
        for (int yy = 0; yy < h; yy++) {
            int yp = y + yy;
            for (int xx = 0; xx < w; xx++) {
                int xp = x + xx;
                pixelBuffer[xx + yy * w] = sheet.pixelBuffer[xp + yp * sheet.spriteWidth];
            }
        }
        int frame = 0;
        sprites = new Sprite[spriteSheetWidthInTiles * spriteSheetHeightInTiles];
        for (int yTile = 0; yTile < spriteSheetHeightInTiles; yTile++) {
            for (int xTile = 0; xTile < spriteSheetWidthInTiles; xTile++) {
                int[] spritePixels = new int[spriteWidth * spriteHeight];
                for (int yPixel = 0; yPixel < spriteHeight; yPixel++) {
                    for (int xPixel = 0; xPixel < spriteWidth; xPixel++) {
                        spritePixels[xPixel + yPixel * spriteWidth] = pixelBuffer[(xPixel + xTile * spriteWidth)
                                + (yPixel + yTile * spriteHeight) * this.spriteWidth];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteWidth, spriteHeight);
                sprites[frame++] = sprite;
            }
        }
    }

    private void load() {
        try {
            log.info("Loading: {}{}", url.toString(), "...");
            BufferedImage image = ImageIO.read(url);
            sheetWidth = image.getWidth();
            sheetHeight = image.getHeight();
            pixelBuffer = new int[sheetWidth * sheetHeight];
            image.getRGB(0, 0, sheetWidth, sheetHeight, pixelBuffer, 0, sheetWidth);
            log.info("Loading: {} successful!", url.toString());
        } catch (IOException e) {
            log.error("Loading: {} failed!", url.toString());
            e.printStackTrace();
        }
    }
}
