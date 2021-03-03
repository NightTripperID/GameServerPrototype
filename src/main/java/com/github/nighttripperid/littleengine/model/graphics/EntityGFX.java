package com.github.nighttripperid.littleengine.model.graphics;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class EntityGFX {
    private final Map<String, Map<Integer, Sprite>> spriteMaps = new HashMap<>();

    public void addSpriteMap(String key, SpriteSheet spriteSheet) {
        spriteMaps.put(key, new HashMap<>());
        for (int y = 0, i = 0; y < spriteSheet.getSheetHeight() / spriteSheet.spriteHeight; y++) {
            for (int x = 0; x < spriteSheet.getSheetWidth() / spriteSheet.spriteWidth; x++, i++)
                spriteMaps.get(key).put(i, new Sprite(spriteSheet, spriteSheet.spriteWidth,
                        spriteSheet.spriteHeight, x, y));
        }
    }
}
