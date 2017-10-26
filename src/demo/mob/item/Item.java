package demo.mob.item;

import demo.audio.Sfx;
import demo.mob.Mob;
import demo.mob.player.Player;

public abstract class Item extends Mob {

    public Item(int col, double x, double y, int width, int height) {
        super(col, x, y, 1, 1, width, height, 1, 0, true, false);
    }

    protected void addToInventory(Mob mob, String name) {
        if(mob instanceof Player) {
            ((Player) mob).inventory.add(name);
            this.setRemoved(true);
            Sfx.GRAB_ITEM.play();
        }
    }
}
