package demo.mob.treasure;

import demo.area.Area_1;
import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;

public class Doorkey extends Mob {

    public Doorkey(int col, double x, double y) {
        super(col, x, y, 1, 1, 8, 8, 1, 0, true, false);
        currSprite = new AnimSprite(SpriteSheets.DOORKEY, getWidth(), getHeight(), 1);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    @Override
    public void runCollision(Mob mob) {
        if(mob instanceof Player) {
            ((Player)mob).inventory.add("doorkey");
            ((Area_1)gameState).setMobSpawn((int) x, (int) y, 0xff00ff);
            this.setRemoved(true);
        }
    }
}
