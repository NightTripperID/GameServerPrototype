package demo.area;

import com.sun.istack.internal.NotNull;
import demo.textbox.TextBox;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Intent;
import server.Server;

public class Area_1_2 extends Area_1 {

    private static boolean cached;

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        if(!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_1-2.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_1-2.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_1-2.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-2.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-2.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-2.png");
        }

        putTrigger(0xffff0000, () -> changeArea(Area_1_1.class, new TileCoord(11, 12, 16))); // red
        putTrigger(0xff00ff00, () -> changeArea(Area_1_1.class, new TileCoord(14, 15, 16))); // green
        putTrigger(0xff0000ff, () -> changeArea(Area_1_1.class, new TileCoord(17, 12, 16))); // blue
        putTrigger(0xffffff00, () -> changeArea(Area_1_3.class, new TileCoord(18, 31, 16))); // yellow

        putTrigger(0xff00ffff, () -> { // cyan
            if(player.inventory.getCount("doorkey") > 0) {
                player.inventory.remove("doorkey");
                setMapTile(18, 13, 0xffa0a0cd);
                setMapTile(18, 12, 0xffa0a0cd);
            }
        });

        putTrigger(0xffff00ff, () -> changeArea(Area_1_4.class, new TileCoord(17, 8, 16))); // magenta

        putTrigger(0xffffa500, () -> { // orange
            String msg = "Kill enemies to collect potions. Use the middle mouse button to heal with a potion. Use the right mouse button to use all your potions to cast magic! The more potions you have, the stronger the spell!";
            createTextBox(0xffffff, msg);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-2.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-2.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-2.png");
    }
}
