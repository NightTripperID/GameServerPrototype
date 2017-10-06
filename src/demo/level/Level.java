package demo.level;

import com.sun.istack.internal.NotNull;
import demo.player.Player;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.Tiles;
import gamestate.GameState;
import graphics.Screen;
import input.MouseCursor;

import server.Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Level extends GameState {

    private Random random = new Random();

    private final int mapWidth = 30; // tile precision
    private final int mapHeight = 20; // tile precision

    private int xScroll, yScroll;

    private int[] tiles = new int[mapWidth * mapHeight];

    @Override
    public void onCreate(@NotNull Server server) {
        super.onCreate(server);

        Player player = (Player) getIntent().getSerializableExtra("player");
        player.initialize(this);
        addEntity(player);

        MouseCursor cursor = (MouseCursor) getIntent().getSerializableExtra("cursor");
        cursor.initialize(this);
        addEntity(cursor);

        loadTiles(getClass().getClassLoader().getResource("resource/map.png"));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void generateTiles() {
        for(int y = 0; y < mapHeight; y++)
            for(int x = 0; x < mapWidth; x++)
                tiles[x + y * mapWidth] = random.nextInt(2);
    }

    private void loadTiles(@NotNull URL url) {

        try {
            System.out.println("Trying to load: " + url.toString() + "...");
            BufferedImage map = ImageIO.read(url);
            int[] pixels = new int[mapWidth * mapHeight];
            map.getRGB(0, 0, mapWidth, mapHeight, pixels, 0, mapWidth);
            System.arraycopy(pixels, 0, tiles, 0, tiles.length);
            System.out.println("Success!");
        } catch (IOException e) {
            System.out.println("failed...");
            e.printStackTrace();
        }
    }

    @Override
    public void render(@NotNull Screen screen) {
        renderTiles(screen);
        super.render(screen);
    }

    private void renderTiles(@NotNull Screen screen) {

        screen.setOffset(xScroll, yScroll);
        int x0 = xScroll >> 4;
        int x1 = ((xScroll + screen.getWidth()) + DemoTile.WIDTH) >> 4;
        int y0 = yScroll >> 4;
        int y1 = ((yScroll + screen.getHeight()) + DemoTile.HEIGHT) >> 4;

        for (int y = y0; y < y1; y++)
            for (int x = x0; x < x1; x++)
                getTile(x, y).render(screen, x << 4, y << 4);
    }

    private Tile getTile(int x, int y) {

        if(x < 0 || y < 0 || x >= mapWidth || y >= mapHeight)
            return Tiles.voidTile;

        switch (tiles[x + y * mapWidth]) {
            case 0xfffca75d:
                return Tiles.dirtTile;
            case 0xffc17e47:
                return Tiles.mudTile;
            default:
                return Tiles.voidTile;
        }
    }

    public void scrollX(int xScroll) {
        this.xScroll += xScroll;
    }

    public void scrollY(int yScroll) {
        this.yScroll += yScroll;
    }
}
