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

import com.github.nighttripperid.littleengine.model.NumWrap;
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.Rect;
import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.entity.RenderRequest;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.staticutil.VectorMath;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: flatten encapsulation. This is getting too bloated and complex. Needs a major redesign.
@Slf4j
public class GameStateUpdater {

    @Getter
    private final ScreenBufferUpdater screenBufferUpdater;
    @Getter
    private final GameStateStackController gameStateStackController;

    public GameStateUpdater(ScreenBufferUpdater screenBufferUpdater,
                            GameStateStackController gameStateStackController) {
        this.screenBufferUpdater = screenBufferUpdater;
        this.gameStateStackController = gameStateStackController;
    }

    public void update(double elapsedTime) {
        addPendingEntities();
        gameStateStackController.getActiveGameState().getEntityData().getEntities()
        .forEach(entity -> {
            entity.getRenderRequests().clear();
            runBehaviorScript(entity, elapsedTime);
            runAnimationScript(entity,
                gameStateStackController.getActiveGameState().getEntityData().getEntityGFX()
                        .getSpriteMap(entity.getFilename()));
            gameStateStackController.performGameStateTransition(entity.getGameStateTransition());
            runCollisionCheck(entity, elapsedTime);
        });
        removeMarkedEntities();
    }

    public void renderToScreenBuffer() {
        screenBufferUpdater.clearScreenBuffer();

        GameMap gameMap = gameStateStackController.getActiveGameState().getGameMap();
        List<Entity> entities = gameStateStackController.getActiveGameState().getEntityData().getEntities();

        for (int i = 1; i <= gameMap.getTileMap().getNumLayers(); i++) {
            final int layer = i;
            screenBufferUpdater.renderTileLayer(gameMap, layer);

            List<Entity> entitiesInLayer = entities.stream()
                    .filter(entity -> entity.getRenderLayer() == layer)
                    .collect(Collectors.toList());
            screenBufferUpdater.renderEntities(entitiesInLayer, gameMap);

            List<RenderRequest> renderRequests = entities.stream()
                    .flatMap(entity -> entity.getRenderRequests().stream())
                    .filter(renderRequest -> renderRequest.getRenderLayer() == layer)
                    .collect(Collectors.toList());
            screenBufferUpdater.processRenderRequests(renderRequests);
        }
    }

    // TODO: delegate entity methods to EntityProcessor (maybe)
    public void addEntity(Entity entity) {
        entity.onCreate();
        gameStateStackController.getActiveGameState().getEntityData().getPendingEntities().add(entity);
    }

    private void addPendingEntities() {
        gameStateStackController.getActiveGameState().getEntityData().getEntities()
                .addAll(gameStateStackController.getActiveGameState().getEntityData().getPendingEntities());
        gameStateStackController.getActiveGameState().getEntityData().getPendingEntities().clear();
    }

    private void removeMarkedEntities() {
        for(int i = 0; i < gameStateStackController.getActiveGameState().getEntityData().getEntities().size(); i++) {
            if(gameStateStackController.getActiveGameState().getEntityData().getEntities().get(i).isRemoved()) {
                gameStateStackController.getActiveGameState().getEntityData().getEntities().get(i).onDestroy();
                gameStateStackController.getActiveGameState().getEntityData().getEntities()
                        .remove(gameStateStackController.getActiveGameState().getEntityData().getEntities().get(i--));
            }
        }
    }

    // TODO: finish implementing projected rectangle tile collision
    private void runCollisionCheck(Entity entity, double elapsedTime) {

        List<Entity> entities = gameStateStackController.getActiveGameState().getEntityData().getEntities();

        for (int i = 0; i < entities.size(); i++) {
            PointDouble cp = PointDouble.of(0.0);
            PointDouble cn = PointDouble.of(0.0);
            NumWrap<Double> ct = new NumWrap<>(0.0);
            List<AbstractMap.SimpleEntry<Integer, Double>> z = new ArrayList<>();
            if (entity.equals(entities.get(i)))
                continue;

            if (VectorMath.dynamicRectVsRect(entity.getRect(), elapsedTime, entities.get(i).getRect(), cp, cn, ct))
                z.add(new AbstractMap.SimpleEntry<>(i, ct.num));

            z = z.stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .collect(Collectors.toList());

            z.forEach(z1 -> VectorMath.resolveDynamicRectVsRect(entity.getRect(), elapsedTime, entities.get(z1.getKey()).getRect()));

        }
        entity.getRect().pos.set(
                entity.getRect().pos
                        .plus(entity.getRect().vel.times(PointDouble.of(elapsedTime)))
        );
    }

    // TODO: figure out how to determine which tiles to check without checking every tile on the map
//    public void tileCollision(Rect dRect, List<Rect> sRects, double elapsedTime) {
//
//        for (int i = 0; i < sRects.size(); i++) {
//            PointDouble cp = PointDouble.of(0.0);
//            PointDouble cn = PointDouble.of(0.0);
//            NumWrap<Double> ct = new NumWrap<>(0.0);
//            List<AbstractMap.SimpleEntry<Integer, Double>> z = new ArrayList<>();
//
//            if (VectorMath.dynamicRectVsRect(dRect, elapsedTime, sRects.get(i), cp, cn, ct))
//                z.add(new AbstractMap.SimpleEntry<>(i, ct.num));
//
//            z = z.stream()
//                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
//                    .collect(Collectors.toList());
//
//            z.forEach(z1 -> VectorMath.resolveDynamicRectVsRect(dRect, elapsedTime, sRects.get(z1.getKey())));
//
//        }
//        dRect.pos.set(dRect.pos.plus(dRect.vel.times(PointDouble.of(elapsedTime))));
//    }


    private void runAnimationScript(Entity entity, Map<Integer, Sprite> spriteMap) {
        if (entity.getAnimationScript() != null)
            entity.getAnimationScript().run(spriteMap);
    }

    private void runBehaviorScript(Entity entity, double timeElapsed) {
        // TODO: implement groovy integration for entity updates (maybe)
        if (entity.getBehaviorScript() != null)
            entity.getBehaviorScript().run(gameStateStackController.getActiveGameState().getGameMap(), timeElapsed);
    }
}
