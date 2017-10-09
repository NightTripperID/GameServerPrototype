package demo.player;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import input.Mouse;

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
    public void update() {

        super.update();

        if(keyboard.upHeld || keyboard.downHeld || keyboard.leftHeld || keyboard.rightHeld)
            mob.setCurrState(new PlayerStateMoving((Player) mob, gameState, count));

        if (Mouse.button3)
            mob.setCurrState(new PlayerStateMoving((Player) mob, gameState, count));
    }
}