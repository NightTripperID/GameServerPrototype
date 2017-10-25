package demo.mob.enemy.medusa;

import com.sun.istack.internal.NotNull;
import demo.mob.enemy.Enemy;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import demo.tile.Tiles;
import graphics.AnimSprite;
import graphics.Screen;

import java.awt.*;

public class Medusa extends Enemy {

    public Medusa(int col, double x, double y, @NotNull Player player) {
        super(col, x, y, 1, 1, 16, 16, 2, 1, true);
        currState = new MedusaStatePatrol(this, gameState, player);
        currSprite = new AnimSprite(SpriteSheets.MEDUSA_DOWN, 16, 16, 2);
        currSprite.setFrameRate(20);
    }

    @Override
    public boolean tileCollision(int xa, int ya) {
        for (int corner = 0; corner < 4; corner++) {
            Point p = getTileCorner(xa, ya, corner);
            if (gameState.getMapTileObject(p.x, p.y) == Tiles.voidTile)
                return true;
        }
        return false;
    }
}
