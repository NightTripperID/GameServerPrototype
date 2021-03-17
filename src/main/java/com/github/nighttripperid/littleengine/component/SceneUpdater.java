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
import com.github.nighttripperid.littleengine.model.behavior.RenderTask;
import com.github.nighttripperid.littleengine.model.scene.GameMap;
import com.github.nighttripperid.littleengine.model.scene.Scene;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.tiles.DynamicTile;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: flatten encapsulation. This is getting too bloated and complex. Needs a major redesign.
@Slf4j
public class SceneUpdater {

    @Getter
    private final ScreenBufferUpdater screenBufferUpdater;
    @Getter
    private final SceneController sceneController;
    private final CollisionResolver collisionResolver;

    public SceneUpdater(ScreenBufferUpdater screenBufferUpdater,
                        SceneController sceneController,
                        CollisionResolver collisionResolver) {
        this.screenBufferUpdater = screenBufferUpdater;
        this.sceneController = sceneController;
        this.collisionResolver = collisionResolver;
    }

    public void update(double elapsedTime) {
        addPendingActors();
        Scene activeScene = sceneController.getActiveScene();
        GameMap gameMap = sceneController.getActiveScene().getGameMap();
        activeScene.getActorData().getActors()
        .forEach(actor -> {
            actor.getRenderTasks().clear();
            runBehaviorScript(actor, elapsedTime);
            runActorAnimation(actor, activeScene.getActorData().getSpriteMaps().getMap(actor.getGfxKey()));
            collisionResolver.runActorCollision(actor, activeScene.getActorData().getActors());
            collisionResolver.runTileCollision(actor, gameMap, elapsedTime);
            sceneController.performSceneTransition(actor.getSceneTransition());
        });
        gameMap.getTileset().getDynamicTiles().forEach(dynamicTile -> {
            runTileAnimation(dynamicTile, gameMap.getTileset().getSpriteMaps().getMap(dynamicTile.getGfxKey()));
        });
        removeMarkedActors();
    }

    public void renderToScreenBuffer() {
        screenBufferUpdater.clearScreenBuffer();

        GameMap gameMap = sceneController.getActiveScene().getGameMap();
        List<Actor> actors = sceneController.getActiveScene().getActorData().getActors();

        for (int i = 1; i <= gameMap.getTileMap().getNumLayers(); i++) {
            final int layer = i;
            screenBufferUpdater.renderTileLayer(gameMap, layer);

            List<Actor> entitiesInLayer = actors.stream()
                    .filter(actor -> actor.getRenderLayer() == layer)
                    .collect(Collectors.toList());
            screenBufferUpdater.renderEntities(entitiesInLayer, gameMap);

            List<RenderTask> renderTasks = actors.stream()
                    .flatMap(actor -> actor.getRenderTasks().stream())
                    .filter(renderTask -> renderTask.getRenderLayer() == layer)
                    .collect(Collectors.toList());
            screenBufferUpdater.processRenderTasks(renderTasks);
        }
    }

    // TODO: delegate actor methods to ActorProcessor (maybe)
    public void addActor(Actor actor) {
        actor.onCreate();
        sceneController.getActiveScene().getActorData().getPendingActors().add(actor);
    }

    private void addPendingActors() {
        sceneController.getActiveScene().getActorData().getActors()
                .addAll(sceneController.getActiveScene().getActorData().getPendingActors());
        sceneController.getActiveScene().getActorData().getPendingActors().clear();
    }

    private void removeMarkedActors() {
        for(int i = 0; i < sceneController.getActiveScene().getActorData().getActors().size(); i++) {
            if(sceneController.getActiveScene().getActorData().getActors().get(i).isRemoved()) {
                sceneController.getActiveScene().getActorData().getActors().get(i).onDestroy();
                sceneController.getActiveScene().getActorData().getActors()
                        .remove(sceneController.getActiveScene().getActorData().getActors().get(i--));
            }
        }
    }

    private void runBehaviorScript(Actor actor, double timeElapsed) {
        // TODO: implement groovy integration for actor updates (maybe)
        if (actor.getBehavior() != null)
            actor.getBehavior().run(sceneController.getActiveScene().getGameMap(), timeElapsed);
    }

    private void runActorAnimation(Actor actor, Map<Integer, Sprite> spriteMap) {
        if (actor.getAnimation() != null)
            actor.getAnimation().run(spriteMap);
    }

    private void runTileAnimation(DynamicTile tile, Map<Integer, Sprite> spriteMap) {
        tile.getAnimation().run(spriteMap);
    }
}
