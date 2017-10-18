package demo.area;

import demo.mob.Mob;
import demo.mob.player.Player;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Area_1_3 extends Area_1 {

    private static boolean cached;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        initMap(36, 36, Tile.TileSize.X16);

        if(!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_1-3.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_1-3.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_1-3.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-3.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-3.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-3.png");
        }

        Bundle inBundle = (Bundle) getIntent().getSerializableExtra("bundle");

        Mob player = (Player) inBundle.getSerializableExtra("player");
        TileCoord tileCoord = (TileCoord) inBundle.getSerializableExtra("tileCoord");
        player.x = tileCoord.getX();
        player.y = tileCoord.getY();
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        putTrigger(0xffff0000, () -> {
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("nextGameState", Area_1_2.class);
            intent.putExtra("pixels", getScreenPixels());


            Bundle bundle = new Bundle();
            bundle.putExtra("tileCoord", new TileCoord(18, 18, DemoTile.SIZE));
            bundle.putExtra("player", player);

            intent.putExtra("bundle", bundle);
            swapGameState(intent);
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_1-3.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_1-3.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_1-3.png");
    }
}
