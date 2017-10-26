package demo.mob.item;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import graphics.Screen;

public class Potion extends Item {

    public Potion(int col, double x, double y) {
        super(col, x, y,  8, 8);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), Sprites.POTION);
    }

    @Override
    public void runCollision(Mob mob) {
        if(mob instanceof Player)
            if (((Player) mob).inventory.getCount("potion") < 4)
                addToInventory(mob, "potion");
    }
}
