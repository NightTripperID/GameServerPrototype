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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnimSprite extends Sprite {

    private int frame;
    public int time;
    public int frameRate = 5;
    public Sprite sprite;
    public int length = -1;

    public AnimSprite(SpriteSheet sheet, int width, int height, int length, int frameRate) {
        this(sheet, width, height, length);
        this.frameRate = frameRate;
    }

    public AnimSprite(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;

        if(length > sheet.getSprites().length)
            log.error("Error: animation length exceeds SpriteSheet length!");
        sprite = sheet.getSprites()[0];
    }

    public AnimSprite(AnimSprite animSprite) {
        super(animSprite.getSpriteSheet(), animSprite.width, animSprite.height);
        this.length = animSprite.length;
        this.frameRate = animSprite.frameRate;
        this.frame = animSprite.frame;
        this.time = animSprite.time;
        sprite = animSprite.getSpriteSheet().getSprites()[0];
    }

    public void setFrame(int frame) {
        if(frame < 0 || frame > this.getSpriteSheet().getSprites().length) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("frame index is out of bounds!");
            e.printStackTrace();
        }
        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }
}
