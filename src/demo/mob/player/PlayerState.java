package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.mob.magic.Fireball;
import demo.mob.projectile.axe.Axe;
import demo.audio.Sfx;
import entity.Entity;
import gamestate.GameState;
import gamestate.Trigger;
import input.Keyboard;
import input.Mouse;

abstract class PlayerState extends MobState {

    Keyboard keyboard;

    private static final int ATTACK_RATE = 1 * 30;
    private static int attackCount = ATTACK_RATE;

    private static final int MAGIC_RATE = 2 * 30;
    private static int magicCount = MAGIC_RATE;

    PlayerState(@NotNull Player mob, @NotNull GameState gameState) {
        super(mob, gameState);
        keyboard = gameState.getKeyboard();
    }

    @Override
    public void update() {

        if (++magicCount > MAGIC_RATE)
            magicCount = MAGIC_RATE;

        if (Mouse.button3Pressed) {

            if (magicCount < MAGIC_RATE)
                return;
            magicCount = 0;

            Player player = (Player) mob;
            int potionCount = player.inventory.getCount("potion");

            switch (potionCount) {
                case 1:
                    castMagic_1();
                    break;
                case 2:
                    castMagic_2();
                    break;
                case 3:
                    castMagic_3();
                    break;
                case 4:
                    castMagic_4();
                    break;
                default:
                    break;
            }
        }

        if (++attackCount > ATTACK_RATE)
            attackCount = ATTACK_RATE;

        if (Mouse.button1Pressed) {
            if (attackCount < ATTACK_RATE / 2)
                return;
            attackCount = 0;
            throwAxe();
        }

        if (Mouse.button1Held) {
            if (attackCount < ATTACK_RATE)
                return;
            attackCount = 0;
            throwAxe();
        }

        if (Mouse.button2Pressed || keyboard.cPressed)
            drinkPotion();
    }

    private void drinkPotion() {
        if (mob.getHealth() < Player.MAX_HEALTH)
            if (((Player) mob).inventory.remove("potion")) {
                mob.addHealth(1);
                Sfx.DRINK_POTION.play();
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

        Sfx.AXE_THROW.play();
    }

    private void castMagic_1() {
        spawnFireball(mob.x, mob.y);
        ((Player) mob).inventory.remove("potion");
    }

    private void castMagic_2() {
        spawnFireball(mob.x, mob.y - mob.getHeight());
        spawnFireball(mob.x, mob.y + mob.getHeight());
        ((Player) mob).inventory.remove("potion");
        ((Player) mob).inventory.remove("potion");
    }

    private void castMagic_3() {
        Player player = (Player) mob;
        spawnFireball(mob.x, mob.y - mob.getHeight());
        spawnFireball(mob.x - mob.getWidth(), mob.y + mob.getHeight());
        spawnFireball(mob.x + mob.getWidth(), mob.y + mob.getHeight());
        player.inventory.remove("potion");
        player.inventory.remove("potion");
        player.inventory.remove("potion");
    }

    private void castMagic_4() {
        Player player = (Player) mob;
        spawnFireball(mob.x - mob.getWidth(), mob.y - mob.getHeight());
        spawnFireball(mob.x - mob.getWidth(), mob.y + mob.getHeight());
        spawnFireball(mob.x + mob.getWidth(), mob.y - mob.getHeight());
        spawnFireball(mob.x + mob.getWidth(), mob.y + mob.getHeight());
        player.inventory.remove("potion");
        player.inventory.remove("potion");
        player.inventory.remove("potion");
        player.inventory.remove("potion");
    }

    private void spawnFireball(double x, double y) {
        Entity fireball = new Fireball(x, y, mob);
        fireball.initialize(gameState);
        gameState.addEntity(fireball);
        Sfx.FLAME.play();
    }

    @Override
    protected void commitMove(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        Trigger trigger;
        if (mob.triggerCollision((int) xa, (int) ya)) {
            trigger = mob.getTileTrigger((int) xa, (int) ya);
            if (!trigger.active)
                trigger.runnable.run();
            else if (gameState.getKeyboard().spacePressed)
                trigger.runnable.run();
        }

        if (!mob.tileCollision((int) xa, (int) ya)) {
            mob.x += xa;
            mob.y += ya;
            gameState.scrollX(xa);
            gameState.scrollY(ya);
        } else {
            mob.xa = 0;
            mob.ya = 0;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}