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
package com.github.nighttripperid.littleengine.model.entity;

import com.github.nighttripperid.littleengine.model.Eventable;
import com.github.nighttripperid.littleengine.model.Rect;
import com.github.nighttripperid.littleengine.model.graphics.AnimationReel;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Entity implements Eventable, Comparable<Entity> {

    private String gfxKey;
    private int renderPriority;
    private int renderLayer;
    private Sprite sprite;
    private AnimationReel animationReel = new AnimationReel();

    private Rect hitBox = new Rect();
    private boolean removed;

    private List<RenderTask> renderTasks = new ArrayList<>();
    private BehaviorScript behaviorScript;
    private Animation animation;
    private InitGfxRoutine initGfxRoutine;
    private SceneTransition sceneTransition;

    @Override
    public int compareTo(Entity entity) {
        return (int) (this.hitBox.pos.y - entity.hitBox.pos.y);
//        return this.renderPriority - entity.renderPriority;
    }
}
