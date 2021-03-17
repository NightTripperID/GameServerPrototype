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
package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.Actor;
import com.github.nighttripperid.littleengine.model.behavior.SceneTransition;
import com.github.nighttripperid.littleengine.model.scene.Scene;
import com.github.nighttripperid.littleengine.model.scene.Intent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class SceneController {
    private final Stack<Scene> sceneStack = new Stack<>();
    @Getter
    private Scene activeScene;


    public final void pushScene(Intent intent) {
        Scene scene = buildScene(intent);
        sceneStack.push(scene);
        activeScene = sceneStack.peek();
    }

    public final void popScene() {
        sceneStack.pop().onDestroy();
    }

    public final void swapScene(Intent intent) {
        sceneStack.pop().onDestroy();
        Scene scene = buildScene(intent);
        sceneStack.push(scene);
        activeScene = sceneStack.peek();
    }

    public final void performSceneTransition(SceneTransition sceneTransition) {
        if (sceneTransition != null)
            sceneTransition.perform(this);
    }

    private Scene buildScene(Intent intent) {
        try {
            Scene scene = intent.getSceneClass().newInstance();
            scene.setIntent(intent);
            scene.onCreate();
            scene.getActorData().getActors().forEach(Actor::onCreate);
            scene.getActorData().getActors().forEach(actor -> {
                if (actor.getGfxInitializer() != null) {
                    actor.getGfxInitializer().init(scene.getActorData().getSpriteMaps());
                }
            });
            scene.getGameMap().getTileset().getDynamicTiles().forEach(tile -> {
               tile.getGfxInitializer().init(scene.getGameMap().getTileset().getSpriteMaps());
            });
            return scene;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error swapping scene: {} " + e.getMessage());
        }
        return null;
    }
}
