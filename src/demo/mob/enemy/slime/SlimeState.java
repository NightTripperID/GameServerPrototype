package demo.mob.enemy.slime;

import demo.mob.Mob;
import demo.mob.MobState;
import gamestate.GameState;

import java.util.Random;

public abstract class SlimeState extends MobState {

    public SlimeState(Mob mob, GameState gameState) {
        super(mob, gameState);
    }
}
