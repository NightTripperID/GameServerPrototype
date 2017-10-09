package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import demo.projectile.Axe;
import gamestate.GameState;
import input.Keyboard;
import input.Mouse;

abstract class PlayerState extends MobState {

    Keyboard keyboard;

    private static final int ATTACK_RATE = 1 * 30;

    int count = ATTACK_RATE;

    PlayerState(@NotNull Mob mob, @NotNull GameState gameState) {
        super(mob, gameState);
        keyboard = gameState.getKeyboard();
    }

    PlayerState(@NotNull Mob mob, @NotNull GameState gameState, int count) {
        this(mob, gameState);
        this.count = count;
    }

    @Override
    public void update() {

        if(++count < ATTACK_RATE)
            return;

        count = 0;

        if(Mouse.button1) {

            int mouseX = Mouse.mouseX + (int) gameState.getScrollX();
            int mouseY = Mouse.mouseY + (int) gameState.getScrollY();

            double dx = mouseX - mob.x;
            double dy = mouseY - mob.y;

            double angle = Math.atan2(dy, dx);

            Axe axe = new Axe(mob.x, mob.y, angle);
            axe.initialize(gameState);
            gameState.addEntity(axe);
        }
    }

    @Override
    protected void commitMove(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        if (mob.tileCollision((int) xa, (int) ya)) {
            if (mob.triggerCollision((int) xa, (int) ya))
                mob.getTileTrigger((int) xa, (int) ya).run();

        } else {
            mob.x += xa;
            mob.y += ya;
            gameState.scrollX(xa);
            gameState.scrollY(ya);
        }
    }
}
