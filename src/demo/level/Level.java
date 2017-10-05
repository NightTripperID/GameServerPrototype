package demo.level;

import com.sun.istack.internal.NotNull;
import demo.player.Player;
import demo.tile.Tile;
import demo.tile.Tiles;
import gamestate.GameState;
import graphics.Screen;
import input.MouseCursor;

import server.Server;

import java.util.Random;

public class Level extends GameState {

    private Random random = new Random();

    private int width = 30; // tile precision
    private int height = 20; // tile precision

    private int xScroll, yScroll;

    private int[] tiles = new int[width * height];

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        Player player = (Player) getIntent().getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        addEntity(cursor);

        generateTiles();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void generateTiles() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                tiles[x + y * width] = random.nextInt(2);
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = (xScroll + screen.getWidth()) >> 4;
        int y0 = yScroll >> 4;
        int y1 = (yScroll + screen.getHeight()) >> 4;
        
        for (int y = y0; y < y1; y++)
            for (int x = x0; x < x1; x++)
                getTile(x, y).render(screen, x << 4, y << 4);

        super.render(screen);
    }

    public Tile getTile(int x, int y) {

        if(x < 0 || y < 0 || x >= width || y >= height)
            return Tiles.voidTile;

        if(tiles[x + y * width] == 0)
            return Tiles.dirtTile;

        if(tiles[x + y * width] == 1)
            return Tiles.mudTile;

        return Tiles.voidTile;
    }

    public void scrollX(int xScroll) {
        this.xScroll += xScroll;
    }

    public void scrollY(int yScroll) {
        this.yScroll += yScroll;
    }
}
