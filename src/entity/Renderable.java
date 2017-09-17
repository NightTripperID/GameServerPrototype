package entity;

import com.sun.istack.internal.NotNull;
import graphics.Screen;

public interface Renderable {
    void onRender(@NotNull Screen screen);
}
