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
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.PointInt;
import com.github.nighttripperid.littleengine.model.graphics.AnimReel;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Entity implements Eventable, Comparable<Entity> {
    private static final int MIN_RENDER_PRIORITY = 0;
    private static final int MAX_RENDER_PRIORITY =  3;

    private boolean removed;
    private int renderPriority;
    private int renderLayer;
    private String spriteKey;
    private Sprite sprite;
    private AnimReel animReel = new AnimReel();

    public PointInt direction = new PointInt();
    public PointDouble position = new PointDouble();
    public PointDouble speed = new PointDouble();

    private List<RenderRequest> renderRequests = new ArrayList<>();
    private BehaviorScript behaviorScript;
    private AnimationScript animationScript;
    private GfxInitScript gfxInitScript;

    @Override
    public int compareTo(Entity entity) {
        return (int) (this.getPosition().y - entity.getPosition().y);
//        return this.renderPriority - entity.renderPriority;
    }
}