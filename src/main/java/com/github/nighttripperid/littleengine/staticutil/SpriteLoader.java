package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.graphics.EntityGFX;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;

public class SpriteLoader {
    private SpriteLoader(){
    }
    public static void loadSprites(String filePath, String key, int spriteWidth, int spriteHeight,
                                   EntityGFX entityGFX) {
        SpriteSheet spriteSheet = new SpriteSheet(filePath, spriteWidth, spriteHeight);
        entityGFX.addSpriteMap(key, spriteSheet);
    }
}
