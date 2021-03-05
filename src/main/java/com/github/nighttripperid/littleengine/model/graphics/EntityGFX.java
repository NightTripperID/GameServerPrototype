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

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EntityGFX {
    private final Map<String, Map<Integer, Sprite>> spriteMaps = new HashMap<>();

    public void addSpritesByColumns(String key, SpriteSheet spriteSheet) {
        if(spriteMaps.get(key) != null)
            return;
        spriteMaps.put(key, new HashMap<>());
        for (int x = 0, i = 0; x < spriteSheet.sheetW_P / spriteSheet.spriteW_P; x++) {
            for (int y = 0; y < spriteSheet.sheetH_P / spriteSheet.spriteH_P; y++, i++)
                spriteMaps.get(key).put(i, new Sprite(spriteSheet, spriteSheet.spriteW_P,
                        spriteSheet.spriteH_P, x, y));
        }
    }

    public void addSpritesByRows(String key, SpriteSheet spriteSheet) {
        if(spriteMaps.get(key) != null)
            return;
        spriteMaps.put(key, new HashMap<>());
        for (int y = 0, i = 0; y < spriteSheet.sheetH_P / spriteSheet.spriteH_P; y++) {
            for (int x = 0; x < spriteSheet.sheetW_P / spriteSheet.spriteW_P; x++, i++)
                spriteMaps.get(key).put(i, new Sprite(spriteSheet, spriteSheet.spriteW_P,
                        spriteSheet.spriteH_P, x, y));
        }
    }
}
