package demo.area;

import com.sun.istack.internal.NotNull;
import demo.mob.player.Player;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_1_2 extends Area_1 {

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        loadMapTiles(getClass().getClassLoader().getResource("resource/map_1-2.png"));
        loadTriggerTiles(getClass().getClassLoader().getResource("resource/triggermap_1-2.png"));
        loadMobs(getClass().getClassLoader().getResource("resource/spawnmap_1-2.png"));

        Bundle inBundle = getIntent().getBundle();

        Player player = (Player) inBundle.getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        putTrigger(0xffff0000, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(11, 12, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(14, 15, DemoTile.SIZE);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });

        putTrigger(0xff0000ff, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(17, 12, DemoTile.SIZE);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });

        putTrigger(0xffffff00, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_3.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(18, 31, DemoTile.SIZE);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ffff, () -> {
            if(player.inventory.getCount("doorkey") > 0) {
                player.inventory.remove("doorkey");
                setMapTile(18, 1, 0xffb200ff);
                setTriggerTile(18, 1, 0xffff00ff);
            }
        });

        putTrigger(0xffff00ff, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(11, 12, 16);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });
    }
}
