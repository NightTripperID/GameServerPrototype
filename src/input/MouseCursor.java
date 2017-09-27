package input;

import com.sun.istack.internal.NotNull;
import entity.Entity;
import gamestate.GameState;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class MouseCursor extends Entity implements Serializable, Mouse.MouseCallback {

    public static final long serialVersionUID = 201709251738L;

    protected List<Image> images = new ArrayList<>();

    protected Point cursorHotSpot;
    protected String name;

    public MouseCursor(@NotNull Point cursorHotSpot, @NotNull String name, @NotNull String ... imagePaths) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        for(String path: imagePaths)
            images.add(toolkit.getImage(path));

        this.cursorHotSpot = cursorHotSpot;
        this.name = name;
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

    public final Image getImage(int index) {
        return images.get(index);
    }

    public final Point getCursorHotSpot() {
        return cursorHotSpot;
    }

    public final String getName() {
        return name;
    }
}
