package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;
import demo.projectile.Arrow;
import gamestate.GameState;
import input.Mouse;

import java.awt.event.MouseEvent;

class PlayerStateStanding extends PlayerState {

     PlayerStateStanding(@NotNull Player player, @NotNull GameState gameState) {
        super(player, gameState);
        mob.getCurrSprite().setFrame(0);
        mob.setxSpeed(0);
        mob.setySpeed(0);
    }

    PlayerStateStanding(@NotNull Player player, @NotNull GameState gameState, int count) {
        this(player, gameState);
        this.count = count;
    }

    @Override
    public MobState update() {

        super.update();

        if(keyboard.upHeld || keyboard.downHeld || keyboard.leftHeld || keyboard.rightHeld)
            return new PlayerStateWalking((Player) mob, gameState, count);

        if (Mouse.button3)
            return new PlayerStateWalking((Player) mob, gameState, count);

        return this;
    }
}