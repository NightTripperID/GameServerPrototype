package demo.level;

import com.sun.istack.internal.NotNull;
import demo.player.Player;
import demo.tile.Tile;
import demo.tile.Tiles;
import gamestate.GameState;
import input.MouseCursor;
import server.Server;

public class Level extends GameState {

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        initMap(30, 20, Tile.TileSize.X16);

        Player player = (Player) getIntent().getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        addEntity(cursor);

        loadTiles(getClass().getClassLoader().getResource("resource/map.png"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Tile getTile(int x, int y) {
        if(x < 0 || y < 0 || x >= getMapWidth() || y >= getMapHeight())
            return Tiles.voidTile;

        switch (getTiles()[x + y * getMapWidth()]) {
            case 0xfffca75d:
                return Tiles.dirtTile;
            case 0xff267f00:
                return Tiles.cactusTile;
            case 0xffffffff:
                return Tiles.skellytile;
            case 0xff00ffff:
                return Tiles.graveTile;
            case 0xffa5ff7f:
                return Tiles.crossTile;
            case 0xff606060:
                return Tiles.pillarTopTile;
            case 0xff808080:
                return Tiles.pillarSideTile;
            case 0xffc0c0c0:
                return Tiles.stoneFloorTile;
            case 0xff21007f:
                return Tiles.stoneDoorwayTile;
            default:
                return Tiles.voidTile;
        }
    }
}
