package gamestate;

import com.sun.istack.internal.NotNull;
import entity.Entity;
import entity.MouseInteractive;
import entity.Renderable;
import entity.Updatable;
import graphics.Screen;
import input.Keyboard;
import input.Mouse;
import server.Server;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class GameState {

    private Server server;
    private Intent intent;

    private List<Updatable> updatables = new ArrayList<>();
    private List<Renderable> renderables = new ArrayList<>();
    private List<MouseInteractive> mouseInteractives = new ArrayList<>();

    protected static final int MAX_TICK = 10 * 60;

    public void onCreate(@NotNull Server server) {
        this.server = server;
    }

    public void onDestroy() {
    }

    public void onUpdate() {
        updatables.forEach(Updatable::onUpdate);
    }

    public void onRender(@NotNull Screen screen) {
        for(Renderable r : renderables)
            r.onRender(screen);
    }

    protected final void startGameState(@NotNull Intent intent) {
        server.pushGameState(intent);
    }

    protected final void finish() {
        server.popGameState();
    }

    protected final void swapGameState(@NotNull Intent intent) {
        server.swapGameState(intent);
    }

    @NotNull
    protected final Intent getIntent() {
        return intent;
    }

    protected void populate(@NotNull Entity entity) {

        if (entity instanceof Updatable)
            updatables.add((Updatable) entity);

        if (entity instanceof Renderable)
            renderables.add((Renderable) entity);

        if (entity instanceof MouseInteractive)
            mouseInteractives.add((MouseInteractive) entity);
    }

    public final void setIntent(@NotNull Intent intent) {
        this.intent = intent;
    }

    public final void setCustomMouseCursor(@NotNull String imagePath, @NotNull Point cursorHotspot,
                                           @NotNull String name) {
        server.setCustomMouseCursor(imagePath, cursorHotspot, name);
    }

    public final int getScreenWidth() {
        return server.getScreenWidth();
    }

    public final int getScreenHeight() {
        return server.getScreenHeight();
    }

    public final int getScreenScale() {
        return server.getScreenScale();
    }

    public final Keyboard getKeyboard() {
        return server.getKeyboard();
    }

    public final Mouse getMouse() {
        return server.getMouse();
    }

    public final void mouseClicked(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseClicked(mouseEvent);
    }

    public final void mousePressed(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mousePressed(mouseEvent);
    }

    public final void mouseReleased(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseReleased(mouseEvent);
    }

    public final void mouseEntered(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseEntered(mouseEvent);
    }

    public final void mouseExited(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseExited(mouseEvent);
    }

    public final void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseWheelMoved(mouseWheelEvent);
    }

    public final void mouseDragged(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseDragged(mouseEvent);
    }

    public final void mouseMoved(MouseEvent mouseEvent) {
        for(MouseInteractive m : mouseInteractives)
            m.mouseMoved(mouseEvent);
    }
}
