package demo.zone;

import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.tile.WaterTile;
import gamestate.Trigger;
import server.Server;

public class Zone_1_4 extends Zone_1 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_1-4.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_1-4.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_1-4.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-4.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-4.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-4.png");
        }

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_1_2.class, new TileCoord(18, 6, 16)), false)); // red

        putTrigger(0xff00ff00, // green
                new Trigger(
                        () -> {
                            setMapTile(2, 3, 0xffbc0051);
                            if (getMapTile(33, 3) == 0xffbc0051 &&
                                    getMapTile(2, 32) == 0xffbc0051 &&
                                    getMapTile(33, 32) == 0xffbc0051) {
                                drainMoat();
                            }
                        },
                        false));

        putTrigger(0xff0000ff, // blue
                new Trigger(
                        () -> {
                            setMapTile(33, 3, 0xffbc0051);
                            if (getMapTile(2, 3) == 0xffbc0051 &&
                                    getMapTile(2, 32) == 0xffbc0051 &&
                                    getMapTile(33, 32) == 0xffbc0051) {
                                drainMoat();
                            }
                        },
                        false));

        putTrigger(0xffffff00, // yellow
                new Trigger(
                        () -> {
                            setMapTile(2, 32, 0xffbc0051);
                            if (getMapTile(2, 3) == 0xffbc0051 &&
                                    getMapTile(33, 3) == 0xffbc0051 &&
                                    getMapTile(33, 32) == 0xffbc0051) {
                                drainMoat();
                            }
                        },
                        false));

        putTrigger(0xff00ffff, // cyan
                new Trigger(
                        () -> {
                            setMapTile(33, 32, 0xffbc0051);
                            if (getMapTile(2, 3) == 0xffbc0051 &&
                                    getMapTile(33, 3) == 0xffbc0051 &&
                                    getMapTile(2, 32) == 0xffbc0051) {
                                drainMoat();
                            }
                        },
                        false));

        putTrigger(0xffff00ff,new Trigger(() -> changeZone(Zone_2_1.class, new TileCoord(11, 4, 16)), false)); // magenta
    }

    private void drainMoat() {
        // drain the water
        for (int y = 11; y < 24; y++)
            for (int x = 11; x < 25; x++)
                setMapTile(x, y, 0xffc0c0c0);

        // place the staircase
        setMapTile(18, 17, 0xff303030);

        // open the moat walls
        setMapTile(17, 10, 0xffc0c0c0);
        setMapTile(17, 11, 0xffc0c0c0);
        setMapTile(18, 10, 0xffc0c0c0);
        setMapTile(18, 11, 0xffc0c0c0);
        setMapTile(10, 17, 0xffc0c0c0);
        setMapTile(10, 18, 0xffc0c0c0);
        setMapTile(25, 17, 0xffc0c0c0);
        setMapTile(25, 18, 0xffc0c0c0);
        setMapTile(17, 24, 0xffc0c0c0);
        setMapTile(17, 25, 0xffc0c0c0);
        setMapTile(18, 24, 0xffc0c0c0);
        setMapTile(18, 25, 0xffc0c0c0);

        String msg = "You hear the sound of draining water...";
        createTextBox(0xff00ffff, msg);
    }

    @Override
    public void update() {
        super.update();
        WaterTile.update();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-4.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-4.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-4.png");
    }
}