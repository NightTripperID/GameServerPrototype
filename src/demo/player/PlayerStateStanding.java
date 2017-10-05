package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.projectile.Arrow;

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

        return this;
    }

    @Override
    public MobState mousePressed(MouseEvent e) {

        if(e.getButton() == MouseEvent.BUTTON1) {

            int screenScale = mob.getGameState().getScreenScale();

            int mouseX = e.getX() / screenScale;
            int mouseY = e.getY() / screenScale;

            double dx = mob.x - mouseX;
            double dy = mob.y - mouseY;

            double angle = Math.atan2(dy, dx);

            Arrow arrow = new Arrow(mob.x, mob.y, angle);
            arrow.initialize(mob.getGameState());
            mob.getGameState().addEntity(arrow);

            System.out.println(angle);
        }

        if(e.getButton() == MouseEvent.BUTTON3)
            return new PlayerStateWalking((Player) mob, e);
        return this;
    }
}
