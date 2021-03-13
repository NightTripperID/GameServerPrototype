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
import com.github.nighttripperid.littleengine.model.script.RenderTask;
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
    private final SceneStackController sceneStackController;
    private final CollisionResolver collisionResolver;

    public SceneUpdater(ScreenBufferUpdater screenBufferUpdater,
                        SceneStackController sceneStackController,
                        CollisionResolver collisionResolver) {
        this.screenBufferUpdater = screenBufferUpdater;
        this.sceneStackController = sceneStackController;
        this.collisionResolver = collisionResolver;
    }

    public void update(double elapsedTime) {
        addPendingEntities();
        Scene activeScene = sceneStackController.getActiveScene();
        GameMap gameMap = sceneStackController.getActiveScene().getGameMap();
        activeScene.getActorData().getActors()
        .forEach(actor -> {
            actor.getRenderTasks().clear();
            runBehaviorScript(actor, elapsedTime);
            runActorAnimation(actor, activeScene.getActorData().getSpriteMaps().getMap(actor.getGfxKey()));
            collisionResolver.runTileCollision(actor, gameMap, elapsedTime);
            sceneStackController.performSceneTransition(actor.getSceneTransition());
        });
        gameMap.getTileset().getDynamicTiles().forEach(dynamicTile -> {
            runTileAnimation(dynamicTile, gameMap.getTileset().getSpriteMaps().getMap(dynamicTile.getGfxKey()));
        });
        removeMarkedEntities();
    }

    public void renderToScreenBuffer() {
        screenBufferUpdater.clearScreenBuffer();

        GameMap gameMap = sceneStackController.getActiveScene().getGameMap();
        List<Actor> actors = sceneStackController.getActiveScene().getActorData().getActors();

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
        sceneStackController.getActiveScene().getActorData().getPendingActors().add(actor);
    }

    private void addPendingEntities() {
        sceneStackController.getActiveScene().getActorData().getActors()
                .addAll(sceneStackController.getActiveScene().getActorData().getPendingActors());
        sceneStackController.getActiveScene().getActorData().getPendingActors().clear();
    }

    private void removeMarkedEntities() {
        for(int i = 0; i < sceneStackController.getActiveScene().getActorData().getActors().size(); i++) {
            if(sceneStackController.getActiveScene().getActorData().getActors().get(i).isRemoved()) {
                sceneStackController.getActiveScene().getActorData().getActors().get(i).onDestroy();
                sceneStackController.getActiveScene().getActorData().getActors()
                        .remove(sceneStackController.getActiveScene().getActorData().getActors().get(i--));
            }
        }
    }

    private void runBehaviorScript(Actor actor, double timeElapsed) {
        // TODO: implement groovy integration for actor updates (maybe)
        if (actor.getBehaviorScript() != null)
            actor.getBehaviorScript().run(sceneStackController.getActiveScene().getGameMap(), timeElapsed);
    }

    private void runActorAnimation(Actor actor, Map<Integer, Sprite> spriteMap) {
        if (actor.getAnimation() != null)
            actor.getAnimation().run(spriteMap);
    }

    private void runTileAnimation(DynamicTile tile, Map<Integer, Sprite> spriteMap) {
        tile.getAnimation().run(spriteMap);
    }
}
