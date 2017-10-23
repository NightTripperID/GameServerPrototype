package demo.mob.treasure;

import demo.area.Area;
import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import graphics.Screen;

public class Potion extends Mob {

    public Potion(int col, double x, double y) {
        super(col, x, y, 1, 1, 8, 8, 2, 0, true, false);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), Sprites.POTION);
    }

    @Override
    public void runCollision(Mob mob) {

        if(mob instanceof Player) {
            Player player = (Player) mob;
            if(player.inventory.getCount("potion") < Player.MAX_POTIONS) {
                player.inventory.add("potion");
                ((Area) gameState).setMobSpawn((int) x, (int) y, 0xff00ff);
                this.setRemoved(true);
            }
        }
    }
}
