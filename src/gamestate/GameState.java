package gamestate;

import com.sun.istack.internal.NotNull;
import entity.Entity;
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

    protected static final int MAX_TICK = 10 * 60;

    public void onCreate(@NotNull Server server) {
        this.server = server;
    }

    public void onDestroy() {
    }

    public void update() {
        for(int i = 0; i < updatables.size(); i++)
            updatables.get(i).update();
    }

    public void render(@NotNull Screen screen) {
        for(int i = 0; i < renderables.size(); i++)
            renderables.get(i).render(screen);
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

    public void addEntity(@NotNull Entity entity) {

        if (entity instanceof Updatable)
            updatables.add((Updatable) entity);

        if (entity instanceof Renderable)
            renderables.add((Renderable) entity);
    }

    public void removeEntity(@NotNull Entity entity) {

        if(entity instanceof Updatable)
            updatables.remove(entity);

        if(entity instanceof Renderable)
            renderables.remove(entity);
    }

    public final void setIntent(@NotNull Intent intent) {
        this.intent = intent;
    }

    public final void setCustomMouseCursor(@NotNull Image image, @NotNull Point cursorHotspot,
                                           @NotNull String name) {
        server.setCustomMouseCursor(image, cursorHotspot, name);
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
}
