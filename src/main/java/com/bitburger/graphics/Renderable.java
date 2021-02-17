package com.bitburger.graphics;

import com.bitburger.engine.Screen;

/**
 * Classes that render graphics to the screen must implement this interface.
 * Rendering logic will be contained inside this method.
 */

public interface Renderable {
    void render(Screen screen);
}
