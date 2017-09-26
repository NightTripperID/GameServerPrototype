package test.gamestates;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import input.Clickable;
import server.Server;
import test.mobs.Blotty;
import test.mobs.MouseCursor;
import test.mobs.Pudgie;

import java.util.ArrayList;
import java.util.List;

public class LevelOne extends GameState {

    private Pudgie pudgie;
    private Blotty blotty;
    private MouseCursor cursor;
    private int count = 0;

    private List<Clickable> clickables = new ArrayList<>();

    public LevelOne(@NotNull Server server) {
        super(server);
    }

    @Override
    public void onCreate() {
        pudgie = (Pudgie) getIntent().getSerializableExtra("pudgie");
        pudgie.initialize(this);

        blotty = (Blotty) getIntent().getSerializableExtra("blotty");
        blotty.initialize(this);
        clickables.add(blotty);

        cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
    }

    @Override
    public void onUpdate() {
        if(getKeyboard().enterPressed) {
            Intent intent = new Intent(PauseMenu.class);
            startGameState(intent);
        }

        pudgie.onUpdate();

        blotty.onUpdate();

//        if(count++ == MAX_TICK) {
//            Intent intent = new Intent(LevelTwo.class);
//            intent.putExtra("pudgie", pudgie);
//            intent.putExtra("cursor", cursor);
//            intent.putExtra("blotty", blotty);
//            swapGameState(intent);
//        }
    }

    @Override
    public void onRender(@NotNull Screen screen) {
        screen.renderString8x8(20, 20, 0xff00ff, "level 1");

        blotty.onRender(screen);

        pudgie.onRender(screen);
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void onClick(int x, int y) {
        super.onClick(x, y);
        for(Clickable c: clickables) {
            c.onClicked(x, y);
        }
//        clickables.forEach((clickable) -> onClicked(x, y));
    }
}
