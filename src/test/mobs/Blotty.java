package test.mobs;

import graphics.Screen;
import input.Clickable;

import java.io.Serializable;

public class Blotty extends Mob implements Serializable, Clickable {

    public static final long serialVersionUID = 201709261124L;

    int col = 0xff0000;

    public Blotty(double x, double y) {
        super(x, y, 1, 1, 1, 1);
        width = 16;
        height = 16;
    }

    @Override
    public void onUpdate() {

        x += xSpeed * xDir;

        if(x < 0 || x > gameState.getScreenWidth()) {
            xDir *= -1;
        }

        y += ySpeed * yDir;

        if(y < 0 || y > gameState.getScreenHeight()) {
            yDir *= -1;
        }
    }

    @Override
    public void onRender(Screen screen) {
        screen.fillRect(x, y, 16, 16, col);
    }

    @Override
    public void onClicked(int x, int y) {
        int adjustedX = x / gameState.getScreenScale();
        int adjustedY = y / gameState.getScreenScale();

        if(adjustedX >= this.x && adjustedX <= this.x + width) {
            if(adjustedY >= this.y && adjustedY <= this.y + height) {
                col = ~col;
            }
        }
    }
}
