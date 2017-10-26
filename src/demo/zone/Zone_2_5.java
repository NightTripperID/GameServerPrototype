package demo.zone;

import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_2_5 extends Zone_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(11, 16, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("res/map_2-5.png");
            loadTriggerTiles("res/triggermap_2-5.png");
            loadMobs("res/spawnmap_2-5.png");
            cached = true;
        } else {
            loadMapTiles("res/cached/map_2-5.png");
            loadTriggerTiles("res/cached/triggermap_2-5.png");
            loadMobs("res/cached/spawnmap_2-5.png");
        }
        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_2_3.class, new TileCoord(5, 2, DemoTile.SIZE)), false)); // red
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "res/cached/map_2-5.png");
        pixelsToPNG(getTriggerTiles(), "res/cached/triggermap_2-5.png");
        pixelsToPNG(getMobTiles(), "res/cached/spawnmap_2-5.png");
    }
}
