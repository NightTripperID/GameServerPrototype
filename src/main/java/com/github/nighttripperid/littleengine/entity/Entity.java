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

package com.github.nighttripperid.littleengine.entity;

import com.github.nighttripperid.littleengine.engine.Screen;
import com.github.nighttripperid.littleengine.gamestate.GameState;
import com.github.nighttripperid.littleengine.gamestate.Updatable;
import com.github.nighttripperid.littleengine.graphics.Renderable;

import java.io.Serializable;

/**
 * Abstract object representing a game entity that is updated and rendered by a GameState
 */
public abstract class Entity implements Updatable, Renderable, Serializable, Comparable<Entity> {

    private static final long serialVersionUID = 201711110911L;

    protected GameState gameState;
    private boolean removed;

    private int renderPriority;
    private static final int MIN_RENDER_PRIORITY = 0;
    private static final int MAX_RENDER_PRIORITY =  3;

    /**
     * Associates the Entity with its containing GameState. Overridable by API user so they can run any other code
     * when Entity is created.
     * @param gameState The Entity's containing GameState.
     */
    public void onCreate(GameState gameState) {
        this.gameState = gameState;
    }

    /**
     * Runs any final code when Entity is destroyed.
     */
    public void onDestroy() {
    }

    /**
     * Update method that is executed with each of the Engine's update calls. (60 times per second)
     */
    public abstract void update();

    /**
     * Render method that that is executed with each of the Engine's Renderable calls (as fast as hardware allows).
     * @param screen the screen to render to.
     */
    public abstract void render(Screen screen);

    /**
     * Returns whether Entity is marked for removal from the GameState.
     * @return true if the entity is marked for removal, false otherwise.
     */
    public boolean removed() {
        return removed;
    }

    /**
     * Sets whether the Entity should be removed on the GameState's next removeMarkedEntities() call.
     * @param removed true if the entity should be removed, false otherwise.
     */
    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /**
     * Sets the render priority. Valid values are 0, 1, 2, and 3. Entities with a lower priority are rendered first.
     * @param priority the specified priority.
     */
    public void setRenderPriority(int priority) {
        if(priority < MIN_RENDER_PRIORITY || priority > MAX_RENDER_PRIORITY)
            throw new IllegalArgumentException("render priority must be greater than - 1 and less than 4");
        this.renderPriority = priority;
    }

    /**
     * Compares the render priority of this entity to another entity.
     * @param entity the other entity to compare to.
     * @return less than 1 if this entity has a higher render priority,
     * 0 if they have the same prioriy, and 1 if the other entiy has a
     * higher priority.
     */
    @Override
    public int compareTo(Entity entity) {
        return this.renderPriority - entity.renderPriority;
    }
}
