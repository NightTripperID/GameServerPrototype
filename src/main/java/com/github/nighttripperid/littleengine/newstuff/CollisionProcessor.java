package com.github.nighttripperid.littleengine.newstuff;

import java.awt.*;

public class CollisionProcessor {

    public static boolean processTileCollision(GameMap gameMap, Entity entity, int xa, int ya) {
        for (int corner = 0; corner < 4; corner++) {

            for (int x = 1; x <= Math.abs(xa); x++) {
                Point p = getTileCorner(entity, x * (int) entity.getXDir(), 0, corner);
                for(Integer [] mapTiles : gameMap.getMapTileHashMap().values()) {
                    if (gameMap.getMapTileObject(mapTiles, p.x, p.y).isSolid()) {
                        return true;
                    }
                }
            }

            for (int y = 1; y <= Math.abs(ya); y++) {
                Point p = getTileCorner(entity,0, y * (int) entity.getYDir(), corner);
                for(Integer [] mapTiles : gameMap.getMapTileHashMap().values()) {
                    if (gameMap.getMapTileObject(mapTiles, p.x, p.y).isSolid()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static Point getTileCorner(Entity entity, int xa, int ya, int corner) {
        int xt = ((int) (entity.getXPos() + xa) + corner % 2 * 2 + 6) / 16;
        int yt = ((int) (entity.getYPos() + ya) + corner / 2 * 2 + 14) / 16;
        return new Point(xt, yt);
    }
}
