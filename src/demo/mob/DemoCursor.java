package demo.mob;

import com.sun.istack.internal.NotNull;
import input.MouseCursor;

import java.awt.*;

public class DemoCursor extends MouseCursor {

    public static final int CURSOR_UP = 0;
    public static final int CURSOR_DOWN = 1;

    public DemoCursor(@NotNull Point cursorHotSpot, @NotNull String name, @NotNull String... imagePaths) {
        super(cursorHotSpot, name, imagePaths);
    }
}
