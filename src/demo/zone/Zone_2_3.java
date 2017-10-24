package demo.zone;

import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_2_3 extends Zone_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(11, 7, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_2-3.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_2-3.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_2-3.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-3.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-3.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-3.png");
        }
        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_2_1.class, new TileCoord(21, 5, DemoTile.SIZE)), false)); // red
        putTrigger(0xff00ff00, new Trigger(() -> changeZone(Zone_2_4.class, new TileCoord(1, 9, DemoTile.SIZE)), false)); // green
        putTrigger(0xff0000ff, new Trigger(() -> changeZone(Zone_2_5.class, new TileCoord(5, 13, DemoTile.SIZE)), false)); // blue
        putTrigger(0xffffff00, new Trigger(() -> changeZone(Zone_2_6.class, new TileCoord(5, 1, DemoTile.SIZE)), false)); // yellow
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-3.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-3.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-3.png");
    }
}
