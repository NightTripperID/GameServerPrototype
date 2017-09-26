package test.mobs;

import entity.Entity;
import gamestate.GameState;
import input.Mouse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class MouseCursor extends Entity implements Serializable, Mouse.MouseCallbacks {

    public static final long serialVersionUID = 201709251738L;

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        gameState.getMouse().setMouseCallbacks(this);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseMoved(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        gameState.setCustomMouseCursor("res/pointerdown.png", new Point(0, 0), "test");
        gameState.onClick(mouseEvent.getX(), mouseEvent.getY());

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        gameState.setCustomMouseCursor("res/pointerup.png", new Point(0, 0), "test");
    }
}
