package demo.area;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.overlay.Overlay;
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

        Bundle inBundle = (Bundle) getIntent().getSerializableExtra("bundle");
        Player player = (Player) inBundle.getSerializableExtra("player");
        TileCoord tileCoord = (TileCoord) inBundle.getSerializableExtra("tileCoord");
        player.x = tileCoord.getX();
        player.y = tileCoord.getY();
        player.initialize(this);
        addEntity(player);
        setOverlay(new Overlay(player));

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        putTrigger(0xffff0000, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_2_1.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(1, 5, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> {
            setMapTile(12, 8 , 0xff808080);
            if(getMapTile(16, 12) == 0xff808080 && getMapTile(12, 16) == 0xff808080
                    && getMapTile(8, 12) == 0xff808080) {
                removeObelisk();
            }
        });
        putTrigger(0xff0000ff, () -> {
            setMapTile(16, 12, 0xff808080);
            if(getMapTile(12, 8) == 0xff808080 && getMapTile(12, 16) == 0xff808080
                    && getMapTile(8, 12) == 0xff808080) {
                removeObelisk();
            }
        });
        putTrigger(0xffffff00, () -> {
            setMapTile(12, 16, 0xff808080);
            if(getMapTile(12, 8) == 0xff808080 && getMapTile(16, 12) == 0xff808080
                    && getMapTile(8, 12) == 0xff808080) {
                removeObelisk();
            }
        });
        putTrigger(0xff00ffff, () -> {
            setMapTile(8, 12, 0xff808080);
            if(getMapTile(16, 12) == 0xff808080 && getMapTile(12, 16) == 0xff808080
                    && getMapTile(12, 8) == 0xff808080) {
                removeObelisk();
            }
        });
        putTrigger(0xffff00ff, () -> {  // raise wide doorway
            setMapTile(12, 12, 0xffbc0051); // floor switch down
            setMapTile(10, 6, 0xff823400); // earth top
            setMapTile(11, 6, 0xff823400); // earth top
            setMapTile(12, 6, 0xff823400); // earth top
            setMapTile(13, 6, 0xff823400); // earth top
            setMapTile(14, 6, 0xff823400); // earth top
            setMapTile(10, 7, 0xffc14d00); // earth side
            setMapTile(11, 7, 0xffdaff7f); // left side of wide doorway
            setMapTile(12, 7, 0xffa5ff7f); // left of wide doorway
            setMapTile(13, 7, 0xffffff8e); // right side of wide doorway
            setMapTile(14, 7, 0xffc14d00); // earth side
        });
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
