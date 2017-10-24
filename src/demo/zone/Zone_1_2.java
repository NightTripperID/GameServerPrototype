package demo.zone;

import com.sun.istack.internal.NotNull;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_1_2 extends Zone_1 {

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

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_1_1.class, new TileCoord(11, 12, 16)), false)); // red
        putTrigger(0xff00ff00, new Trigger(() -> changeZone(Zone_1_1.class, new TileCoord(14, 15, 16)), false)); // green
        putTrigger(0xff0000ff, new Trigger(() -> changeZone(Zone_1_1.class, new TileCoord(17, 12, 16)), false)); // blue
        putTrigger(0xffffff00, new Trigger(() -> changeZone(Zone_1_3.class, new TileCoord(18, 31, 16)), false)); // yellow

        putTrigger(0xff00ffff, // cyan
                new Trigger(
                        () -> {
                             if(player.inventory.getCount("doorkey") > 0) {
                                 player.inventory.remove("doorkey");
                                 setMapTile(18, 13, 0xffa0a0cd);
                                 setMapTile(18, 12, 0xffa0a0cd);
                             } else {
                                 createTextBox(0xffffff, "The door is locked.");
                             }
                        },
                        true));

        putTrigger(0xffff00ff, new Trigger(() -> changeZone(Zone_1_4.class, new TileCoord(17, 8, 16)), false)); // magenta

        putTrigger(0xffffa500, // orange
                new Trigger(
                        () -> {
                            String msg = "Kill enemies to collect potions. Use the middle mouse button to heal with a potion. Use the right mouse button to use all your potions to cast magic! The more potions you have, the stronger the spell!";
                            createTextBox(0xffffff, msg);
                        },
                        true));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-2.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-2.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-2.png");
    }
}
