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

public class LevelTwo extends GameState {

    private Pudgie pudgie;
    private Blotty blotty;
    private MouseCursor cursor;
    private int count;

    private List<Clickable> clickables = new ArrayList<>();

    public LevelTwo(@NotNull Server server) {
        super(server);
    }

    @Override
    public void onCreate() {

        blotty = (Blotty) getIntent().getSerializableExtra("blotty");
        blotty.initialize(this);
        clickables.add(blotty);

        pudgie = (Pudgie) getIntent().getSerializableExtra("pudgie");
        pudgie.initialize(this);

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

    @Override
    public void onDestroy() {

    }

    @Override
    public void onClick(int x, int y) {
        super.onClick(x, y);
        clickables.forEach((clickable) -> onClick(x, y));
    }
}
