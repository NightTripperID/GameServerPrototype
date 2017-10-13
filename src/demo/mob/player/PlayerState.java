package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.mob.projectile.Axe;
import entity.Entity;
import gamestate.GameState;
import input.Keyboard;
import input.Mouse;

abstract class PlayerState extends MobState {

    Keyboard keyboard;

    private static final int ATTACK_RATE = 1 * 30;

    static int attackCount = ATTACK_RATE;

    private static int chargeStep;


    PlayerState(@NotNull Player mob, @NotNull GameState gameState) {
        super(mob, gameState);
        keyboard = gameState.getKeyboard();
    }

    @Override
    public void update() {

        if(Mouse.button3Held) {
            System.out.println(chargeStep + ", " + chargeStep % 3);
            if (chargeStep++ % 3 == 2) {
                ((Player) mob).addCharge(3);
                chargeStep = 0;
            }
        }

        if(Mouse.button3Released)
            ((Player) mob).spendCharge();

        if (++attackCount > ATTACK_RATE)
            attackCount = ATTACK_RATE;

        if(Mouse.button1Pressed) {
            if(attackCount < ATTACK_RATE / 2)
                return;
            attackCount = 0;
            throwAxe();
        }

        if(Mouse.button1Held) {
            if (attackCount < ATTACK_RATE)
                return;
            attackCount = 0;
            throwAxe();
        }

    }

    private void throwAxe() {
        int mouseX = Mouse.mouseX + (int) gameState.getScrollX();
        int mouseY = Mouse.mouseY + (int) gameState.getScrollY();

        double dx = mouseX - mob.x;
        double dy = mouseY - mob.y;

        double angle = Math.atan2(dy, dx);

        Entity axe = new Axe(mob.x, mob.y, angle);
        axe.initialize(gameState);
        gameState.addEntity(axe);
    }

    @Override
    protected void commitMove(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        if (mob.triggerCollision((int) xa, (int) ya))
            mob.getTileTrigger((int) xa, (int) ya).run();

        if (!mob.tileCollision((int) xa, (int) ya)) {
            mob.x += xa;
            mob.y += ya;
            gameState.scrollX(xa);
            gameState.scrollY(ya);
        }
    }
}
