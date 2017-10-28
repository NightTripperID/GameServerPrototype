package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Object that listens to keys for keyboard input handling.
 *
 */
public class Keyboard implements KeyListener {

    private static boolean[] keys = new boolean[120];
    private static boolean[] keysLast = new boolean[120];
    private static boolean[] keysHeld = new boolean[120];
    private static boolean[] keysPressed = new boolean[120];
    private static boolean[] keysReleased = new boolean[120];

    /**
     * Updates the state of the keys on the keyboard.
     */
    public void update() {
        System.arraycopy(keysHeld, 0, keysLast, 0, keysHeld.length);
        System.arraycopy(keys, 0, keysHeld, 0, keys.length);

        for (int i = 0; i < keysHeld.length; i++) {
            keysPressed[i] = keysHeld[i] && !keysLast[i];
            keysReleased[i] = !keysHeld[i] && keysLast[i];
        }
    }

    /**
     * Returns whether the given key is held down.
     * @param virtualKey The virtual key representing the key being held down.
     * @return Whether the given key is held down.
     */
    public static boolean held(int virtualKey) {
        return keysHeld[virtualKey];
    }

    /**
     * Returns wheter the given key was just pressed this game frame.
     * @param virtualKey The virtual key representing the key being pressed.
     * @return Whether the given key was just pressed.
     */
    public static boolean pressed(int virtualKey) {
        return keysPressed[virtualKey];
    }

    /**
     * Returns wheter the given key was just released this game frame.
     * @param virtualKey The virtual key representing the key being released.
     * @return Whether the given key was just released.
     */
    public static boolean released(int virtualKey) {
        return keysReleased[virtualKey];
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
