package demo.zone;

import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_1_3 extends Zone_1 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        if(!cached) {
            loadMapTiles("res/map_1-3.png");
            loadTriggerTiles("res/triggermap_1-3.png");
            loadMobs("res/spawnmap_1-3.png");
            cached = true;
        } else {
            loadMapTiles("res/cached/map_1-3.png");
            loadTriggerTiles("res/cached/triggermap_1-3.png");
            loadMobs("res/cached/spawnmap_1-3.png");
        }

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_1_2.class, new TileCoord(18, 18, 16)), false)); // red

        putTrigger(0xff00ff00, // green
                new Trigger(
                        () -> {
                            String msg = "there is a door somewhere that needs unlocking...";
                            createTextBox(0xffffff, msg);
                        },
                        true));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "res/cached/map_1-3.png");
        pixelsToPNG(getTriggerTiles(), "res/cached/triggermap_1-3.png");
        pixelsToPNG(getMobTiles(), "res/cached/spawnmap_1-3.png");
    }
}
