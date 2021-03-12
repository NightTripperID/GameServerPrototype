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
import com.github.nighttripperid.littleengine.model.PointInt;
import com.github.nighttripperid.littleengine.model.Rect;
import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.entity.RenderTask;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tile;
import com.github.nighttripperid.littleengine.staticutil.VectorMath;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
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
            entity.getRenderTasks().clear();
            runBehaviorScript(entity, elapsedTime);
            runAnimationScript(entity,
                gameStateStackController.getActiveGameState().getEntityData().getEntityGFX()
                        .getSpriteMap(entity.getFilename()));
            gameStateStackController.performGameStateTransition(entity.getGameStateTransition());
            runTileCollision(entity, gameStateStackController.getActiveGameState().getGameMap(), elapsedTime);
//            runEntityCollision(entity, elapsedTime);
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

            List<RenderTask> renderTasks = entities.stream()
                    .flatMap(entity -> entity.getRenderTasks().stream())
                    .filter(renderRequest -> renderRequest.getRenderLayer() == layer)
                    .collect(Collectors.toList());
            screenBufferUpdater.processRenderTasks(renderTasks);
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

    private void runEntityCollision(Entity entity, double elapsedTime) {
    }

    public void runTileCollision(Entity entity, GameMap gameMap, double elapsedTime) {
        // broad phase pass

        Rect rect = entity.getRect();
        // calculate 3 of 4 tile corners (the smallest entity can occupy from 1 to 4 bg tiles,
        // so any entity can occupy at the very least 4 tiles.
        // we only need to know 3 of the 4 corner tiles to get the perimeter tiles
        PointInt currTile_TL = new PointInt((int) (rect.pos.x / gameMap.getTileSize().x), // top left corner
                (int) (rect.pos.y / gameMap.getTileSize().y));
        PointInt currTile_TR = new PointInt((int) ((rect.pos.x + rect.size.x - 1) / gameMap.getTileSize().x), // top right corner
                (int) (rect.pos.y / gameMap.getTileSize().y));
        PointInt currTile_BL = new PointInt((int) (rect.pos.x / gameMap.getTileSize().x),  // bottom left corner
                (int) ((rect.pos.y + rect.size.y - 1) / gameMap.getTileSize().y));

        // get x,y coords of all tiles outside the perimeter. these are the tiles we want to check for collision.
        List<PointDouble> outerPoints = new ArrayList<>();
        // top row of perimeter (working correctly)
        for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
            outerPoints.add(new PointDouble((double)x, (double)currTile_TL.y - 1));


        // bottom row of perimeter
        for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
            outerPoints.add(new PointDouble((double)x, (double)currTile_BL.y + 1));


        // left column of perimeter (working correctly)
        for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
            outerPoints.add(new PointDouble((double)currTile_TL.x - 1, (double)y));


        // right column of perimeter
        for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
            outerPoints.add(new PointDouble((double)currTile_TR.x + 1,  (double)y));


        List<Tile> tiles = new ArrayList<>();
        outerPoints.forEach(outerPoint -> tiles.add(gameMap.getTileMap().getTile(gameMap.getTileset(), 1,
                (int)(double)outerPoint.x, (int)(double)outerPoint.y)));

        List<Rect> tileRects = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            TILED_TileMap.Object tileObject = gameMap.getTileMap().getTileObject(gameMap.getTileMap().getTileId(1,
                    (int)(double)outerPoints.get(i).x, (int)(double)outerPoints.get(i).y));
            if (tileObject != null && tileObject.getName().equals("solid")) {
                Rect r = new Rect();
                r.pos = outerPoints.get(i).times(gameMap.getTileSize());
                r.size = tiles.get(i).getRect().size;
                tileRects.add(r);
            }
        }

        resolveTileCollision(entity, tileRects, elapsedTime);

    }

    public void resolveTileCollision(Entity entity, List<Rect> sRects, double elapsedTime) {
        for (int i = 0; i < sRects.size(); i++) {
            PointDouble cp = PointDouble.of(0.0);
            PointDouble cn = PointDouble.of(0.0);
            NumWrap<Double> ct = new NumWrap<>(0.0);
            List<AbstractMap.SimpleEntry<Integer, Double>> z = new ArrayList<>();

            if (VectorMath.dynamicRectVsRect(entity.getRect(), elapsedTime, sRects.get(i), cp, cn, ct))
                z.add(new AbstractMap.SimpleEntry<>(i, ct.num));

            z = z.stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .collect(Collectors.toList());

            z.forEach(z1 -> VectorMath.resolveDynamicRectVsRect(entity.getRect(), elapsedTime, sRects.get(z1.getKey())));

        }

        if (entity.getRect().vel.x > 0) {
            entity.getRect().pos.x += Math.ceil(entity.getRect().vel.x * elapsedTime);
        }
        else if (entity.getRect().vel.x < 0) {
            entity.getRect().pos.x += Math.floor(entity.getRect().vel.x * elapsedTime);
        }

        if (entity.getRect().vel.y > 0) {
            entity.getRect().pos.y += Math.ceil(entity.getRect().vel.y * elapsedTime);
        }
        else if (entity.getRect().vel.y < 0) {
            entity.getRect().pos.y += Math.floor(entity.getRect().vel.y * elapsedTime);
        }
    }

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
