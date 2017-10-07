package gamestate;

import com.sun.istack.internal.NotNull;
import demo.tile.Tile;
import demo.tile.Tiles;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import graphics.Screen;
import input.Keyboard;
import input.Mouse;
import server.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GameState {

    private Server server;
    private Intent intent;

    private List<Updatable> updatables = new ArrayList<>();
    private List<Renderable> renderables = new ArrayList<>();

    private double xScroll, yScroll;

    private int mapWidth, mapHeight; // tile precision

    private int tileSize, tileBitShift;

    private int[] tiles;

    private Random random = new Random();

    public void onCreate(@NotNull Server server) {
        this.server = server;
    }

    public void onDestroy() {
    }

    private void generateTiles() {
        for(int y = 0; y < mapHeight; y++)
            for(int x = 0; x < mapWidth; x++)
                tiles[x + y * mapWidth] = random.nextInt(2);
    }

    protected void loadTiles(@NotNull URL url) {
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

    public void update() {
        for(int i = 0; i < updatables.size(); i++)
            updatables.get(i).update();
    }

    public void render(@NotNull Screen screen) {

        renderTiles(screen);

        for(int i = 0; i < renderables.size(); i++)
            renderables.get(i).render(screen);
    }

    protected void renderTiles(@NotNull Screen screen) {
        screen.setOffset(xScroll, yScroll);

        int x0 = (int) xScroll >> tileBitShift;
        int x1 = (((int) xScroll + screen.getWidth()) + tileSize) >> tileBitShift;
        int y0 = (int) yScroll >> tileBitShift;
        int y1 = (((int) yScroll + screen.getHeight()) + tileSize) >> tileBitShift;

        for (int y = y0; y < y1; y++)
            for (int x = x0; x < x1; x++)
                getTile(x, y).render(screen, x << tileBitShift, y << tileBitShift);
    }

    protected final void startGameState(@NotNull Intent intent) {
        server.pushGameState(intent);
    }

    protected final void finish() {
        server.popGameState();
    }

    protected final void swapGameState(@NotNull Intent intent) {
        server.swapGameState(intent);
    }

    @NotNull
    protected final Intent getIntent() {
        return intent;
    }

    public void addEntity(@NotNull Entity entity) {

        if (entity instanceof Updatable)
            updatables.add((Updatable) entity);

        if (entity instanceof Renderable)
            renderables.add((Renderable) entity);
    }

    public void removeEntity(@NotNull Entity entity) {

        if(entity instanceof Updatable)
            updatables.remove(entity);

        if(entity instanceof Renderable)
            renderables.remove(entity);
    }

    protected void initMap(int mapWidth, int mapHeight, @NotNull Tile.TileSize tileSize) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        tiles = new int[mapWidth * mapHeight];

        this.tileSize = tileSize.get();

        switch(this.tileSize) {
            case 8:
                tileBitShift = 3;
                break;
            case 16:
                tileBitShift = 4;
                break;
            case 32:
                tileBitShift = 5;
                break;
            case 64:
                tileBitShift = 6;
                break;
            case 128:
                tileBitShift = 7;
                break;
            default:
                throw new IllegalArgumentException("TileSize is invalid.");
        }
    }

    public final void setIntent(@NotNull Intent intent) {
        this.intent = intent;
    }

    public final void setCustomMouseCursor(@NotNull Image image, @NotNull Point cursorHotspot,
                                           @NotNull String name) {
        server.setCustomMouseCursor(image, cursorHotspot, name);
    }

    public final Keyboard getKeyboard() {
        return server.getKeyboard();
    }

    public final Mouse getMouse() {
        return server.getMouse();
    }

    public void scrollX(double xScroll) {
        this.xScroll += xScroll;
    }

    public void scrollY(double yScroll) {
        this.yScroll += yScroll;
    }

    public double getScrollX() {
        return xScroll;
    }

    public double getScrollY() {
        return yScroll;
    }

    public void setScrollX(int xScroll) {
        this.xScroll = xScroll;
    }

    public void setScrollY(int yScroll) {
        this.yScroll = yScroll;
    }

    public Tile getTile(int x, int y) {
        return null;
    }

    public int getScreenWidth() {
        return server.getScreenWidth();
    }

    public int getScreenHeight() {
        return server.getScreenHeight();
    }

    public int getScreenScale() {
        return server.getScreenScale();
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    protected int[] getTiles() {
        return tiles;
    }
}
