package demo.mob.treasure;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import entity.Updatable;
import graphics.AnimSprite;
import graphics.Screen;

public class Doorkey extends Mob {

    public Doorkey(double x, double y) {
        super(x, y, 1, 1, 8, 8, 1, 0, true, false);
        currSprite = new AnimSprite(SpriteSheets.DOORKEY, getWidth(), getHeight(), 1);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    @Override
    public void runCollision(Updatable updatable) {
        if(!(updatable instanceof Mob))
            throw new IllegalArgumentException("updatable must be instance of Mob");

        if(updatable instanceof Player) {
            ((Player)updatable).inventory.add("doorkey");
            this.setRemoved(true);
        }
    }
}
