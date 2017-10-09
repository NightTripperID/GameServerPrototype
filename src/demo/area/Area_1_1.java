package demo.area;

import com.sun.istack.internal.NotNull;
import demo.player.Player;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_1_1 extends Area_1 {

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(30, 20, Tile.TileSize.X16);

        Bundle inBundle = getIntent().getBundle();

        Player player = (Player) inBundle.getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        loadMapTiles(getClass().getClassLoader().getResource("resource/map_1-1.png"));
        loadTriggerTiles(getClass().getClassLoader().getResource("resource/triggermap_1-1.png"));
        loadMobs(getClass().getClassLoader().getResource("resource/spawnmap_1-1.png"));

        putTrigger(0xffff0000, () -> {

            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(5, 22, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(17, 34, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });

        putTrigger(0xff0000ff, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(29, 22, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });
    }
}
