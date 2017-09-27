package demo.mobs;

import input.MouseCursor;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DemoCursor extends MouseCursor {

    public DemoCursor(Point cursorHotSpot) {
        super(cursorHotSpot);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);
        gameState.setCustomMouseCursor("res/pointerdown.png", cursorHotSpot, "demo");
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        super.mouseReleased(event);
        gameState.setCustomMouseCursor("res/pointerup.png", cursorHotSpot, "demo");
    }
}
