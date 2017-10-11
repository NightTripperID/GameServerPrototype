package demo.area;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_1_3 extends Area_1 {

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        loadMapTiles(getClass().getClassLoader().getResource("resource/map_1-3.png"));
        loadTriggerTiles(getClass().getClassLoader().getResource("resource/triggermap_1-3.png"));
        loadMobs(getClass().getClassLoader().getResource("resource/spawnmap_1-3.png"));

        Bundle inBundle = getIntent().getBundle();

        Mob player = (Player) inBundle.getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        putTrigger(0xffff0000, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            TileCoord tileCoord = new TileCoord(18, 18, DemoTile.SIZE);
            player.x = tileCoord.getX();
            player.y = tileCoord.getY();

            Bundle outBundle = new Bundle();
            outBundle.putExtra("player", player);

            intent.setBundle(outBundle);
            swapGameState(intent);
        });
    }
}
