package demo.mob.item;

import demo.zone.Zone;
import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import graphics.Screen;

public class Doorkey extends Mob {

    public Doorkey(int col, double x, double y) {
        super(col, x, y, 1, 1, 8, 8, 1, 0, true, false);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), Sprites.YELLOW_DOORKEY);
    }

    @Override
    public void runCollision(Mob mob) {
        if(mob instanceof Player) {
            ((Player)mob).inventory.add("doorkey");
            ((Zone) gameState).setMobSpawn((int) x, (int) y, 0xff00ff);
            this.setRemoved(true);
        }
    }
}
