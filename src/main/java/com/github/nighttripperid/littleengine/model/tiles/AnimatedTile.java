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
package com.github.nighttripperid.littleengine.model.tiles;

import com.github.nighttripperid.littleengine.model.entity.Animation;
import com.github.nighttripperid.littleengine.model.entity.InitGfxRoutine;
import com.github.nighttripperid.littleengine.staticutil.SpriteLoader;
import com.github.nighttripperid.littleengine.staticutil.SpriteUtil;
import lombok.Getter;

@Getter
public class AnimatedTile extends Tile {

    public AnimatedTile(Tile tile, String gfxKey, String frameRate, String length) {
        super(tile);
        this.setGfxKey(gfxKey);
        this.getAnimationReel().frameRate = Integer.parseInt(frameRate);
        this.getAnimationReel().length = Integer.parseInt(length);
        this.setAnimation(initAnimation());
        this.setInitGfxRoutine(initGfxRoutine());
    }

    private Animation initAnimation() {
        return new Animation(spriteMap -> {
            SpriteUtil.updateAnimReel(this.getAnimationReel());
            this.setSprite(spriteMap.get(this.getAnimationReel().frame));
        });
    }

    private InitGfxRoutine initGfxRoutine() {
        return new InitGfxRoutine(spriteMaps -> {
            SpriteLoader.loadSpritesByColumns(this.getGfxKey(), this.getSprite().width, this.getSprite().height, spriteMaps);
            this.setSprite(spriteMaps.getMap(this.getGfxKey()).get(this.getAnimationReel().frame));
        });
    }
}
