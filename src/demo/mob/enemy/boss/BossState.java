package demo.mob.enemy.boss;

import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;

public abstract class BossState extends MobState {

    public BossState(Mob mob, GameState gameState) {
        super(mob, gameState);
    }
}
