package test.mobs;

import entity.Entity;
import entity.Renderable;
import gamestate.GameState;
import graphics.Screen;
import input.Mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.Serializable;

public class MouseCursor extends Entity implements Serializable, Renderable, Mouse.MouseCallbacks {

    public static final long serialVersionUID = 201709251738L;

    private int x;
    private int y;

    private boolean isPressed;

    private int[] mouseColors = {0xff0000, 0x00ff00, 0x0000ff, 0xffff00, 0xff00ff, 0x00ffff};
    private int currentColor;

    public MouseCursor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        gameState.getMouse().setMouseCallbacks(this);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        x = mouseEvent.getX() / gameState.getScreenScale();
        y = mouseEvent.getY() / gameState.getScreenScale();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseMoved(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        isPressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        isPressed = false;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        if (mouseWheelEvent.getWheelRotation() == 1) {
            if(++currentColor == mouseColors.length)
                currentColor = 0;
        } else if (mouseWheelEvent.getWheelRotation() == -1) {
            if(--currentColor < 0)
                currentColor = mouseColors.length - 1;
        }
    }

    @Override
    public void onRender(Screen screen) {
        if(isPressed) {
            screen.fillRect(x, y, 8, 8, mouseColors[currentColor]);
        } else {
            screen.drawRect(x, y, 8, 8, mouseColors[currentColor]);
        }
    }
}
