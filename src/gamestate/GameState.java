package gamestate;

import com.sun.istack.internal.NotNull;
import graphics.Screen;
import input.Keyboard;
import server.Server;

public abstract class GameState {

    private Server server;
    private Intent intent;

    protected static final int MAX_TICK = 10 * 60;

    public GameState(@NotNull Server server) {
        this.server = server;
    }

    public abstract void onCreate();

    public abstract void onUpdate();

    public abstract void onRender(@NotNull Screen screen);

    public abstract void onDestroy();

    public final void swapGameState(@NotNull Intent intent) {
        server.swapGameState(intent);
    }

    public final void finish() {
        server.finishGameState();
    }

    public final void startGameState(@NotNull Intent intent) {
        server.startNewGameState(intent);
    }

    @NotNull
    public final Intent getIntent() {
        return intent;
    }

    public final void setIntent(@NotNull Intent intent) {
        this.intent = intent;
    }

    public final int getScreenWidth() {
        return server.getScreenWidth();
    }

    public final int getScreenHeight() {
        return server.getScreenHeight();
    }

    public final Keyboard getKeyboard() {
        return server.getKeyboard();
    }
}
