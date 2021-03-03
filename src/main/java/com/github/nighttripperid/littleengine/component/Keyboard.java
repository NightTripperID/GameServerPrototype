package com.github.nighttripperid.littleengine.component;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

    private static final boolean[] keys = new boolean[120];
    private static final boolean[] keysLast = new boolean[120];
    private static final boolean[] keysHeld = new boolean[120];
    private static final boolean[] keysPressed = new boolean[120];
    private static final boolean[] keysReleased = new boolean[120];

    public Keyboard() {
    }

    public static void update() {
        System.arraycopy(keysHeld, 0, keysLast, 0, keysHeld.length);
        System.arraycopy(keys, 0, keysHeld, 0, keys.length);

        for (int i = 0; i < keysHeld.length; i++) {
            keysPressed[i] = keysHeld[i] && !keysLast[i];
            keysReleased[i] = !keysHeld[i] && keysLast[i];
        }
    }

    public static boolean held(int virtualKey) {
        return keysHeld[virtualKey];
    }

    public static boolean pressed(int virtualKey) {
        return keysPressed[virtualKey];
    }

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
