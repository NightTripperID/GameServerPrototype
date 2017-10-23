package demo.mob.enemy.skelly;

import demo.area.Area;
import demo.mob.Spawner;
import demo.spritesheets.Sprites;
import entity.Entity;

public class SkellySpawner extends Spawner {

    public SkellySpawner(int col, double x, double y) {
        super(col, x, y, 4 * 60, Sprites.SKELLY);
    }

    @Override
    protected void spawn() {
        Entity skelly = new Skelly(0x00ff00, x,  y, ((Area) gameState).getPlayer());
        skelly.initialize(gameState);
        gameState.addEntity(skelly);
    }
}
