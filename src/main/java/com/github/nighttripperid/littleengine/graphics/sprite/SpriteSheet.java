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

package com.github.nighttripperid.littleengine.graphics.sprite;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * SpriteSheet is an image that contains images that can be selected to make com.bitburger.graphics.Sprite objects. Image data is loaded from a png file
 * that is typically comprised of multiple sprite images, e.g. the animation frames for a video game character such as Mario.
 *
 */
@Slf4j
public class SpriteSheet {

    private URL url;
    public final int spriteWidth; // pixel precision
    public final int spriteHeight;

    @Getter
    private int spriteSheetWidth; // pixel precision
    @Getter
    private int spriteSheetHeight;

    @Getter
    public int[] pixelBuffer;

    @Getter
    private Sprite[] sprites;

    /**
     * Creates a SpriteSheet object of specified width and height in pixel precision. A width = 16 and a height = 32 means
     * a SpriteSheet object 16 pixels wide and 32 pixels tall.
     * @param path: The url path of the image file.
     * @param spriteWidth: Width in pixels.
     * @param spriteHeight: Height in pixels.
     */
    public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
        url = getClass().getClassLoader().getResource(path);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        load();
    }

    /**
     * Creates a new SpriteSheet object from another SpriteSheet. This constructor is typically used to create
     * a smaller SpriteSheet object from a larger one. Width and height are in tile precision, meaning width = 5 and
     * height = 5 creates a SpriteSheet object 5 sprite tiles wide and 5 sprite tiles tall, each sprite being spriteSize
     * pixels wide and spriteSize pixels tall.
     * @param sheet: the SpriteSheet object to copy from.
     * @param xOfsInTiles: the starting x coordinate to copy from in tile precision.
     * @param yOfsInTiles: the starting y coordinate to copy from in tile precision.
     * @param spriteSheetWidthInTiles: the width of the new SpriteSheet in tile precision.
     * @param spriteSheetHeightInTiles: the height of the new SpriteSheet in tile precision.
     * @param spriteWidth: the width of the sprite tiles in pixel precision.
     * @param spriteHeight: the height of the sprite tiles in pixel precision.
     */
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

    /**
     * Loads image from specified URL to SpriteSheet object's pixel buffer.
     */
    private void load() {
        try {
            log.info("Loading: {}{}", url.toString(), "...");
            BufferedImage image = ImageIO.read(url);
            spriteSheetWidth = image.getWidth();
            spriteSheetHeight = image.getHeight();
            pixelBuffer = new int[spriteSheetWidth * spriteSheetHeight];
            image.getRGB(0, 0, spriteSheetWidth, spriteSheetHeight, pixelBuffer, 0, spriteSheetWidth);
            log.info("Loading: {} successful!", url.toString());
        } catch (IOException e) {
            log.info("Loading: {} failed!", url.toString());
            e.printStackTrace();
        }
    }
}