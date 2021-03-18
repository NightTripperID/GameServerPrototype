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
import com.github.nighttripperid.littleengine.model.physics.PointDouble;
import com.github.nighttripperid.littleengine.model.physics.PointInt;
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
           if (VectorMath.RectVsRect(actor1.getHitBox(), actor2.getHitBox())) {
               if (actor1.getCollisionResult() != null)
                   actor1.getCollisionResult().run(actor2);
               if (actor2.getCollisionResult() != null)
                   actor2.getCollisionResult().run(actor1);
           }
        });
    }

    public void runTileCollision(Actor actor, GameMap gameMap, double elapsedTime) {
        // broad phase pass

        Rect hitBox = actor.getHitBox();
        // calculate 3 of 4 tile corners (the smallest actor can occupy from 1 to 4 bg tiles,
        // so any actor can occupy at the very least 4 tiles.
        // we only need to know 3 of the 4 corner tiles to get the perimeter tiles
        PointInt currTile_TL = new PointInt((int) (hitBox.pos.x / gameMap.getTileSize().x), // top left corner
                (int) (hitBox.pos.y / gameMap.getTileSize().y));
        PointInt currTile_TR = new PointInt((int) ((hitBox.pos.x + hitBox.size.x - 1) / gameMap.getTileSize().x), // top right corner
                (int) (hitBox.pos.y / gameMap.getTileSize().y));
        PointInt currTile_BL = new PointInt((int) (hitBox.pos.x / gameMap.getTileSize().x),  // bottom left corner
                (int) ((hitBox.pos.y + hitBox.size.y - 1) / gameMap.getTileSize().y));

        // get x,y coords of all tiles outside the perimeter. these are the tiles we want to check for collision.
        // scan only the rows and columns that are relevant to the current velocity vector.
        // e.g., if velocity.x < 0 and velocity y > 0, then scan only the left column and bottom row
        List<PointDouble> outerPoints = new ArrayList<>();
        // top row of perimeter
        if (hitBox.vel.y < 0) {
            for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
                outerPoints.add(new PointDouble((double) x, (double) currTile_TL.y - 1));
        }

        // bottom row of perimeter
        if (hitBox.vel.y > 0) {
            for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
                outerPoints.add(new PointDouble((double) x, (double) currTile_BL.y + 1));
        }

        // left column of perimeter
        if (hitBox.vel.x < 0) {
            for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
                outerPoints.add(new PointDouble((double) currTile_TL.x - 1, (double) y));
        }

        // right column of perimeter
        if (hitBox.vel.x > 0) {
            for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
                outerPoints.add(new PointDouble((double) currTile_TR.x + 1, (double) y));
        }

        for (int i = 1; i <= gameMap.getTileMap().getNumLayers(); i++) {
            List<Tile> tiles = new ArrayList<>();
            final int layer = i;
            outerPoints.forEach(outerPoint -> tiles.add(gameMap.getTileMap().getTile(gameMap.getTileset(), layer,
                    (int) (double) outerPoint.x, (int) (double) outerPoint.y)));

            List<Rect> tileRects = new ArrayList<>();
            for (int k = 0; k < tiles.size(); k++) {
                Tile tile = tiles.get(k);
                if (tile != null &&
                    (tile.getAttributes().contains("solidTopEdge") && hitBox.vel.y > 0) ||
                    (tile.getAttributes().contains("solidBottomEdge") && hitBox.vel.y < 0) ||
                    (tile.getAttributes().contains("solidLeftEdge") && hitBox.vel.x > 0) ||
                    (tile.getAttributes().contains("solidRightEdge") && hitBox.vel.x < 0)) {
                        Rect r = new Rect();
                        r.pos = outerPoints.get(k).times(gameMap.getTileSize());
                        r.size = tiles.get(k).getHitBox().size;
                        tileRects.add(r);
                }
            }

            resolveTileCollision(actor, tileRects, elapsedTime);
        }

        if (actor.getHitBox().vel.x > 0) {
            actor.getHitBox().pos.x += Math.ceil(actor.getHitBox().vel.x * elapsedTime);
        }
        else if (actor.getHitBox().vel.x < 0) {
            actor.getHitBox().pos.x += Math.floor(actor.getHitBox().vel.x * elapsedTime);
        }

        if (actor.getHitBox().vel.y > 0) {
            actor.getHitBox().pos.y += Math.ceil(actor.getHitBox().vel.y * elapsedTime);
        }
        else if (actor.getHitBox().vel.y < 0) {
            actor.getHitBox().pos.y += Math.floor(actor.getHitBox().vel.y * elapsedTime);
        }
    }

    private void resolveTileCollision(Actor actor, List<Rect> sRects, double elapsedTime) {
        for (int i = 0; i < sRects.size(); i++) {
            PointDouble cp = PointDouble.of(0.0);
            PointDouble cn = PointDouble.of(0.0);
            NumWrap<Double> ct = new NumWrap<>(0.0);
            List<AbstractMap.SimpleEntry<Integer, Double>> z = new ArrayList<>();

            if (VectorMath.dynamicRectVsRect(actor.getHitBox(), elapsedTime, sRects.get(i), cp, cn, ct))
                z.add(new AbstractMap.SimpleEntry<>(i, ct.num));

            z = z.stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .collect(Collectors.toList());

            z.forEach(z1 -> VectorMath.resolveDynamicRectVsRect(actor.getHitBox(), elapsedTime, sRects.get(z1.getKey())));

        }
    }
}
