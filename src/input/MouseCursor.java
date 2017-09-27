package input;

import com.sun.istack.internal.NotNull;
import entity.Entity;
import gamestate.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.Serializable;

public abstract class MouseCursor extends Entity implements Serializable, Mouse.MouseCallback {

    public static final long serialVersionUID = 201709251738L;

    protected Point cursorHotSpot;

    public MouseCursor(Point cursorHotSpot) {
        this.cursorHotSpot = cursorHotSpot;
    }

    @Override
    public void initialize(@NotNull GameState gameState) {
        super.initialize(gameState);
        gameState.getMouse().setMouseCallback(this);
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        gameState.mouseDragged(event);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        gameState.mousePressed(event);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        gameState.mouseReleased(event);
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        gameState.mouseClicked(event);
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        gameState.mouseEntered(event);
    }

    @Override
    public void mouseExited(MouseEvent event) {
        gameState.mouseExited(event);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        gameState.mouseWheelMoved(event);
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        gameState.mouseMoved(event);
    }
}
