package demo.zone;

import demo.audio.Jukebox;
import demo.audio.Sfx;
import demo.tile.DemoTile;
import demo.tile.LavaTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import gamestate.Trigger;
import server.Server;

public class Zone_2_2 extends Zone_2 {

    private static boolean cached;

    String msg = "the gate is locked.";

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(25, 25, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("res/map_2-2.png");
            loadTriggerTiles("res/triggermap_2-2.png");
            loadMobs("res/spawnmap_2-2.png");
            cached = true;
        } else {
            loadMapTiles("res/cached/map_2-2.png");
            loadTriggerTiles("res/cached/triggermap_2-2.png");
            loadMobs("res/cached/spawnmap_2-2.png");
        }

        putTrigger(0xffff0000, new Trigger(() -> changeZone(Zone_2_1.class, new TileCoord(1, 5, DemoTile.SIZE)), false)); // red

        putTrigger(0xff00ff00, // green
                new Trigger(
                        () -> {
                             if(player.inventory.remove("doorkey")) {
                                 setMapTile(16, 12, 0xff808080);
                                 Sfx.SWITCH.play();
                                 if(getMapTile(12, 16) == 0xff808080 && getMapTile(8, 12) == 0xff808080)
                                     removeObelisk();
                             } else
                                 createTextBox(0xffffff, msg);
                        },
                        true));

        putTrigger(0xff0000ff, // blue
                new Trigger(
                        () -> {
                            if(player.inventory.remove("doorkey")) {
                                setMapTile(12, 16, 0xff808080);
                                Sfx.SWITCH.play();
                                if(getMapTile(16, 12) == 0xff808080 && getMapTile(8, 12) == 0xff808080)
                                    removeObelisk();
                            } else {
                                createTextBox(0xffffff, msg);
                            }
                        },
                        true));

        putTrigger(0xffffff00, // yellow
                new Trigger(
                        () -> {
                            if(player.inventory.remove("doorkey")) {
                                setMapTile(8, 12, 0xff808080);
                                Sfx.SWITCH.play();
                                if(getMapTile(12, 16) == 0xff808080 && getMapTile(16, 12) == 0xff808080)
                                    removeObelisk();
                            } else {
                                createTextBox(0xffffff, msg);
                            }
                        },
                        true));

        putTrigger(0xff00ffff, // cyan
                new Trigger(
                        () -> {
                            setMapTile(12, 12, 0xffbc0051); // floor switch down
                            setMapTile(11, 7, 0xffdaff7f); // left side of wide doorway
                            setMapTile(12, 7, 0xffa5ff7f); // center of wide doorway
                            setMapTile(13, 7, 0xffffff8e); // right side of wide doorway
                            String msg = "Abandon all hope, ye who enter here!";
                            createTextBox(0xff0000, msg, Sfx.SWITCH);
                            Jukebox.DUNGEON_MUSIC.stop();
                        },
                        false));

        putTrigger(0xffff00ff, new Trigger(() -> changeZone(Zone_3_1.class, new TileCoord(10, 14, DemoTile.SIZE)), false)); // magenta

        putTrigger(0xffffa500,
                new Trigger(
                        () -> { // orange
                            String msg = "Legend has it that unlocking three gates reveals a secret.";
                            createTextBox(0xffffff, msg);
                        },
                        true));
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
        pixelsToPNG(getMapTiles(), "res/cached/map_2-2.png");
        pixelsToPNG(getTriggerTiles(), "res/cached/triggermap_2-2.png");
        pixelsToPNG(getMobTiles(), "res/cached/spawnmap_2-2.png");
    }
}
