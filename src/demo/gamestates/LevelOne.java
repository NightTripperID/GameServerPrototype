package demo.gamestates;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import server.Server;
import demo.mobs.Blotty;
import input.MouseCursor;
import demo.mobs.Pudgie;

public class LevelOne extends GameState {

    private int count = 0;

    @Override
    public void onCreate(@NotNull Server server) {

        super.onCreate(server);

        Pudgie pudgie = (Pudgie) getIntent().getSerializableExtra("pudgie");
        pudgie.initialize(this);
        populate(pudgie);

        Blotty blotty = (Blotty) getIntent().getSerializableExtra("blotty");
        blotty.initialize(this);
        populate(blotty);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
    }

    @Override
    public void onUpdate() {
        if(getKeyboard().enterPressed) {
            Intent intent = new Intent(PauseMenu.class);
            startGameState(intent);
        }

        super.onUpdate();
    }

    @Override
    public void onRender(@NotNull Screen screen) {
        screen.renderString8x8(20, 20, 0xff00ff, "level 1");

        super.onRender(screen);
    }
}