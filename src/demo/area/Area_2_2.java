package demo.area;

import demo.textbox.TextBox;
import demo.tile.DemoTile;
import demo.tile.LavaTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
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

        putTrigger(0xffff0000, () -> changeArea(Area_2_1.class, new TileCoord(1, 5, DemoTile.SIZE))); // red

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

            String msg = "Abandon all hope, ye who enter here!";
            createTextBox(0xff0000, msg);
        });

        putTrigger(0xffff00ff, () -> changeArea(Area_3_1.class, new TileCoord(10, 14, DemoTile.SIZE))); // magenta

        putTrigger(0xffffa500, () -> { // orange
            String msg = "Legend has it that unlocking three gates reveals a secret.";
            createTextBox(0xffffff, msg);
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
