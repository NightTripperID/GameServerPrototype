package demo.zone;

import demo.audio.Jukebox;
import demo.congratulations.Congratulations;
import demo.mob.enemy.slime.RevengeSlime;
import demo.tile.PurpleLavaTile;
import demo.tile.Tile;
import demo.transition.FadeOut;
import gamestate.Intent;
import server.Server;

public class Zone_3_1 extends Zone_3 {
    
    private static boolean cached;

    public boolean lastScene;
    private final int numRevengeSlimes = RevengeSlime.NUM_REVENGE_SLIMES;
    private int slimeCount = numRevengeSlimes;


    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        Jukebox.BOSS_MUSIC.play(true);

        initMap(22, 22, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("res/map_3-1.png");
            loadTriggerTiles("res/triggermap_3-1.png");
            loadMobs("res/spawnmap_3-1.png");
            cached = true;
        } else {
            loadMapTiles("res/cached/map_3-1.png");
            loadTriggerTiles("res/cached/triggermap_3-1.png");
            loadMobs("res/cached/spawnmap_3-1.png");
        }

        slimeCount = numRevengeSlimes;
    }

    @Override
    public void update() {
        super.update();
        PurpleLavaTile.update();

        if(lastScene)
            if(slimeCount == 0)
                congratulations();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pixelsToPNG(getMapTiles(), "res/cached/map_3-1.png");
        pixelsToPNG(getTriggerTiles(), "res/cached/triggermap_3-1.png");
        pixelsToPNG(getMobTiles(), "res/cached/spawnmap_3-1.png");
    }

    public void subtractSlime() {
        slimeCount--;
    }

    private void congratulations() {
        Intent intent = new Intent(FadeOut.class);
        intent.putExtra("nextGameState", Congratulations.class);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("fadeRate", 1);
        swapGameState(intent);
    }
}
