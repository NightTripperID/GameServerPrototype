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

import java.awt.image.BufferedImage;
import java.net.URL;

public class SpriteSheet {

    private URL url;
    public final int spriteW_P; // pixel precision
    public final int spriteH_P;


    public int sheetW_P; // pixel precision
    public int sheetH_P;

    public int[] pixelBuffer;

    public Sprite[] sprites;

    public SpriteSheet(BufferedImage image, int spriteW_P, int spriteH_P) {
        this.spriteW_P = spriteW_P;
        this.spriteH_P = spriteH_P;
        sheetW_P = image.getWidth();
        sheetH_P = image.getHeight();
        pixelBuffer = new int[sheetW_P * sheetH_P];
        image.getRGB(0, 0, sheetW_P, sheetH_P, pixelBuffer, 0, sheetW_P);
    }

    public SpriteSheet(SpriteSheet sheet, int xOfs_T, int yOfs_T, int sheetW_T,
                       int sheetH_T, int spriteW_P, int sprite_P) {
        int x = xOfs_T * spriteW_P;
        int y = yOfs_T * sprite_P;
        int w = sheetW_T * spriteW_P;
        int h = sheetH_T * sprite_P;
        this.spriteW_P = w;
        this.spriteH_P = h;
        pixelBuffer = new int[w * h];
        for (int yy = 0; yy < h; yy++) {
            int yp = y + yy;
            for (int xx = 0; xx < w; xx++) {
                int xp = x + xx;
                pixelBuffer[xx + yy * w] = sheet.pixelBuffer[xp + yp * sheet.spriteW_P];
            }
        }
        int frame = 0;
        sprites = new Sprite[sheetW_T * sheetH_T];
        for (int yTile = 0; yTile < sheetH_T; yTile++) {
            for (int xTile = 0; xTile < sheetW_T; xTile++) {
                int[] spritePixels = new int[spriteW_P * sprite_P];
                for (int yPixel = 0; yPixel < sprite_P; yPixel++) {
                    for (int xPixel = 0; xPixel < spriteW_P; xPixel++) {
                        spritePixels[xPixel + yPixel * spriteW_P] = pixelBuffer[(xPixel + xTile * spriteW_P)
                                + (yPixel + yTile * sprite_P) * this.spriteW_P];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteW_P, sprite_P);
                sprites[frame++] = sprite;
            }
        }
    }
}
