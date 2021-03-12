package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.NumWrap;
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.PointInt;
import com.github.nighttripperid.littleengine.model.Rect;
import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.tiles.TILED_TileMap;
import com.github.nighttripperid.littleengine.model.tiles.Tile;
import com.github.nighttripperid.littleengine.staticutil.VectorMath;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollisionResolver {

    private void runEntityCollision(Entity entity, double elapsedTime) {
    }

    public void runTileCollision(Entity entity, GameMap gameMap, double elapsedTime) {
        // broad phase pass

        Rect rect = entity.getBody();
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
        // top row of perimeter
        for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
            outerPoints.add(new PointDouble((double)x, (double)currTile_TL.y - 1));


        // bottom row of perimeter
        for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
            outerPoints.add(new PointDouble((double)x, (double)currTile_BL.y + 1));


        // left column of perimeter
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

    private void resolveTileCollision(Entity entity, List<Rect> sRects, double elapsedTime) {
        for (int i = 0; i < sRects.size(); i++) {
            PointDouble cp = PointDouble.of(0.0);
            PointDouble cn = PointDouble.of(0.0);
            NumWrap<Double> ct = new NumWrap<>(0.0);
            List<AbstractMap.SimpleEntry<Integer, Double>> z = new ArrayList<>();

            if (VectorMath.dynamicRectVsRect(entity.getBody(), elapsedTime, sRects.get(i), cp, cn, ct))
                z.add(new AbstractMap.SimpleEntry<>(i, ct.num));

            z = z.stream()
                    .sorted(Map.Entry.<Integer, Double>comparingByValue().reversed())
                    .collect(Collectors.toList());

            z.forEach(z1 -> VectorMath.resolveDynamicRectVsRect(entity.getBody(), elapsedTime, sRects.get(z1.getKey())));

        }

        if (entity.getBody().vel.x > 0) {
            entity.getBody().pos.x += Math.ceil(entity.getBody().vel.x * elapsedTime);
        }
        else if (entity.getBody().vel.x < 0) {
            entity.getBody().pos.x += Math.floor(entity.getBody().vel.x * elapsedTime);
        }

        if (entity.getBody().vel.y > 0) {
            entity.getBody().pos.y += Math.ceil(entity.getBody().vel.y * elapsedTime);
        }
        else if (entity.getBody().vel.y < 0) {
            entity.getBody().pos.y += Math.floor(entity.getBody().vel.y * elapsedTime);
        }
    }
}
