package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.MobState;

import java.awt.event.MouseEvent;

public class PlayerStateWalking extends PlayerState {

    private static final double WALK_SPEED = 1.3;

    private MouseEvent mouseEvent;

    public PlayerStateWalking(@NotNull Player player) {
        super(player);
    }

    public PlayerStateWalking(@NotNull Player player, @NotNull MouseEvent mouseEvent) {
        super(player);
        this.mouseEvent = mouseEvent;
    }

    @Override
    public MobState update() {

        mob.getCurrSprite().update();

        mob.setxSpeed(0);
        mob.setySpeed(0);


        if (mouseEvent != null) {

            doMouseLogic();

        } else {

            if (!keyboard.upHeld && !keyboard.downHeld && !keyboard.leftHeld &&!keyboard.rightHeld)
                return new PlayerStateStanding((Player) mob);

            doKeyLogic();
        }

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        mob.x += mob.xa;
        mob.y += mob.ya;

        return this;
    }

    @Override
    public MobState mousePressed(MouseEvent e) {
        mouseEvent = e;
        return this;
    }

    @Override
    public MobState mouseDragged(MouseEvent e) {
        mouseEvent = e;
        return this;
    }

    @Override
    public MobState mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON3)
            return new PlayerStateStanding((Player) mob);

        return this;
    }

    private void doMouseLogic() {
        int screenScale = mob.getGameState().getScreenScale();

        int b3 = MouseEvent.BUTTON3_DOWN_MASK;

        int mouseX = mouseEvent.getX() / screenScale;
        int mouseY = mouseEvent.getY() / screenScale;

        int xCenter = (int) mob.x + mob.getWidth();
        int yCenter = (int) mob.y + mob.getHeight();

        if ((mouseEvent.getModifiersEx() & b3) == b3) {
            if (mouseY < yCenter - 1)
                walkUp();
            if (mouseY > yCenter + 1)
                walkDown();
            if (mouseX < xCenter - 1)
                walkLeft();
            if (mouseX > xCenter + 1)
                walkRight();
        }
    }

    private void doKeyLogic() {

        if (keyboard.upHeld)
            walkUp();

        if (keyboard.downHeld)
            walkDown();

        if (keyboard.leftHeld)
            walkLeft();

        if (keyboard.rightHeld)
            walkRight();
    }

    private void walkUp() {
        mob.setCurrSprite(PlayerState.PLAYER_UP);
        mob.setySpeed(WALK_SPEED);
        mob.setyDir(-1);
    }

    private void walkDown() {
        mob.setCurrSprite(PlayerState.PLAYER_DOWN);
        mob.setySpeed(WALK_SPEED);
        mob.setyDir(1);
    }

    private void walkLeft() {
        mob.setCurrSprite(PlayerState.PLAYER_LEFT);
        mob.setxSpeed(WALK_SPEED);
        mob.setxDir(-1);
    }

    private void walkRight() {
        mob.setCurrSprite(PlayerState.PLAYER_RIGHT);
        mob.setxSpeed(WALK_SPEED);
        mob.setxDir(1);
    }
}
