package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.projectile.Arrow;
import input.Mouse;

import java.awt.event.MouseEvent;

class PlayerStateStanding extends PlayerState {

     PlayerStateStanding(@NotNull Player player) {
        super(player);
        mob.getCurrSprite().setFrame(0);
        mob.setxSpeed(0);
        mob.setySpeed(0);
    }

    PlayerStateStanding(@NotNull Player player, int count) {
        this(player);
        this.count = count;
    }

    @Override
    public MobState update() {

        super.update();

        if(keyboard.upHeld || keyboard.downHeld || keyboard.leftHeld || keyboard.rightHeld)
            return new PlayerStateWalking((Player) mob, count);

        if (Mouse.button3)
            return new PlayerStateWalking((Player) mob, count);

        return this;
    }
}