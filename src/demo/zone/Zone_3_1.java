package demo.zone;

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

        initMap(22, 22, Tile.TileSize.X16);

        if (!cached) {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/map_3-1.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/triggermap_3-1.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/spawnmap_3-1.png");
            cached = true;
        } else {
            loadMapTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/map_3-1.png");
            loadTriggerTiles("/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_3-1.png");
            loadMobs("/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_3-1.png");
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

        pixelsToPNG(getMapTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/map_3-1.png");
        pixelsToPNG(getTriggerTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/triggermap_3-1.png");
        pixelsToPNG(getMobTiles(), "/home/jeep/IdeaProjects/LittleEngine/res/cached/spawnmap_3-1.png");
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
