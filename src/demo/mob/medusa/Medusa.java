package demo.mob.medusa;

import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import demo.tile.Tiles;
import graphics.AnimSprite;
import graphics.Screen;

import java.awt.*;

public class Medusa extends Mob {

    public Medusa(double x, double y) {
        super(x, y, 1, 1, 16, 16, 2, 1, false, true);
        currState = new MedusaStatePatrol(this, gameState);
        currSprite = new AnimSprite(SpriteSheets.MEDUSA_DOWN, 16, 16, 2);
        currSprite.setFrameRate(20);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
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
