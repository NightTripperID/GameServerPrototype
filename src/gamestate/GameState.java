package gamestate;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import demo.tile.Tile;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import graphics.Screen;
import input.Keyboard;
import server.Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public abstract class GameState {

    private Server server;
    private Intent intent;

    private List<Entity> pendingEntites = new ArrayList<>();
    private List<Updatable> updatables = new ArrayList<>();
    private List<Renderable> renderables = new ArrayList<>();

    private double xScroll, yScroll;

    private int mapWidth, mapHeight; // tile precision

    private int tileSize, tileBitShift;

    private int[] mapTiles;
    private int[] triggerTiles;

    private Map<Integer, Runnable> triggers = new HashMap<>();

    private Random random = new Random();

    public void onCreate(@NotNull Server server) {
        this.server = server;
    }

    public void onDestroy() {
    }

    protected void loadMapTiles(@NotNull URL url) {
        loadTiles(url, mapTiles);
    }

    protected void loadTriggerTiles(@NotNull URL url) {
        loadTiles(url, triggerTiles);
    }

    protected void loadTiles(@NotNull URL url, @Nullable int[] dest) {
        try {
            System.out.println("Trying to load: " + url.toString() + "...");
            BufferedImage map = ImageIO.read(url);
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
        checkCollision();
        updatables.forEach(Updatable::update);
        removeEntities();
    }

    public void render(@NotNull Screen screen) {
        renderTiles(screen);
        renderables.forEach(r -> r.render(screen));
    }

    private void renderTiles(@NotNull Screen screen) {
        screen.setOffset(xScroll, yScroll);

        int x0 = (int) xScroll >> tileBitShift;
        int x1 = (((int) xScroll + screen.getWidth()) + tileSize) >> tileBitShift;
        int y0 = (int) yScroll >> tileBitShift;
        int y1 = (((int) yScroll + screen.getHeight()) + tileSize) >> tileBitShift;

        for (int y = y0; y < y1; y++)
            for (int x = x0; x < x1; x++)
                getMapTile(x, y).render(screen, x << tileBitShift, y << tileBitShift);
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

    public void addEntity(@NotNull Entity entity) {
        if(!(entity instanceof Updatable) && !(entity instanceof Renderable))
            throw new IllegalArgumentException("entity must implement either Updatable " +
                    "or Renderable to be added to GameState");
        pendingEntites.add(entity);
    }

    private void addEntities() {
        pendingEntites.forEach(entity -> {
            if(entity instanceof Updatable)
                updatables.add((Updatable) entity);
            if(entity instanceof Renderable)
                renderables.add((Renderable) entity);
        });
        pendingEntites.clear();
    }

    private void removeEntities() {
        for(int i = 0; i < updatables.size(); i++) {
            if(updatables.get(i).removed())
                updatables.remove(updatables.get(i--));
        }

        for(int i = 0; i < renderables.size(); i++) {
            if(renderables.get(i).removed())
                renderables.remove(renderables.get(i--));
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

    public final void setIntent(@NotNull Intent intent) {
        this.intent = intent;
    }

    public final Keyboard getKeyboard() {
        return server.getKeyboard();
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

    public Tile getMapTile(int x, int y) {
        return null;
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

    protected int getMapWidth() {
        return mapWidth;
    }

    protected int getMapHeight() {
        return mapHeight;
    }

    protected int[] getMapTiles() {
        return mapTiles;
    }

    public int[] getScreenPixels() {
        return server.getScreenPixels();
    }

    private void checkCollision() {
        for(int i = 0; i < updatables.size(); i++) {
            for(int k = i + 1; k < updatables.size(); k++) {
                if(updatables.get(i).collidesWith(updatables.get(k))) {
                    updatables.get(i).runCollision(updatables.get(k));
                    updatables.get(k).runCollision(updatables.get(i));
                }
            }

        }
    }
}
