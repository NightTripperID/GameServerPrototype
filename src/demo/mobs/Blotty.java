package demo.mobs;

import entity.MouseInteractive;
import graphics.Screen;

import java.awt.event.MouseEvent;
import java.io.Serializable;

public class Blotty extends Mob implements Serializable, MouseInteractive {

    public static final long serialVersionUID = 201709261124L;

    private int col = 0xff0000;

    public Blotty(double x, double y) {
        super(x, y, 1, 1, 1, 1);
        width = 32;
        height = 32;
    }

    @Override
    public void onUpdate() {

        x += xSpeed * xDir;

        if(x < 0 || x + width > gameState.getScreenWidth()) {
            xDir *= -1;
        }

        y += ySpeed * yDir;

        if(y < 0 || y + height > gameState.getScreenHeight()) {
            yDir *= -1;
        }
    }

    @Override
    public void onRender(Screen screen) {
        screen.fillRect(x, y, width, height, col);
    }

    @Override
    public void mousePressed(MouseEvent event) {

        int adjustedX = adjustPointForScale(event.getX());
        int adjustedY = adjustPointForScale(event.getY());

        if(!mouseTouched(adjustedX, adjustedY))
            return;

        xSpeed = 0;
        ySpeed = 0;
        col = ~col;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        xSpeed = 1;
        ySpeed = 1;
    }

    @Override
    public void mouseDragged(MouseEvent event) {

        int adjustedX = adjustPointForScale(event.getX());
        int adjustedY = adjustPointForScale(event.getY());

        if(!mouseTouched(adjustedX, adjustedY))
            return;

        x = adjustedX - width / 2;
        y = adjustedY - height / 2;
    }

    private int adjustPointForScale(int point) {
       return point / gameState.getScreenScale();
    }

    private boolean mouseTouched(int mouseX, int mouseY) {

        if(mouseX >= x && mouseX <= x + width) {
            if(mouseY >= y && mouseY <= y + height) {
                return true;
            }
        }

        return false;
    }
}
