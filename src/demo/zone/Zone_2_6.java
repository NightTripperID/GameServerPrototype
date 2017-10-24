package demo.zone;

import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_2_6 extends Zone_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(11, 16, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_2-6.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_2-6.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_2-6.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-6.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-6.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-6.png");
        }

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_2_3.class, new TileCoord(5, 5, DemoTile.SIZE)), false)); // red
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-6.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-6.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-6.png");
    }
}
