package demo.area;

import com.sun.istack.internal.NotNull;
import demo.player.Player;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Intent;
import input.MouseCursor;
import server.Server;

public class Area_1_2 extends Area {

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        Player player = (Player) getIntent().getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        addEntity(cursor);

        loadMapTiles(getClass().getClassLoader().getResource("resource/map_1-2.png"));
        loadTriggerTiles(getClass().getClassLoader().getResource("resource/triggermap_1-2.png"));

        triggers.put(0xffff0000, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());
            TileCoord tileCoord = new TileCoord(11, 12, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();
            intent.putExtra("player", player);
            intent.putExtra("cursor", cursor);
            swapGameState(intent);
        });

        triggers.put(0xff00ff00, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());
            TileCoord tileCoord = new TileCoord(14, 15, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();
            intent.putExtra("player", player);
            intent.putExtra("cursor", cursor);
            swapGameState(intent);
        });

        triggers.put(0xff0000ff, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());
            TileCoord tileCoord = new TileCoord(17, 12, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();
            intent.putExtra("player", player);
            intent.putExtra("cursor", cursor);
            swapGameState(intent);
        });
    }
}
