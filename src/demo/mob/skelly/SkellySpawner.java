package demo.mob.skelly;

import demo.area.Area;
import demo.mob.spawner.Spawner;
import demo.spritesheets.TileSprites;
import entity.Entity;

public class SkellySpawner extends Spawner {

    public SkellySpawner(int col, double x, double y) {
        super(col, x, y, 4 * 60, TileSprites.SKELLY);
    }

    @Override
    protected void spawn() {
        Entity skelly = new Skelly(0x00ff00, x,  y, ((Area) gameState).getPlayer());
        skelly.initialize(gameState);
        gameState.addEntity(skelly);
    }
}
