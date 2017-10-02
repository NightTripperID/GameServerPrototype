package demo.mob;

import input.MouseCursor;

import java.awt.*;
import java.awt.event.MouseEvent;

public class DemoCursor extends MouseCursor {

    public static final int CURSOR_UP = 0;
    public static final int CURSOR_DOWN = 1;

    public DemoCursor(Point cursorHotSpot, String name, String... imagePaths) {
        super(cursorHotSpot, name, imagePaths);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);
        gameState.setCustomMouseCursor(images.get(CURSOR_DOWN), cursorHotSpot, name);
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        super.mouseReleased(event);
        gameState.setCustomMouseCursor(images.get(CURSOR_UP), cursorHotSpot, name);
    }
}
