package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import demo.projectile.Arrow;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import input.Keyboard;
import input.Mouse;

abstract class PlayerState extends MobState {

    Keyboard keyboard;

    private static final int ATTACK_RATE = 1 * 30;

    int count = ATTACK_RATE;

    PlayerState(@NotNull Mob mob) {
        super(mob);
        keyboard = mob.getGameState().getKeyboard();
    }

    PlayerState(@NotNull Mob mob, int count) {
        this(mob);
        this.count = count;
    }

    @Override
    public MobState update() {

        if(++count < ATTACK_RATE)
            return this;

        count = 0;

        if(Mouse.button1) {
            int screenScale = mob.getGameState().getScreenScale();

            int mouseX = Mouse.mouseX / screenScale;
            int mouseY = Mouse.mouseY / screenScale;

            double dx = mouseX - mob.x;
            double dy = mouseY - mob.y;

            double angle = Math.atan2(dy, dx);

            Arrow arrow = new Arrow(mob.x, mob.y, angle);
            arrow.initialize(mob.getGameState());
            mob.getGameState().addEntity(arrow);
        }
        return this;
    }
}
