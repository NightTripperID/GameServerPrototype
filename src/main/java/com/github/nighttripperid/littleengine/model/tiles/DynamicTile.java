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

import com.github.nighttripperid.littleengine.model.Eventable;
import com.github.nighttripperid.littleengine.model.object.DynamicObject;
import com.github.nighttripperid.littleengine.model.physics.Rect;
import com.github.nighttripperid.littleengine.model.behavior.*;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.staticutil.SpriteLoader;
import com.github.nighttripperid.littleengine.staticutil.SpriteUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DynamicTile implements Tile, DynamicObject, Eventable {

    private int id;
    private Sprite sprite;
    private Rect physBody;
    private List<String> attributes;

    private String gfxKey;
    private String frameRate;
    private String length;
    private AnimationReel animationReel = new AnimationReel();

    private List<RenderTask> renderTasks;
    private Behavior behavior;
    private Animation animation;
    private GfxInitializer gfxInitializer;
    private SceneTransition sceneTransition;
    private CollisionResult collisionResult;

    public DynamicTile(Tile tile) {
        this.physBody = tile.getPhysBody();
        this.attributes = tile.getAttributes();
        this.sprite = tile.getSprite();
    }

    private Animation initAnimation() {
        return new Animation(spriteMap -> {
            SpriteUtil.updateAnimReel(this.getAnimationReel());
            this.sprite = spriteMap.get(this.getAnimationReel().frame);
        });
    }

    private GfxInitializer initGfx() {
        return new GfxInitializer(spriteMaps -> {
            SpriteLoader.loadSpritesByColumns(this.gfxKey, this.sprite.size.x, this.sprite.size.y, spriteMaps);
            this.sprite = spriteMaps.getMap(this.gfxKey).get(this.animationReel.frame);
        });
    }

    @Override
    public void onCreate() {
        this.animation = initAnimation();
        this.gfxInitializer = initGfx();
        this.animationReel.length = Integer.parseInt(this.length);
        this.animationReel.frameRate = Integer.parseInt(this.frameRate);
    }

    @Override
    public void onDestroy() {

    }
}
