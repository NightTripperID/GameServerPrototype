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
package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.constant.Font5x5;
import com.github.nighttripperid.littleengine.constant.Font8x8;
import com.github.nighttripperid.littleengine.model.physics.VectorF2D;
import com.github.nighttripperid.littleengine.model.physics.Rect;
import com.github.nighttripperid.littleengine.model.behavior.RenderTask;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import lombok.Setter;

import java.util.Arrays;

public class RenderTaskHandler {

    @Setter
    private ScreenBuffer screenBuffer;

    public void process(RenderTask renderTask) {
        renderTask.handle(this);
    }

    public void fill(int col) {
        Arrays.fill(screenBuffer.getPixels(), col);
    }

    // source: https://stackoverflow.com/questions/8113629/simplified-bresenhams-line-algorithm-what-does-it-exactly-do
    public void drawLine(VectorF2D start, VectorF2D end, int col) {
        int x1 = (int)(float) start.x;
        int x2 = (int)(float) end.x;
        int y1 = (int)(float) start.y;
        int y2 = (int)(float) end.y;
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            renderPixel(x1, y1, col);
            if (x1 == x2 && y1 == y2) {
                break;
            }
            int e2 = 2 * err;
            if (e2 > -dy) {
                err = err - dy;
                x1 = x1 + sx;
            }
            if (e2 < dx) {
                err = err + dx;
                y1 = y1 + sy;
            }
        }
    }

    public void drawRect(Rect rect, int col) {
        for (int y = (int)(float) rect.pos.y; y < (int)(float) rect.pos.y + rect.size.y; y++) {
            if (y < 0 || y >= screenBuffer.getHeight())
                continue;
            for (int x = (int)(float)rect.pos.x; x < (int)(float) rect.pos.x + rect.size.x; x++) {
                if (x < 0 || x >= screenBuffer.getWidth())
                    continue;
                if (x == (int)(float) rect.pos.x || x == (int)(float) rect.pos.x + rect.size.x - 1 ||
                        y == (int)(float) rect.pos.y || y == (int)(float) rect.pos.y + rect.size.y - 1)
                    screenBuffer.getPixels()[x + y * screenBuffer.getWidth()] = col;
            }
        }
    }

    public void drawRect(VectorF2D pos, VectorF2D size, int col) {
        for (int y = (int)(float) pos.y; y < (int)(float) pos.y + size.y; y++) {
            if (y < 0 || y >= screenBuffer.getHeight())
                continue;
            for (int x = (int)(float)pos.x; x < (int)(float) pos.x + size.x; x++) {
                if (x < 0 || x >= screenBuffer.getWidth())
                    continue;
                if (x == (int)(float) pos.x || x == (int)(float) pos.x + size.x - 1 ||
                        y == (int)(float) pos.y || y == (int)(float) pos.y + size.y - 1)
                    screenBuffer.getPixels()[x + y * screenBuffer.getWidth()] = col;
            }
        }
    }

    public void drawRect(float x, float y, int width, int height, int col) {
        for (int yy = (int) y; yy < (int) y + height; yy++) {
            if (yy < 0 || yy >= screenBuffer.getHeight())
                continue;
            for (int xx = (int) x; xx < (int) x + width; xx++) {
                if (xx < 0 || xx >= screenBuffer.getWidth())
                    continue;
                if (xx == (int) x || xx == (int) x + width - 1 || yy == (int) y || yy == (int) y + height - 1)
                    screenBuffer.getPixels()[xx + yy * screenBuffer.getWidth()] = col;
            }
        }
    }

    public void fillRect(Rect rect, int col) {
        for (int y = (int)(float) rect.pos.y; y < (int)(float) rect.pos.y + rect.size.y; y++) {
            if (y < 0 || y >= screenBuffer.getHeight())
                continue;
            for (int x = (int)(float)rect.pos.x; x < (int)(float) rect.pos.x + rect.size.x; x++) {
                if (x < 0 || x >= screenBuffer.getWidth())
                    continue;
                screenBuffer.getPixels()[x + y * screenBuffer.getWidth()] = col;
            }
        }
    }

    public void fillRect(VectorF2D pos, VectorF2D size, int col) {
        for (int y = (int)(float) pos.y; y < (int)(float) pos.y + size.y; y++) {
            if (y < 0 || y >= screenBuffer.getHeight())
                continue;
            for (int x = (int)(float)pos.x; x < (int)(float) pos.x + size.x; x++) {
                if (x < 0 || x >= screenBuffer.getWidth())
                    continue;
                screenBuffer.getPixels()[x + y * screenBuffer.getWidth()] = col;
            }
        }
    }

    public void fillRect(float x, float y, int width, int height, int col) {
        for (int yy = (int) y; yy < (int) y + height; yy++) {
            if (yy < 0 || yy >= screenBuffer.getHeight())
                continue;
            for (int xx = (int) x; xx < (int) x + width; xx++) {
                if (xx < 0 || xx >= screenBuffer.getWidth())
                    continue;
                screenBuffer.getPixels()[xx + yy * screenBuffer.getWidth()] = col;
            }
        }
    }

    private void renderChar8x8(float x, float y, int col, Character[] character) {
        for (int yy = 0; yy < 8; yy++)
            for (int xx = 0; xx < 8; xx++)
                if (character[xx + (yy << 3)] == '#')
                    renderPixel(x + xx, y + yy, col);
    }

    private void renderChar5x5(float x, float y, int col, Character[] character) {
        for (int yy = 0; yy < 5; yy++)
            for (int xx = 0; xx < 5; xx++)
                if (character[xx + yy * 5] == '#')
                    renderPixel(x + xx, y + yy, col);
    }

    public void renderString8x8(float x, float y, int col, String string) {
        for (int i = 0; i < string.length(); i++)
            renderChar8x8(x + (i << 3), y, col, Font8x8.getChar(string.charAt(i)));
    }

    public void renderString5x5(float x, float y, int col, String string) {
        for (int i = 0; i < string.length(); i++)
            renderChar5x5(x + (i * 5), y, col, Font5x5.getChar(string.charAt(i)));
    }

    public void renderPixels(int[] pixels) {
        if (pixels.length != screenBuffer.getPixels().length)
            throw new IllegalArgumentException("pixel lengths (i.e. screen dimensions) must match.");

        System.arraycopy(pixels, 0, screenBuffer.getPixels(), 0, pixels.length);
    }

    public void renderPixel(int index, int col) {
        if (index < 0 || index >= screenBuffer.getWidth() * screenBuffer.getHeight())
            return;

        screenBuffer.getPixels()[index] = col;
    }

    public void renderPixel(float x, float y, int col) {
        if (x < 0 || x >= screenBuffer.getWidth() || y < 0 || y >= screenBuffer.getHeight())
            return;

        screenBuffer.getPixels()[(int) x + (int) y * screenBuffer.getWidth()] = col;
    }

}
