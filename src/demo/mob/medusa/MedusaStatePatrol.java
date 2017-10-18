package demo.mob.medusa;

import demo.mob.Mob;
import demo.mob.MobState;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import demo.tile.DemoTile;
import gamestate.GameState;
import graphics.AnimSprite;

import java.awt.*;
import java.util.Random;

public class MedusaStatePatrol extends MedusaState {

    private int count;

    MedusaStatePatrol(Mob mob, GameState gameState, Player player) {
        super(mob, gameState, player);
        mob.setxSpeed(0);
        mob.setySpeed(0);
    }

    @Override
    public void update() {
        mob.getCurrSprite().update();

        if(count++ == 60 * 2) {
            count = 0;

            if(mob.getxSpeed() > 0 || mob.getySpeed() > 0) {
                mob.setxSpeed(0);
                mob.setySpeed(0);
            } else {
                mob.setxSpeed(random.nextInt() > 0 ? 1 : 0);
                if(mob.getxSpeed() == 0)
                    mob.setySpeed(1);
                mob.setxDir(random.nextInt() > 0 ? 1 : -1);
                mob.setyDir(random.nextInt() > 0 ? 1 : -1);
            }
        }

        if(mob.x > player.x && mob.x < player.x + player.getWidth() ||
                mob.y > player.y && mob.y < player.y + player.getHeight())
            mob.setCurrState(new MedusaStateCharge(mob, gameState, player));

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        if(mob.ya == -1)
            mob.setCurrSprite(medusaUp);
        else
            mob.setCurrSprite(medusaDown);

        commitMove(mob.xa, mob.ya);
    }
}
