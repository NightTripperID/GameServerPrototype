package demo.mob.enemy.slime;

import demo.area.Area;
import demo.mob.Spawner;
import demo.spritesheets.SpriteSheets;
import entity.Entity;
import graphics.AnimSprite;

public class SlimeSpawner extends Spawner {

    public SlimeSpawner(int col, double x, double y) {
        super(col, x, y, 4 * 60, new AnimSprite(SpriteSheets.SLIME_SPAWNER, 16, 16, 2), 35);
    }

    public void spawn() {
        Entity slime = new Slime(0x00ff00, x,  y, ((Area) gameState).getPlayer());
        slime.initialize(gameState);
        gameState.addEntity(slime);
    }
}
