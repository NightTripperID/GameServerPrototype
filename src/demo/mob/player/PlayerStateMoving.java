package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;

class PlayerStateMoving extends PlayerState {

    private static final double MOVE_SPEED = 1.0;

    private final AnimSprite playerSpriteUp = new AnimSprite(SpriteSheets.PLAYER_UP, 16, 16, 4);
    private final AnimSprite playerSpriteDown = new AnimSprite(SpriteSheets.PLAYER_DOWN, 16, 16, 4);
    private final AnimSprite playerSpriteLeft = new AnimSprite(SpriteSheets.PLAYER_LEFT, 16, 16, 4);
    private final AnimSprite playerSpriteRight = new AnimSprite(SpriteSheets.PLAYER_RIGHT, 16, 16, 4);

    PlayerStateMoving(@NotNull Player player, @NotNull GameState gameState) {
        super(player, gameState);
    }

    @Override
    public void update() {

        super.update();

        mob.getCurrSprite().update();

        mob.setxSpeed(0);
        mob.setySpeed(0);

        if (!moveWithKeyboard())
            mob.setCurrState(new PlayerStateStanding((Player) mob, gameState));

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);
    }

    private boolean moveWithKeyboard() {

        if (!keyboard.upHeld && !keyboard.downHeld && !keyboard.leftHeld && !keyboard.rightHeld)
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
        mob.setCurrSprite(playerSpriteUp);
        mob.setySpeed(MOVE_SPEED);
        mob.setyDir(-1);
        mob.direction = Mob.Direction.UP;
    }

    private void moveDown() {
        mob.setCurrSprite(playerSpriteDown);
        mob.setySpeed(MOVE_SPEED);
        mob.setyDir(1);
        mob.direction = Mob.Direction.DOWN;
    }

    private void moveLeft() {
        mob.setCurrSprite(playerSpriteLeft);
        mob.setxSpeed(MOVE_SPEED);
        mob.setxDir(-1);
        mob.direction = Mob.Direction.LEFT;
    }

    private void moveRight() {
        mob.setCurrSprite(playerSpriteRight);
        mob.setxSpeed(MOVE_SPEED);
        mob.setxDir(1);
        mob.direction = Mob.Direction.RIGHT;
    }
}
