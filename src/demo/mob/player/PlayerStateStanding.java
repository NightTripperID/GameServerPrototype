package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import gamestate.GameState;
import gamestate.Trigger;
import input.Mouse;

class PlayerStateStanding extends PlayerState {

     PlayerStateStanding(@NotNull Player player, @NotNull GameState gameState) {
        super(player, gameState);
        mob.getCurrSprite().setFrame(0);
        mob.setxSpeed(0);
        mob.setySpeed(0);
    }

    @Override
    public void update() {

        super.update();

        if (keyboard.spacePressed || keyboard.enterPressed) {
            switch (mob.direction) {
                case UP:
                    checkTrigger(0, -1);
                    break;
                case DOWN:
                    checkTrigger(0, 1);
                    break;
                case LEFT:
                    checkTrigger(-1, 0);
                    break;
                case RIGHT:
                    checkTrigger(1, 0);
                    break;
            }
        }

            if (keyboard.upHeld || keyboard.downHeld || keyboard.leftHeld || keyboard.rightHeld)
                mob.setCurrState(new PlayerStateMoving((Player) mob, gameState));
    }

    private void checkTrigger(int xa, int ya) {
        Trigger trigger;
         if (mob.triggerCollision(xa, ya)) {
            trigger = mob.getTileTrigger(xa, ya);
            if (trigger.active)
                trigger.runnable.run();
        }
    }
}