package demo.mob.enemy.medusa;

import demo.zone.Zone;
import demo.mob.Spawner;
import demo.spritesheets.SpriteSheets;
import entity.Entity;
import graphics.AnimSprite;

public class MedusaSpawner extends Spawner {

    public MedusaSpawner(int col, double x, double y) {
        super(col, x, y, 4 * 60, new AnimSprite(SpriteSheets.MEDUSA_SPAWNER, 16, 16, 1), 35);
    }

    @Override
    protected void spawn() {
        Entity medusa = new Medusa(0x00ff00, x,  y, ((Zone) gameState).getPlayer());
        medusa.initialize(gameState);
        gameState.addEntity(medusa);
    }
}
