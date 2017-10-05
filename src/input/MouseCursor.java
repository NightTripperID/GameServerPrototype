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

public abstract class MouseCursor extends Entity implements Serializable {

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
