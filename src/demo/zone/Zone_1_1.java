package demo.zone;

import com.sun.istack.internal.NotNull;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_1_1 extends Zone_1 {

    private static boolean cached;

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(30, 20, Tile.TileSize.X16);

        if(!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_1-1.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_1-1.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_1-1.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-1.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-1.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-1.png");
        }

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_1_2.class, new TileCoord(5, 22, 16)), false)); // red
        putTrigger(0xff00ff00, new Trigger(() -> changeZone(Zone_1_2.class, new TileCoord(17, 34, 16)), false)); // green
        putTrigger(0xff0000ff, new Trigger(() -> changeZone(Zone_1_2.class, new TileCoord(29, 22, 16)), false)); // blue

        putTrigger(0xff00ffff,
                new Trigger(() -> { // cyan
                    String msg = "Welcome to Varg's very short adventure! Use W-A-S-D keys or the arrow keys to move. Press the left mouse button to throw an axe!";
                    createTextBox(0xffffff, msg);
                    }, true));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-1.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-1.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-1.png");
    }
}
