package demo.mob.enemy.slime;

import demo.audio.Sfx;
import demo.zone.Zone;
import demo.mob.Spawner;
import demo.spritesheets.SpriteSheets;
import entity.Entity;
import graphics.AnimSprite;

import java.util.Random;

public class SlimeSpawner extends Spawner {

    public SlimeSpawner(int col, double x, double y) {
        super(col, x, y, 0, new AnimSprite(SpriteSheets.SLIME_SPAWNER, 16, 16, 2, 35));
        countMax = (random.nextInt(3) + 4) * 60;
        currSprite.setFrameRate(random.nextInt(5) + 20);
    }

    public void spawn() {
        Entity slime = new Slime(0x00ff00, x,  y, ((Zone) gameState).getPlayer());
        slime.initialize(gameState);
        gameState.addEntity(slime);
        Sfx.SQUISH.play();
    }
}
