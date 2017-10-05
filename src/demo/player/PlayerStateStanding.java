package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.projectile.Arrow;
import input.Mouse;

import java.awt.event.MouseEvent;

public class PlayerStateStanding extends PlayerState {

    public PlayerStateStanding(@NotNull Player player) {
        super(player);
        mob.getCurrSprite().setFrame(0);
        mob.setxSpeed(0);
        mob.setySpeed(0);
    }

    @Override
    public MobState update() {

        if(keyboard.upHeld || keyboard.downHeld || keyboard.leftHeld || keyboard.rightHeld)
            return new PlayerStateWalking((Player) mob);

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

        if (Mouse.button3)
            return new PlayerStateWalking((Player) mob);

        return this;
    }
}
