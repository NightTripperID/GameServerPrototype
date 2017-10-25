package demo.mob.enemy.boss;

import demo.mob.Mob;
import demo.mob.enemy.slime.RevengeSlime;
import demo.mob.explosion.Explosion;
import demo.tile.DemoTile;
import demo.zone.Zone;
import demo.zone.Zone_3_1;
import gamestate.GameState;

public class BossStateDying extends BossState {

    private int explosionDelay;
    private int explosionCount;

    public BossStateDying(Mob mob, GameState gameState) {
        super(mob, gameState);
    }

    @Override
    public void update() {

        if(explosionDelay++ % 5 == 0) {
            spawnExplosion();
            explosionCount++;
        }

        final int maxExplosions = 32;
        if(explosionCount == maxExplosions) {
            mob.setRemoved(true);
            ((Zone) gameState).createTextBox(0xffffff, "The dungeon is festooned with the slimy remnants of the slain beast. Suddenly the gooey bits reanimate!");
            ((Zone_3_1) gameState).lastScene = true;
            spawnRevengeSlimes();

        }

    }

    private void spawnExplosion() {
        Mob explosion = new Explosion(0xff00ff, 0, 0);
        int x = random.nextInt(mob.getWidth() - explosion.getWidth()) + (int) mob.x;
        int y = random.nextInt(mob.getHeight() - explosion.getHeight()) + (int) mob.y;
        explosion.x = x;
        explosion.y = y;
        explosion.initialize(gameState);
        gameState.addEntity(explosion);
    }

    private void spawnRevengeSlimes() {
        for(int i = 0; i < RevengeSlime.NUM_REVENGE_SLIMES; i++)
            spawnRevengeSlime();

    }

    private void spawnRevengeSlime() {
        int xTile = random.nextInt(20) + 1;
        int yTile = random.nextInt(16) + 3;
        Mob revengeSlime = new RevengeSlime(0xff00ff, xTile * DemoTile.SIZE, yTile * DemoTile.SIZE, ((Zone) gameState).getPlayer());
        revengeSlime.initialize(gameState);
        gameState.addEntity(revengeSlime);
    }
}
