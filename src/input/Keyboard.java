package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Object that listens to keys for keyboard input handling.
 * @author Noah Dering
 *
 */
public class Keyboard implements KeyListener {

    public boolean[] keys = new boolean[120];

    public boolean upHeld, downHeld, leftHeld, rightHeld, enterHeld, spaceHeld, escHeld;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, spacePressed, escPressed;
    public boolean upReleased, downReleased, leftReleased, rightReleased, enterReleased, spaceReleased, escReleased;


    public void update() {

        boolean upLast, downLast, leftLast, rightLast, enterLast, spaceLast, escLast;

        upLast    = upHeld;
        downLast  = downHeld;
        leftLast  = leftHeld;
        rightLast = rightHeld;
        enterLast = enterHeld;
        spaceLast = spaceHeld;
        escLast   = escHeld;

        upHeld    = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        downHeld  = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        leftHeld  = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        rightHeld = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
        enterHeld = keys[KeyEvent.VK_ENTER];
        spaceHeld = keys[KeyEvent.VK_SPACE];
        escHeld   = keys[KeyEvent.VK_ESCAPE];

        upPressed    = upHeld && !upLast;
        downPressed  = downHeld && !downLast;
        leftPressed  = leftHeld && !leftLast;
        rightPressed = rightHeld && !rightLast;
        enterPressed = enterHeld && !enterLast;
        spacePressed = spaceHeld && !spaceLast;
        escPressed   = escHeld && !escLast;

        upReleased    = !upHeld && upLast;
        downReleased  = !downHeld && downLast;
        leftReleased  = !leftHeld && leftLast;
        rightReleased = !rightHeld && rightLast;
        enterReleased = !enterHeld && enterLast;
        spaceReleased = !spaceHeld && spaceLast;
        escReleased   = !escHeld && escLast;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keys[keyEvent.getKeyCode()] = false;
    }
}
