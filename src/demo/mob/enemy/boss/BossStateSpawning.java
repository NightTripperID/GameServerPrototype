package demo.mob.enemy.boss;

import demo.audio.Sfx;
import demo.mob.Mob;
import demo.mob.enemy.slime.Slime;
import demo.zone.Zone;
import gamestate.GameState;

public class BossStateSpawning extends BossState {

    private int spawnCount;
    private int spawnDelay;

    public BossStateSpawning(Boss mob, GameState gameState) {
        super(mob, gameState);
    }

    @Override
    public void update() {
        if ((spawnDelay++) % 16 == 0) {
            if ((spawnCount += spawnSlime()) > 5)
                mob.setCurrState(new BossStateBouncing((Boss) mob, gameState));
        }
    }

    private int spawnSlime() {
        Mob slime = new Slime(0x00ff00, 0, mob.y, ((Zone) gameState).getPlayer());
        int xOfs = ((int) mob.x + mob.getWidth() / 2) - (slime.getWidth() / 2);
        slime.x = xOfs;
        slime.initialize(gameState);
        gameState.addEntity(slime);
        Sfx.SQUISH.play();
        return 1;
    }
}
