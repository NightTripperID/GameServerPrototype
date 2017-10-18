package gamestate;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import demo.tile.Tile;
import entity.Entity;
import entity.Updatable;
import graphics.Screen;
import input.Keyboard;
import server.Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class GameState {

    private Server server;
    private Intent intent;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> pendingEntites = new ArrayList<>();

    private double xScroll, yScroll;

    private int mapWidth, mapHeight; // tile precision

    protected int tileSize, tileBitShift;

    private int[] mapTiles;
    private int[] triggerTiles;

    private Map<Integer, Runnable> triggers = new HashMap<>();

    public void onCreate(@NotNull Server server) {
        this.server = server;
    }

    public void onDestroy() {
    }

    protected void loadMapTiles(@NotNull String path) {
        loadTiles(path, mapTiles);
    }

    protected void loadTriggerTiles(@NotNull String path) {
        loadTiles(path, triggerTiles);
    }

    protected void loadTiles(@NotNull String filePath, @Nullable int[] dest) {
        try {
            System.out.println("Trying to load: " + filePath + "...");

            File file = new File(filePath);
            BufferedImage map = ImageIO.read(file);
            int[] pixels = new int[mapWidth * mapHeight];
            map.getRGB(0, 0, mapWidth, mapHeight, pixels, 0, mapWidth);
            System.arraycopy(pixels, 0, dest, 0, dest.length);
            System.out.println("Success!");
        } catch (IOException e) {
            System.out.println("failed...");
            e.printStackTrace();
        }
    }

    public void update() {
        addEntities();
        entities.forEach(Updatable::update);
        removeEntities();
    }

    public void render(@NotNull Screen screen) {
        renderTiles(screen);
        entities.forEach(e -> e.render(screen));
    }

    private void renderTiles(@NotNull Screen screen) {
        screen.setOffset(xScroll, yScroll);

        int x0 = (int) xScroll >> tileBitShift;
        int x1 = (((int) xScroll + screen.getWidth()) + tileSize) >> tileBitShift;
        int y0 = (int) yScroll >> tileBitShift;
        int y1 = (((int) yScroll + screen.getHeight()) + tileSize) >> tileBitShift;

        for (int y = y0; y < y1; y++)
            for (int x = x0; x < x1; x++)
                getMapTileObject(x, y).render(screen, x << tileBitShift, y << tileBitShift);
    }

    public final void pushGameState(@NotNull Intent intent) {
        server.pushGameState(intent);
    }

    public final void popGameState() {
        server.popGameState();
    }

    public final void swapGameState(@NotNull Intent intent) {
        server.swapGameState(intent);
    }

    @NotNull
    protected final Intent getIntent() {
        return intent;
    }

    public int getEntitiesSize() {
        return entities.size();
    }

    public Entity getEntity(int index) {
        return entities.get(index);
    }

    protected List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(@NotNull Entity entity) {
        pendingEntites.add(entity);
    }

    private void addEntities() {
        entities.addAll(pendingEntites);
        pendingEntites.clear();
    }

    private void removeEntities() {
        for(int i = 0; i < entities.size(); i++) {
            if(entities.get(i).removed())
                entities.remove(entities.get(i--));
        }
    }

    protected void initMap(int mapWidth, int mapHeight, @NotNull Tile.TileSize tileSize) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        mapTiles = new int[mapWidth * mapHeight];
        triggerTiles = new int[mapWidth * mapHeight];

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

    public final Keyboard getKeyboard() {
        return server.getKeyboard();
    }

    public final void setIntent(@NotNull Intent intent) {
        this.intent = intent;
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

    protected void setScrollX(int xScroll) {
        this.xScroll = xScroll;
    }

    protected void setScrollY(int yScroll) {
        this.yScroll = yScroll;
    }

    public Tile getMapTileObject(int x, int y) {
        return null;
    }

    public int getMapTile(int x, int y) {
        return mapTiles[x + y * mapWidth];
    }

    public void setMapTile(int x, int y, int col) {
        mapTiles[x + y * mapWidth] = col;
    }

    public int[] getMapTiles() {
        return mapTiles;
    }

    public int getTriggerTile(int x, int y) {
        return triggerTiles[x + y * mapWidth];
    }

    public void setTriggerTile(int x, int y, int col) {
        triggerTiles[x + y * mapWidth] = col;
    }

    protected int[] getTriggerTiles() {
        return triggerTiles;
    }

    public void putTrigger(int key, @NotNull Runnable trigger) {
        triggers.put(key, trigger);
    }

    public Runnable getTrigger(int x, int y) {
        int key = triggerTiles[x + y * mapWidth];
        return triggers.get(key);
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

    public int[] getScreenPixels() {
        return server.getScreenPixels();
    }
}
