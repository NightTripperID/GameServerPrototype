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

import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;

public class SpriteUtil {

    private SpriteUtil() {
    }

    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.sheetW_P * sheet.sheetH_P) / (sheet.spriteW_P * sheet.spriteH_P);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.spriteW_P * sheet.spriteH_P];

        for (int yp = 0; yp < sheet.sheetH_P / sheet.spriteH_P; yp++) {
            for (int xp = 0; xp < sheet.sheetW_P / sheet.spriteW_P; xp++) {

                for (int y = 0; y < sheet.spriteH_P; y++) {
                    for (int x = 0; x < sheet.spriteW_P; x++) {
                        int xo = x + xp * sheet.spriteW_P;
                        int yo = y + yp * sheet.spriteH_P;
                        pixels[x + y * sheet.spriteW_P] = sheet.pixelBuffer[xo + yo * sheet.sheetW_P];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.spriteW_P, sheet.spriteH_P);
            }
        }
        return sprites;
    }

    public static Sprite rotate(Sprite sprite, float angle){
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    public static void updateAnimReel(AnimationReel animationReel) {
        if(++animationReel.time % animationReel.frameRate == 0) {
            if (++animationReel.frame == animationReel.length + animationReel.offset) {
                animationReel.frame = animationReel.offset;
            }
        }
    }

    private static int[] rotate(int[] pixels, int width, int height, float angle) {
        int[] result = new int[width * height];

        float nx_x = rot_x(-angle, 1.0f, 0.0f);
        float nx_y = rot_y(-angle, 1.0f, 0.0f);
        float ny_x = rot_x(-angle, 0.0f, 1.0f);
        float ny_y = rot_y(-angle, 0.0f, 1.0f);

        float x0 = rot_x(-angle, -width / 2.0f, -height / 2.0f) + width / 2.0f;
        float y0 = rot_y(-angle, -width / 2.0f, -height / 2.0f) + height / 2.0f;

        for (int y = 0; y < height; y++) {
            float x1 = x0;
            float y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height)
                    col = 0xffff00ff;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    private static float rot_x(float angle, float x, float y) {
        float cos = (float) Math.cos(angle-Math.PI / 2);
        float sin = (float) Math.sin(angle- Math.PI / 2);
        return x * cos + y * -sin;
    }

    private static float rot_y(float angle, float x, float y) {
        float cos = (float) Math.cos(angle-Math.PI / 2);
        float sin = (float) Math.sin(angle-Math.PI / 2);
        return x * sin + y * cos;
    }

}
