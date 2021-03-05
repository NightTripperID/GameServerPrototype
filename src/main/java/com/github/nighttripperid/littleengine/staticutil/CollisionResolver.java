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
package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.gamestate.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.PointInt;

public class CollisionResolver {

    public static boolean resolveTileCollision(GameMap gameMap, Entity entity, double xSpeed, double ySpeed) {
//        for (int corner = 0; corner < 4; corner++) {
//
//            for (int x = 1; x <= Math.abs(xSpeed); x++) {
//                PointInt p = getTileCorner(entity, x * entity.direction.x, 0, corner);
//
//                for(int layerId : gameMap.getTileMap().keySet()) {
//                    if (gameMap.getMapTileObject(layerId, p.x, p.y).isSolid()) {
//                        return true;
//                    }
//                }
//            }
//
//            for (int y = 1; y <= Math.abs(ySpeed); y++) {
//                PointInt p = getTileCorner(entity,0, y * entity.direction.y, corner);
//                for(int layerId : gameMap.getTileMap().keySet()) {
//                    if (gameMap.getMapTileObject(layerId, p.x, p.y).isSolid()) {
//                        return true;
//                    }
//                }
//            }
//        }
        return false;
    }

    private static PointInt getTileCorner(Entity entity, int xa, int ya, int corner) {
        int xPos = xa > 1 ? (int) entity.getPosition().x + entity.getSprite().width : (int) entity.getPosition().x;
        int yPos = ya > 1 ? (int) entity.getPosition().y + entity.getSprite().height : (int) entity.getPosition().y;
        int cornerX = ((xPos + xa) + corner % 2) / entity.getSprite().width;
        int cornerY = ((yPos + ya) + corner / 2) / entity.getSprite().height;
//        int xt = ((int) (entity.getXPos() + xa) + corner % 2 * 2 + 6) / 16;
//        int yt = ((int) (entity.getYPos() + ya) + corner / 2 * 2 + 14) / 16;
        return new PointInt(cornerX, cornerY);

    }
}
