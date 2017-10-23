package demo.area;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.player.Player;
import demo.overlay.Overlay;
import demo.textbox.TextBox;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_1_1 extends Area_1 {

    private static boolean cached;

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(30, 20, Tile.TileSize.X16);

        if(!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_1-1.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_1-1.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_1-1.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-1.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-1.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-1.png");
        }

        putTrigger(0xffff0000, () -> { // red

            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();

            bundle.putExtra("tileCoord", new TileCoord(5, 22, 16));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ff00, () -> { // green
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();

            bundle.putExtra("tileCoord", new TileCoord(17, 34, 16));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff0000ff, () -> { // blue
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());

            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(29, 22, 16));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });

        putTrigger(0xff00ffff, () -> { // cyan
            Intent intent = new Intent(TextBox.class);
            intent.putExtra("pixels", getScreenPixels());
            intent.putExtra("msg", "Welcome to Varg's very short adventure! Use W-A-S-D keys or the arrow keys to move. Press the left mouse button to throw an axe!");
            intent.putExtra("textCol", 0xffffff);
            pushGameState(intent);

        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-1.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-1.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-1.png");
    }
}
