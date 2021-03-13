package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.*;
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

    private void runActorCollision(Actor actor, double elapsedTime) {
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
        List<PointDouble> outerPoints = new ArrayList<>();
        // top row of perimeter
        for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
            outerPoints.add(new PointDouble((double) x, (double) currTile_TL.y - 1));

        // bottom row of perimeter
        for (int x = currTile_TL.x - 1; x <= currTile_TR.x + 1; x++)
            outerPoints.add(new PointDouble((double) x, (double) currTile_BL.y + 1));

        // left column of perimeter
        for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
            outerPoints.add(new PointDouble((double) currTile_TL.x - 1, (double) y));

        // right column of perimeter
        for (int y = currTile_TL.y - 1; y <= currTile_BL.y + 1; y++)
            outerPoints.add(new PointDouble((double) currTile_TR.x + 1,  (double) y));

        for (int i = 1; i <= gameMap.getTileMap().getNumLayers(); i++) {
            List<Tile> tiles = new ArrayList<>();
            final int layer = i;
            outerPoints.forEach(outerPoint -> tiles.add(gameMap.getTileMap().getTile(gameMap.getTileset(), layer,
                    (int) (double) outerPoint.x, (int) (double) outerPoint.y)));

            List<Rect> tileRects = new ArrayList<>();
            for (int k = 0; k < tiles.size(); k++) {
                Tile tile = tiles.get(k);
                if (tile != null && tile.getAttributes().contains("solid")) {
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
