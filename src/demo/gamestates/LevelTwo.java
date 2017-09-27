package demo.gamestates;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import server.Server;
import demo.mobs.Blotty;
import input.MouseCursor;
import demo.mobs.Pudgie;

public class LevelTwo extends GameState {

    private Pudgie pudgie;
    private Blotty blotty;
    private MouseCursor cursor;
    private int count;

    @Override
    public void onCreate(@NotNull Server server) {

        super.onCreate(server);

        blotty = (Blotty) getIntent().getSerializableExtra("blotty");
        blotty.initialize(this);
        populate(blotty);

        pudgie = (Pudgie) getIntent().getSerializableExtra("pudgie");
        pudgie.initialize(this);
        populate(pudgie);

        cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        populate(cursor);
    }

    @Override
    public void onUpdate() {

        if(getKeyboard().enterPressed) {
            Intent intent = new Intent(PauseMenu.class);
            startGameState(intent);
        }

        pudgie.onUpdate();
        blotty.onUpdate();

        if(count++ == MAX_TICK) {
            Intent intent = new Intent(LevelOne.class);
            intent.putExtra("pudgie", pudgie);
            intent.putExtra("blotty", blotty);
            intent.putExtra("cursor", cursor);
            swapGameState(intent);
        }
    }

    @Override
    public void onRender(@NotNull Screen screen) {
        screen.renderString8x8(40, 40, 0x00ff00, "Level 2");
        pudgie.onRender(screen);
        blotty.onRender(screen);
    }
}
