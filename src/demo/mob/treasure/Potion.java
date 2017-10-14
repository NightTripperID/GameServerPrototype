package demo.mob.treasure;

import demo.area.Area_1;
import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import entity.Updatable;
import graphics.AnimSprite;
import graphics.Screen;

public class Potion extends Mob {

    public Potion(double x, double y) {
        super(x, y, 1, 1, 8, 8, 2, 0, true, false);

        currSprite = new AnimSprite(SpriteSheets.POTION, getWidth(), getHeight(), 1);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    @Override
    public void runCollision(Mob mob) {

        if(mob instanceof Player) {
            Player player = (Player) mob;
            if(player.inventory.getCount("potion") < Player.MAX_POTIONS) {
                player.inventory.add("potion");
                ((Area_1) gameState).setMobSpawn((int) x, (int) y, 0xff00ff);
                this.setRemoved(true);
            }
        }
    }
}
