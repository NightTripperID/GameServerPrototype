package demo.mob.enemy.boss;

import demo.mob.enemy.Enemy;
import gamestate.GameState;
import graphics.Screen;

public class Boss extends Enemy {

    public Boss(int col, double x, double y) {
        super(col, x, y, -1, 1, 32, 32, 40, 2, true);
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        currState = new BossStateBouncing(this, gameState);
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
    }

    @Override
    protected void die() {
        if (!(currState instanceof  BossStateDying))
            currState = new BossStateDying(this, gameState);
    }

    @Override
    protected void dropItem() {
        // drop nothing
    }
}
