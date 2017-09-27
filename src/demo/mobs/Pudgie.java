package demo.mobs;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import graphics.Screen;
import input.Keyboard;

import java.io.Serializable;

public class Pudgie extends Mob implements Serializable {

    public static final long serialVersionUID = 201709151748L;

    private Keyboard keyboard;

    public Pudgie(double x, double y) {
        super(x, y, 2, 2, 1, 1);

        width = 8;
        height = 8;
    }

    public void initialize(@NotNull GameState gs) {
        super.initialize(gs);
        keyboard = gameState.getKeyboard();
    }

    @Override
    public void onUpdate() {

        if (keyboard.leftHeld) {
            xDir = -1;
            xSpeed = 2;
        } else if (keyboard.rightHeld) {
            xDir =1;
            xSpeed = 2;
        } else {
            xSpeed = 0;
        }

        if (keyboard.upHeld) {
            yDir = -1;
            ySpeed = 2;
        } else if (keyboard.downHeld) {
            yDir = 1;
            ySpeed = 2;
        } else {
            ySpeed = 0;
        }

        x += (xSpeed * xDir);
        y += (ySpeed * yDir);

        if (x < 0) {
            xSpeed = 0;
            x = 0;
        }

        if (x > (gameState.getScreenWidth() - width)) {
            xSpeed = 0;
            x = gameState.getScreenWidth() - width;
        }

        if (y < 0) {
            ySpeed = 0;
            y = 0;
        }

        if (y > (gameState.getScreenHeight() - height)) {
            ySpeed = 0;
            y = gameState.getScreenHeight() - height;
        }
    }

    @Override
    public void onRender(Screen screen) {
        screen.fillRect(x, y, width, height, 0xffffff);
    }
}
