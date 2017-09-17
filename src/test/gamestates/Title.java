package test.gamestates;

import gamestate.GameState;
import graphics.Screen;
import server.Server;
import test.mobs.Pudgie;


public class Title extends GameState {

    private Pudgie pudgie;

    public Title(Server server) {
        super(server);
    }

    @Override
    public void onCreate() {
        pudgie = (Pudgie) getIntent().getSerializableExtra("pudgie");

        if(pudgie != null)
            pudgie.initialize(this);
    }

    @Override
    public void onUpdate() {
        pudgie.onUpdate();
    }

    @Override
    public void onRender(Screen screen) {
        pudgie.onRender(screen);
    }

    @Override
    public void onDestroy() {
    }
}
