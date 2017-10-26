package demo.mob.item;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import graphics.Screen;

public class Doorkey extends Item {

    public Doorkey(int col, double x, double y) {
        super(col, x, y, 8, 8);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), Sprites.YELLOW_DOORKEY);
    }

    @Override
    public void runCollision(Mob mob) {
        addToInventory(mob, "doorkey");
    }
}
