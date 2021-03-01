package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.gamestate.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.PointInt;

public class CollisionResolver {

    public static boolean resolveTileCollision(GameMap gameMap, Entity entity, double xSpeed, double ySpeed) {
        for (int corner = 0; corner < 4; corner++) {

            for (int x = 1; x <= Math.abs(xSpeed); x++) {
                PointInt p = getTileCorner(entity, x * entity.direction.x, 0, corner);
                for(Integer [] mapTiles : gameMap.getMapTileHashMap().values()) {
                    if (gameMap.getMapTileObject(mapTiles, p.x, p.y).isSolid()) {
                        return true;
                    }
                }
            }

            for (int y = 1; y <= Math.abs(ySpeed); y++) {
                PointInt p = getTileCorner(entity,0, y * entity.direction.y, corner);
                for(Integer [] mapTiles : gameMap.getMapTileHashMap().values()) {
                    if (gameMap.getMapTileObject(mapTiles, p.x, p.y).isSolid()) {
                        return true;
                    }
                }
            }
        }
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
