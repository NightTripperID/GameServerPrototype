package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;

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
        if(e.getButton() == MouseEvent.BUTTON3)
            return new PlayerStateWalking((Player) mob, e);
        return this;
    }
}
