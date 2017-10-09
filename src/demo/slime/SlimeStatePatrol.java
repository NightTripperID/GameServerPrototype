package demo.slime;

import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;

public class SlimeStatePatrol extends MobState {

    public SlimeStatePatrol(Mob mob, GameState gameState) {
        super(mob, gameState);
    }

    @Override
    public void update() {
        mob.getCurrSprite().update();
    }
}
