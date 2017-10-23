package demo.area;

import com.sun.istack.internal.NotNull;
import demo.textbox.TextBox;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
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

        putTrigger(0xffff0000, () -> { // red
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(11, 12, 16));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> { // green
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(14, 15, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff0000ff, () -> { // blue
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(17, 12, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xffffff00, () -> { // yellow
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_3.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(18, 31, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ffff, () -> { // cyan
            if(player.inventory.getCount("doorkey") > 0) {
                player.inventory.remove("doorkey");
                setMapTile(18, 13, 0xffa0a0cd);
                setMapTile(18, 12, 0xffa0a0cd);
            }
        });

        putTrigger(0xffff00ff, () -> { // magenta
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_4.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(17, 8, 16));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xffffa500, () -> { // orange
            Intent intent = new Intent(TextBox.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("textCol", 0xffffff);
            intent.putExtra("msg", "Kill enemies to collect potions. Use the middle mouse button to heal with a potion. Use the right mouse button to use all your potions and cast magic! The more potions you have, the stronger the spell!");
            pushGameState(intent);
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
