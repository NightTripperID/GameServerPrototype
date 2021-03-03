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
import com.github.nighttripperid.littleengine.model.PointInt;
import lombok.Getter;

public class Sprite {

    public final int width;
    public final int height;

    @Getter
    private SpriteSheet spriteSheet;

    private PointInt offset = new PointInt();
    public int[] pixels;

    protected Sprite(SpriteSheet spriteSheet, int width, int height) {
        this.width = width;
        this.height = height;
        this.spriteSheet = spriteSheet;
    }

    public Sprite(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    public Sprite(SpriteSheet spriteSheet, int width, int height, int xOfs, int yOfs) {
        this.spriteSheet = spriteSheet;
        this.width = width;
        this.height = height;
        this.offset = new PointInt(xOfs * width, yOfs * height);
        pixels = new int[width * height];
        load();
    }

    public Sprite(SpriteSheet spriteSheet, int size, int xOfs, int yOfs) {
        this.spriteSheet = spriteSheet;
        this.width = size;
        this.height = size;
        this.offset = new PointInt(xOfs * size, yOfs * size);
        pixels = new int[size * size];
        load();
    }

    public Sprite(int col, int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColor(col);
    }

    private void load() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = spriteSheet.pixelBuffer[(x + offset.x) + (y + offset.y) * spriteSheet.getSheetWidth()];
            }
        }
    }

    private void setColor(int col) {
        for(int i = 0; i < pixels.length; i++)
            pixels[i] = col;
    }
}

