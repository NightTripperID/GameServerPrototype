package demo.area;

import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.tile.WaterTile;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_1_4 extends Area_1 {

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

        putTrigger(0xffff0000, () -> { //red
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(18, 6, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> { // green
            setMapTile(2, 3, 0xffbc0051);
            if (getMapTile(33, 3) == 0xffbc0051 && getMapTile(2, 32) == 0xffbc0051 &&
                    getMapTile(33, 32) == 0xffbc0051) {
                drainMoat();
            }
        });

        putTrigger(0xff0000ff, () -> {  // blue
            setMapTile(33, 3, 0xffbc0051);
            if (getMapTile(2, 3) == 0xffbc0051 && getMapTile(2, 32) == 0xffbc0051 &&
                    getMapTile(33, 32) == 0xffbc0051) {
                drainMoat();
            }
        });

        putTrigger(0xffffff00, () -> { // yellow
            setMapTile(2, 32, 0xffbc0051);
            if (getMapTile(2, 3) == 0xffbc0051 && getMapTile(33, 3) == 0xffbc0051 &&
                    getMapTile(33, 32) == 0xffbc0051) {
                drainMoat();
            }
        });

        putTrigger(0xff00ffff, () -> { // cyan
            setMapTile(33, 32, 0xffbc0051);
            if (getMapTile(2, 3) == 0xffbc0051 && getMapTile(33, 3) == 0xffbc0051 &&
                    getMapTile(2, 32) == 0xffbc0051) {
                drainMoat();
            }
        });

        putTrigger(0xffff00ff,() -> { // magenta
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_2_1.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(11, 4, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });
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