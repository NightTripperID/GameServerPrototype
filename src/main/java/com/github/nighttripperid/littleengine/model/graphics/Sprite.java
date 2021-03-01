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

/**
 * Represents a 2 dimensional graphic on screen. Sprite data is loaded from a SpriteSheet object.
 * Width, height, and coordinates can be specified. Sprites can also be rotated using affine matrix transformation.
 */
public class Sprite {

    public final int width;
    public final int height;

    @Getter
    private SpriteSheet spriteSheet;

    private PointInt offset = new PointInt();
    public int[] pixels;

    /**
     * Creates a sprite strip from a SpriteSheet object
     * @param spriteSheet: The sheet from which the sprite strip is grabbed.
     * @param width: The width of the sprite strip.
     * @param height: The height of the sprite strip.
     */
    protected Sprite(SpriteSheet spriteSheet, int width, int height) {
        this.width = width;
        this.height = height;
        this.spriteSheet = spriteSheet;
    }

    /**
     * Creates a Sprite object using a specified pixel array.
     * @param pixels The pixels representing the Sprite.
     * @param width The Sprite width.
     * @param height The Sprite height.
     */
    public Sprite(int[] pixels, int width, int height) {
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    /**
     * Creates a Sprite object using a specified SpriteSheet
     * @param spriteSheet The given SpriteSheet.
     * @param width The Sprite width.
     * @param height The Sprite height.
     * @param xOfs The x offset of the Sprite on the SpriteSheet in tile precision.
     * @param yOfs The y offset of the Sprite on the SpriteSheet in tile precision.
     */
    public Sprite(SpriteSheet spriteSheet, int width, int height, int xOfs, int yOfs) {
        this.spriteSheet = spriteSheet;
        this.width = width;
        this.height = height;
        this.offset = new PointInt(xOfs * width, yOfs * height);
        pixels = new int[width * height];
        load();
    }

    /**
     * Creates a Sprite object using a specified SpriteSheet
     * @param spriteSheet The given SpriteSheet.
     * @param size The Sprite size.
     * @param xOfs The x offset of the Sprite on the SpriteSheet in tile precision.
     * @param yOfs The y offset of the Sprite on the SpriteSheet in tile precision.
     */
    public Sprite(SpriteSheet spriteSheet, int size, int xOfs, int yOfs) {
        this.spriteSheet = spriteSheet;
        this.width = size;
        this.height = size;
        this.offset = new PointInt(xOfs * size, yOfs * size);
        pixels = new int[size * size];
        load();
    }

    /**
     * Creates a Sprite object that is filled with a solid color.
     * @param col The given color.
     * @param width The given width.
     * @param height The given height.
     */
    public Sprite(int col, int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColor(col);
    }

    /**
     * Loads the Sprite pixels from the Sprite's SpriteSheet.
     */
    private void load() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[x + y * width] = spriteSheet.pixelBuffer[(x + offset.x) + (y + offset.y) * spriteSheet.getWidth()];
            }
        }
    }

    /**
     * Fills the Sprite with the specified color.
     * @param col The given color
     */
    private void setColor(int col) {
        for(int i = 0; i < pixels.length; i++)
            pixels[i] = col;
    }
}
