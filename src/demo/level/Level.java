package demo.level;

import com.sun.istack.internal.NotNull;
import demo.player.Player;
import demo.tile.Tile;
import gamestate.GameState;
import input.MouseCursor;
import server.Server;

public class Level extends GameState {

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(30, 20, Tile.TileSize.X16);

        Player player = (Player) getIntent().getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        addEntity(cursor);

        loadTiles(getClass().getClassLoader().getResource("resource/map.png"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
