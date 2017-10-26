package demo.zone;

import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_2_1 extends Zone_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(23, 11, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("res/map_2-1.png");
            loadTriggerTiles("res/triggermap_2-1.png");
            loadMobs("res/spawnmap_2-1.png");
            cached = true;
        } else {
            loadMapTiles("res/cached/map_2-1.png");
            loadTriggerTiles("res/cached/triggermap_2-1.png");
            loadMobs("res/cached/spawnmap_2-1.png");
        }

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_1_4.class, new TileCoord(17, 17, 16)), false)); // red
        putTrigger(0xff00ff00, new Trigger(() -> changeZone(Zone_2_2.class, new TileCoord(23, 12, 16)), false)); // green
        putTrigger(0xff0000ff, new Trigger(() -> changeZone(Zone_2_3.class, new TileCoord(1, 3, 16)), false)); // blue
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "res/cached/map_2-1.png");
        pixelsToPNG(getTriggerTiles(), "res/cached/triggermap_2-1.png");
        pixelsToPNG(getMobTiles(), "res/cached/spawnmap_2-1.png");
    }
}
