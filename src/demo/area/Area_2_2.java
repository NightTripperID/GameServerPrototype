package demo.area;

import demo.textbox.TextBox;
import demo.tile.DemoTile;
import demo.tile.LavaTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_2_2 extends Area_2 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(25, 25, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_2-2.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_2-2.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_2-2.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-2.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-2.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-2.png");
        }

        putTrigger(0xffff0000, () -> { // red
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_2_1.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(1, 5, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> { // green
            if(!player.inventory.remove("blue_doorkey")) {
                blueKeyRequired();
                return;
            }
            setMapTile(16, 12, 0xff808080);
            if(getMapTile(12, 16) == 0xff808080 && getMapTile(8, 12) == 0xff808080)
                removeObelisk();
        });

        putTrigger(0xff0000ff, () -> { // blue
            if(!player.inventory.remove("blue_doorkey")) {
                blueKeyRequired();
                return;
            }
            setMapTile(12, 16, 0xff808080);
            if(getMapTile(16, 12) == 0xff808080 && getMapTile(8, 12) == 0xff808080)
                removeObelisk();
        });

        putTrigger(0xffffff00, () -> { // yellow
            if(!player.inventory.remove("blue_doorkey")) {
                blueKeyRequired();
                return;
            }
            setMapTile(8, 12, 0xff808080);
            if(getMapTile(16, 12) == 0xff808080 && getMapTile(12, 16) == 0xff808080)
                removeObelisk();
        });

        putTrigger(0xff00ffff, () -> {  // cyan
            // raise wide doorway
            setMapTile(12, 12, 0xffbc0051); // floor switch down
            setMapTile(11, 7, 0xffdaff7f); // left side of wide doorway
            setMapTile(12, 7, 0xffa5ff7f); // center of wide doorway
            setMapTile(13, 7, 0xffffff8e); // right side of wide doorway

            Intent intent = new Intent(TextBox.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("textCol", 0xff0000);
            intent.putExtra("msg", "Abandon all hope, ye who enter here!");
            pushGameState(intent);
        });

        putTrigger(0xffff00ff, () -> { //magenta
           Intent intent = new Intent(FadeOut.class);
           Bundle bundle = new Bundle();
           bundle.putExtra("player", player);
           bundle.putExtra("tileCoord", new TileCoord(10, 14, DemoTile.SIZE));
           intent.putExtra("pixels", getScreenPixels());
           intent.putExtra("nextGameState", Area_3_1.class);
           intent.putExtra("bundle", bundle);
           swapGameState(intent);
        });

        putTrigger(0xffffa500, () -> { // orange
            Intent intent = new Intent(TextBox.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("textCol", 0xffffff);
            intent.putExtra("msg", "Legend has it that unlocking the three gates reveals a secret.");
            pushGameState(intent);
        });
    }

    private void blueKeyRequired() {
        Intent intent = new Intent(TextBox.class);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("textCol", 0x00ffff);
        intent.putExtra("msg", "A blue key is required.");
        pushGameState(intent);
    }

    private void removeObelisk() {
        setMapTile(12, 11, 0xff808080); // lava grate
        setMapTile(12, 12, 0xffe90064); // floor switch up
    }

    @Override
    public void update() {
        super.update();
        LavaTile.update();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_2-2.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_2-2.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_2-2.png");
    }
}
