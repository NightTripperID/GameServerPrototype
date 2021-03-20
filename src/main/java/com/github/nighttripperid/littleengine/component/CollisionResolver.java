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
import com.github.nighttripperid.littleengine.model.physics.NumWrap;
import com.github.nighttripperid.littleengine.model.physics.VectorF2D;
import com.github.nighttripperid.littleengine.model.physics.VectorI2D;
import com.github.nighttripperid.littleengine.model.physics.Rect;
import com.github.nighttripperid.littleengine.model.scene.GameMap;
import com.github.nighttripperid.littleengine.model.tiles.Tile;
import com.github.nighttripperid.littleengine.staticutil.VectorMath;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollisionResolver {

    public void runActorCollision(Actor actor1, List<Actor> actors) {
        actors.forEach(actor2 -> {
            if(actor1.equals(actor2)) return;
           if (VectorMath.RectVsRect(actor1.getPhysBody(), actor2.getPhysBody())) {
               if (actor1.getCollisionResult() != null)
                   actor1.getCollisionResult().run(actor2);
               if (actor2.getCollisionResult() != null)
                   actor2.getCollisionResult().run(actor1);
           }
        });
    }

    public void runTileCollision(Actor actor, GameMap gameMap, float elapsedTime) {
        // broad phase pass
        // TODO: fix scanning algorithm to properly detect edges when hitbox is equal to or smaller than tile size
        Rect hitBox = actor.getPhysBody();
        // calculate 3 of 4 tile corners (the smallest actor can occupy from 1 to 4 bg tiles,
        // so any actor can occupy at the very least 4 tiles.
        // we only need to know 3 of the 4 corner tiles to get the perimeter tiles
        VectorI2D currTile_TL = new VectorI2D((int) (hitBox.pos.x / gameMap.getTileSize().x), // top left corner
                (int) (hitBox.pos.y / gameMap.getTileSize().y));
        VectorI2D currTile_TR = new VectorI2D((int) ((hitBox.pos.x + hitBox.size.x - 1) / gameMap.getTileSize().x), // top right corner
                (int) (hitBox.pos.y / gameMap.getTileSize().y));
        VectorI2D currTile_BL = new VectorI2D((int) (hitBox.pos.x / gameMap.getTileSize().x),  // bottom left corner
                (int) ((hitBox.pos.y + hitBox.size.y - 1) / gameMap.getTileSize().y));

        // get x,y coords of all tiles outside the perimeter. these are the tiles we want to check for collision.
        // scan only the rows and columns that are relevant to the current velocity vector.
        // e.g., if velocity.x < 0 and velocity y > 0, then scan only the left column and bottom row
        List<VectorF2D> outerPoints = new ArrayList<>();
        // top row of perimeter
        if (hitBox.vel.y < 0.0f) {
            for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
                outerPoints.add(new VectorF2D((float) x, (float) currTile_TL.y - 1));
        }

        // bottom row of perimeter
        if (hitBox.vel.y > 0.0f) {
            for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
                outerPoints.add(new VectorF2D((float) x, (float) currTile_BL.y + 1));
        }

        // left column of perimeter
        if (hitBox.vel.x < 0.0f) {
            for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
                outerPoints.add(new VectorF2D((float) currTile_TL.x - 1, (float) y));
        }

        // right column of perimeter
        if (hitBox.vel.x > 0.0f) {
            for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
                outerPoints.add(new VectorF2D((float) currTile_TR.x + 1, (float) y));
        }

        for (int i = 1; i <= gameMap.getTileMap().getNumLayers(); i++) {
            List<Tile> tiles = new ArrayList<>();
            final int layer = i;
            outerPoints.forEach(outerPoint -> tiles.add(gameMap.getTileMap().getTile(gameMap.getTileset(), layer,
                    (int) (float) outerPoint.x, (int) (float) outerPoint.y)));

            List<Rect> tileRects = new ArrayList<>();
            for (int k = 0; k < tiles.size(); k++) {
                Tile tile = tiles.get(k);
                if (tile != null &&
                    (tile.getAttributes().contains("solidTopEdge") && hitBox.vel.y > 0.0f) ||
                    (tile.getAttributes().contains("solidBottomEdge") && hitBox.vel.y < 0.0f) ||
                    (tile.getAttributes().contains("solidLeftEdge") && hitBox.vel.x > 0.0f) ||
                    (tile.getAttributes().contains("solidRightEdge") && hitBox.vel.x < 0.0f)) {
                        Rect r = new Rect();
                        r.pos = outerPoints.get(k).times(gameMap.getTileSize());
                        r.size = tiles.get(k).getPhysBody().size;
                        tileRects.add(r);
                }
            }
            resolveTileCollision(actor, tileRects, elapsedTime);
        }

            actor.getPhysBody().pos
                    .set(actor.getPhysBody().pos.plus(actor.getPhysBody().vel.times(VectorF2D.of(elapsedTime))));
    }

    private void resolveTileCollision(Actor actor, List<Rect> sRects, float elapsedTime) {
        for (int i = 0; i < sRects.size(); i++) {
            VectorF2D cp = VectorF2D.of(0.0f);
            VectorF2D cn = VectorF2D.of(0.0f);
            NumWrap<Float> ct = new NumWrap<>(0.0f);
            List<AbstractMap.SimpleEntry<Integer, Float>> z = new ArrayList<>();

            if (VectorMath.dynamicRectVsRect(actor.getPhysBody(), elapsedTime, sRects.get(i), cp, cn, ct))
                z.add(new AbstractMap.SimpleEntry<>(i, ct.num));

            z = z.stream()
                    .sorted(Map.Entry.<Integer, Float>comparingByValue().reversed())
                    .collect(Collectors.toList());

            z.forEach(z1 -> VectorMath.resolveDynamicRectVsRect(actor.getPhysBody(), elapsedTime, sRects.get(z1.getKey())));

        }
    }
}
