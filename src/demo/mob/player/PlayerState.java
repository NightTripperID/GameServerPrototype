package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.mob.magic.Magic_1;
import demo.mob.projectile.Axe;
import entity.Entity;
import gamestate.GameState;
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

        if(Mouse.button3Pressed) {

            if(magicCount < MAGIC_RATE)
                return;
            magicCount = 0;

            Player player = (Player) mob;
            int potionCount = player.inventory.getCount("potion");

            switch(potionCount) {
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
                    System.out.println("no potions!");
            }
        }

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

        if(Mouse.button2Pressed || keyboard.cPressed) {
            if(mob.getHealth() < Player.MAX_HEALTH)
                if (((Player) mob).inventory.remove("potion"))
                    mob.addHealth(1);
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

    private void castMagic_1() {
        System.out.println("casting magic 1");
        spawnFireball(mob.x, mob.y);
        ((Player) mob).inventory.remove("potion");
    }

    private void castMagic_2() {
        System.out.println("casting magic 2");
        spawnFireball(mob.x, mob.y - mob.getHeight());
        spawnFireball(mob.x, mob.y + mob.getHeight());
        ((Player) mob).inventory.remove("potion");
        ((Player) mob).inventory.remove("potion");
    }

    private void castMagic_3() {
        Player player = (Player) mob;
        System.out.println("casting magic 3");
        spawnFireball(mob.x, mob.y - mob.getHeight());
        spawnFireball(mob.x - mob.getWidth(), mob.y + mob.getHeight());
        spawnFireball(mob.x + mob.getWidth(), mob.y + mob.getHeight());
        player.inventory.remove("potion");
        player.inventory.remove("potion");
        player.inventory.remove("potion");
    }

    private void castMagic_4() {
        Player player = (Player) mob;
        System.out.println("casting magic 4");
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
        Entity fireball = new Magic_1(x, y, mob);
        fireball.initialize(gameState);
        gameState.addEntity(fireball);
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
        } else {
            mob.xa = 0;
            mob.ya = 0;
        }
    }
}
