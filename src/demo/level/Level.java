package demo.level;

import demo.player.Player;
import gamestate.GameState;
import input.MouseCursor;

import server.Server;

public class Level extends GameState {

    private Player player;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        Player player = (Player) getIntent().getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        addEntity(cursor);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
