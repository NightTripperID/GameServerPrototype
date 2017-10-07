package demo.player;

import com.sun.istack.internal.NotNull;
import demo.level.Level;
import demo.mob.MobState;
import demo.spritesheets.PlayerSprites;
import gamestate.GameState;
import input.Mouse;

class PlayerStateWalking extends PlayerState {

    private static final double MOVE_SPEED = 1.0;

    PlayerStateWalking(@NotNull Player player, @NotNull GameState gameState, int count) {
        super(player, gameState, count);
    }

    @Override
    public MobState update() {

        super.update();

        mob.getCurrSprite().update();

        mob.setxSpeed(0);
        mob.setySpeed(0);

        MobState nextState;

        if(!moveWithMouse() && !moveWithKeyboard())
            nextState = new PlayerStateStanding((Player) mob, gameState, count);
        else
            nextState = this;

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);

        return nextState;
    }

    private boolean moveWithMouse() {

        if(!Mouse.button3)
            return false;

        int mouseX = Mouse.mouseX + (int) gameState.getScrollX();
        int mouseY = Mouse.mouseY + (int) gameState.getScrollY();

        int xCenter = (int) mob.x + mob.getWidth() / 2;
        int yCenter = (int) mob.y + mob.getHeight() / 2;

        if (mouseY < yCenter - 1)
            moveUp();
        if (mouseY > yCenter + 1)
            moveDown();
        if (mouseX < xCenter - 1)
            moveLeft();
        if (mouseX > xCenter + 1)
            moveRight();

        return true;
    }

    private boolean moveWithKeyboard() {

        if(!keyboard.upHeld && !keyboard.downHeld && !keyboard.leftHeld && !keyboard.rightHeld)
            return false;

        if (keyboard.upHeld)
            moveUp();

        if (keyboard.downHeld)
            moveDown();

        if (keyboard.leftHeld)
            moveLeft();

        if (keyboard.rightHeld)
            moveRight();

        return true;
    }

    private void moveUp() {
        mob.setCurrSprite(PlayerSprites.PLAYER_UP);
        mob.setySpeed(MOVE_SPEED);
        mob.setyDir(-1);
    }

    private void moveDown() {
        mob.setCurrSprite(PlayerSprites.PLAYER_DOWN);
        mob.setySpeed(MOVE_SPEED);
        mob.setyDir(1);
    }

    private void moveLeft() {
        mob.setCurrSprite(PlayerSprites.PLAYER_LEFT);
        mob.setxSpeed(MOVE_SPEED);
        mob.setxDir(-1);
    }

    private void moveRight() {
        mob.setCurrSprite(PlayerSprites.PLAYER_RIGHT);
        mob.setxSpeed(MOVE_SPEED);
        mob.setxDir(1);
    }

    private void commitMove(double xa, double ya) {
        if(xa !=0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        if(!mob.tileCollision((int) xa, (int) ya)) {
            mob.x += xa;
            mob.y += ya;
            gameState.scrollX(xa);
            gameState.scrollY(ya);
        }
    }
}
