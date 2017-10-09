package demo.transition;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import server.Server;

public abstract class Fade extends GameState {

    int count;
    int[] pixels;

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);
        pixels = getIntent().getIntegerArrayExtra("pixels");
    }
}