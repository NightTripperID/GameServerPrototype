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

/**
 * Object that represents a Sprite animation sequence
 */
@Slf4j
public class AnimSprite extends Sprite {

    private int frame;
    public int time;
    public int frameRate = 5;
    public Sprite sprite;
    public int length = -1;

    /**
     * Creates a new AnimSprite object from a specified SpriteSheet and frame rate.
     * @param sheet The given SpriteSheet
     * @param width The Sprite width in pixel precision.
     * @param height The Sprite height in pixel precision.
     * @param length The length of the AnimSprite in Sprite precision (i.e. the number of sprites in the animation sequence).
     * @param frameRate The given rate at which to animate the sprite (the higher the number, the slower the animation rate).
     */
    public AnimSprite(SpriteSheet sheet, int width, int height, int length, int frameRate) {
        this(sheet, width, height, length);
        this.frameRate = frameRate;
    }

    /**
     * Creates a new AnimSprite object from a specified SpriteSheet and frame rate.
     * @param sheet The given SpriteSheet
     * @param width The Sprite width in pixel precision.
     * @param height The Sprite height in pixel precision.
     * @param length The length of the AnimSprite in Sprite precision (i.e. the number of sprites in the animation sequence).
     */
    public AnimSprite(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;

        if(length > sheet.getSprites().length)
            log.error("Error: animation length exceeds SpriteSheet length!");
        sprite = sheet.getSprites()[0];
    }

    /**
     * Creates an AnimSprite object copy from a specified AnimSprite original.
     * @param animSprite The given animSprite to copy
     */
    public AnimSprite(AnimSprite animSprite) {
        super(animSprite.getSpriteSheet(), animSprite.width, animSprite.height);
        this.length = animSprite.length;
        this.frameRate = animSprite.frameRate;
        this.frame = animSprite.frame;
        this.time = animSprite.time;
        sprite = animSprite.getSpriteSheet().getSprites()[0];
    }

    /**
     * Sets the AnimSprites frame index.
     * @param frame The given frame index.
     */
    public void setFrame(int frame) {
        if(frame < 0 || frame > this.getSpriteSheet().getSprites().length) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("frame index is out of bounds!");
            e.printStackTrace();
        }
        this.frame = frame;
    }

    /**
     * Gets the AnimSprites current frame index.
     * @return The given frame index.
     */
    public int getFrame() {
        return frame;
    }
}
