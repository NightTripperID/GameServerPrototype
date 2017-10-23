package demo.area;

import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_2_3 extends Area_2 {

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

        putTrigger(0xffff0000, () -> { // red
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(21, 5, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_1.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> { // green
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(1, 9, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_4.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff0000ff, () -> { // blue
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(5, 13, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_5.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xffffff00, () -> { // yellow
            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(5, 1, DemoTile.SIZE));
            bundle.putExtra("player", player);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("nextGameState", Area_2_6.class);
            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-3.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-3.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-3.png");
    }
}
