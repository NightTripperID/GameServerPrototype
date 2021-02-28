/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.github.nighttripperid.littleengine.deprecated.gamestate;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import com.github.nighttripperid.littleengine.deprecated.engine.Engine;
import com.github.nighttripperid.littleengine.deprecated.engine.Screen;
import com.github.nighttripperid.littleengine.deprecated.entity.Entity;
import com.github.nighttripperid.littleengine.deprecated.graphics.Renderable;
import com.github.nighttripperid.littleengine.model.graphics.TILED_TileMap;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Abstract object representing a GameState.
 */
public abstract class GameState implements Updatable, Renderable {

    private Engine engine;
    private Intent intent;

    private List<Entity> entities = new ArrayList<>();
    private List<Entity> pendingEntities = new ArrayList<>();

    private double xScroll, yScroll;

    private int mapWidth, mapHeight; // tile precision

    protected int tileSize, tileBitShift;

    private int[] mapTiles;
    private int[] triggerTiles;

    private Map<Integer, Trigger> triggers = new HashMap<>();

    /**
     * Called when Engine instantiates this GameState. Provides a reference to the sever for engine callbacks.
     * @param engine the engine that is instantiating the GameState and available for callback. Can be overridden
     * so GameState can perform additional actions upon being created.
     */
    public void onCreate(Engine engine) {
        this.engine = engine;
    }

    /**
     * Called when Engine disposes of this GameState. Can be overridden so GameState can perform actions upon being
     * destroyed.
     */
    public void onDestroy() {
    }

    /**
     * Called when Engine executes the update loop. Any pending entities are added to the Entity list. The update method
     * of each Entity is called. Finally, any entities flagged for removal are removed.
     */
    public void update() { // delegate to EntityProcessor
        addPendingEntities();
        entities.sort(Entity::compareTo);
        entities.forEach(Entity::update);
        removeMarkedEntities();
    }

    /**
     * Called when Engine executes the Renderable loop. The Renderable method of each Tile is called. Also, the Renderable method
     * of each Entity is called.
     * @param screen the screen object to which Tiles and Entities are rendered
     */
    public void render(Screen screen) { // delegate to ScreenBufferUtil
        renderTiles(screen);
        entities.forEach(e -> e.render(screen));
    }

    protected void loadMapTilesJson(URL jsonUrl) { // delegate to MapLoader
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(jsonUrl.openStream()))) {
            JsonObject jsonObject = (JsonObject) Jsoner.deserialize(reader);
            Mapper mapper = new DozerBeanMapper();
            TILED_TileMap tilEdTileMap = mapper.map(jsonObject, TILED_TileMap.class);

            this.mapWidth = tilEdTileMap.getWidth();
            this.mapHeight = tilEdTileMap.getHeight();
            this.tileSize = tilEdTileMap.getTilewidth();
            this.tileBitShift = (int) (Math.log(tileSize) / Math.log(2));

            this.mapTiles = new int[this.mapWidth * this.mapHeight];
            this.triggerTiles = new int[tilEdTileMap.getWidth() * tilEdTileMap.getHeight()];
            System.arraycopy(tilEdTileMap.getLayers().stream().findFirst().orElse(null)
                            .getData(), 0,  this.mapTiles, 0, this.mapTiles.length);

        } catch (IOException | JsonException e) {
            System.out.println("error loading " + jsonUrl);
        }
    }

    /**
     * Loads map tiles from a png file to an integer array. Each integer represents a pixel on the tilemap, which in turn
     * represents a Tile that will be rendered by the GameState to the Screen.
     * @param path the filepath of the png file specified by the caller.
     */
    @Deprecated
    protected void loadMapTiles(String path) {
        loadTiles(path, mapTiles);
    }

    @Deprecated
    protected void loadMapTiles(URL url) {
        loadTiles(url, mapTiles);
    }

    /**
     * Loads trigger tiles from a png file to an integer array. Each integer represents a pixel on the tilemap,
     * which in turn represents a Trigger that is loaded into a HashMap called 'triggers'. The pixel color acts as
     * the HashMap's key, which coincides with a value that is a Runnable. Generally, the trigger map will be identical
     * to the tile map, but locations with interactive triggers will be represented by primary colors such as red,
     * blue, green, yellow, etc, so they stand out visually on the map. When the player interacts with the trigger in game,
     * it is called by color, executing the runnable piece of code via lambda or anonymous inner class.
     * @param path the filepath of the png file specified by the caller.
     */
    @Deprecated
    protected void loadTriggerTiles(String path) {
        loadTiles(path, triggerTiles);
    }

    @Deprecated
    protected void loadTriggerTiles(URL url) {
        loadTiles(url, triggerTiles);
    }

    /**
     * Helper method that loads a tile map into an integer array representing a tileset.
     * @param filePath // the filepath specified by the caller
     * @param dest // the destination integer array that the tileset will be loaded into
     */
    @Deprecated
    protected void loadTiles(String filePath, int[] dest) {
        try {
            System.out.println("Trying to load file path: " + filePath + "...");

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

    @Deprecated
    protected void loadTiles(URL url, int[] dest) {
        try {
            System.out.println("Trying to load url: " + url + "...");

            BufferedImage map = ImageIO.read(url);
            int[] pixels = new int[mapWidth * mapHeight];
            map.getRGB(0, 0, mapWidth, mapHeight, pixels, 0, mapWidth);
            System.arraycopy(pixels, 0, dest, 0, dest.length);
            System.out.println("Success!");

        } catch(IOException e) {
            System.out.println("failed...");
            e.printStackTrace();
        }
    }

    /**
     * Renders map Tiles onto the given Screen.
     * @param screen The Screen on which to Renderable the Tiles.
     */
    private void renderTiles(Screen screen) { // delegate to ScreenBufferUtil
        screen.setScroll(xScroll, yScroll);

        int x0 = (int) xScroll >> tileBitShift;
        int x1 = (((int) xScroll + screen.getWidth()) + tileSize) >> tileBitShift;
        int y0 = (int) yScroll >> tileBitShift;
        int y1 = (((int) yScroll + screen.getHeight()) + tileSize) >> tileBitShift;

        for (int y = y0; y < y1; y++)
            for (int x = x0; x < x1; x++) {
                if(getMapTiles() != null)
                    getMapTileObject(x, y).render(screen, x << tileBitShift, y << tileBitShift);
            }
    }

    /**
     * Pushes a new GameState onto the GameStateManager's GameState stack.
     * @param intent The intent that contains the class metadata of the new GameState.
     */
    public final void pushGameState(Intent intent) {
        engine.pushGameState(intent);
    }

    /**
     * Pops this GameState from the GameStateManager's GameState stack.
     */
    public final void popGameState() {
        engine.popGameState();
    }

    /**
     * Pops this GameState from the GameStateManager's stack and pushes a new GameState in its place.
     * @param intent The intent containing the class metadata of the new GameState.
     */
    public final void swapGameState(Intent intent) {
        engine.swapGameState(intent);
    }

    /**
     * Returns the size of AraayList entities.
     * @return the size of ArrayList entities.
     */
    public int getEntitiesSize() {
        return entities.size();
    }

    /**
     * Returns the entity residing at the given index of ArrayList entities.
     * @param index the index of the desired entity.
     * @return the Entity residing at the given index.
     */
    public Entity getEntity(int index) {
        return entities.get(index);
    }

    /**
     * Adds a new entity to ArrayList pendingEntities. Pending Entites are added to ArraList entities on the next
     * update cycle. Runs Entity#onCreate.
     * @param entity The new entity to add.
     */
    public void addEntity(Entity entity) { // delegate to EntityProcessor
        entity.onCreate(this);
        pendingEntities.add(entity);
    }

    /**
     * Adds all pending entities to ArrayList entities during the last update cycle.
     * Clears ArrayList pendingEntities once all pending entities are added.
     */
    private void addPendingEntities() { // delegate to EntityProcessor
        entities.addAll(pendingEntities);
        pendingEntities.clear();
    }

    /**
     * Removes all entities that were marked for removal in this update cycle. Runs marked entities' onDestroy method.
     */
    private void removeMarkedEntities() { // delegate to EntityProcessor
        for(int i = 0; i < entities.size(); i++) {
            if(entities.get(i).removed()) {
                entities.get(i).onDestroy();
                entities.remove(entities.get(i--));
            }
        }
    }

    /**
     * Initializes the tileMap and triggerMap with the given dimensions. Sets the bitshift value based on given tileSize
     * for rendering operations.
     * @param mapWidth The given map width in tile precision.
     * @param mapHeight The given map height in tile precision.
     * @param tileSize The given tile size in pixel precision.
     */
    @Deprecated
    protected void initMap(int mapWidth, int mapHeight, Tile.TileSize tileSize) { // delegate to MapLoader, convert to logarithmic function
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

    /**
     * Returns this GameState's Intent (It should be the intent from which this GameState was created).
     * @return the GameState's Intent.
     */
    protected final Intent getIntent() {
        return intent;
    }

    /**
     * Sets this GameState's intent. (It should be the intent from which this GameState was created. The engine
     * sets this, and the user should probably never call this method).
     * @param intent The Intent to set.
     */
    public final void setIntent(Intent intent) {
        this.intent = intent;
    }

    /**
     * Increments scrollX by the given value.
     * @param xScroll The value by which to increment scrollX.
     */
    public void scrollX(double xScroll) {
        this.xScroll += xScroll;
    }

    /**
     * Increments scrollY by the given value.
     * @param yScroll The value by which to increment scrollY.
     */
    public void scrollY(double yScroll) {
        this.yScroll += yScroll;
    }

    /**
     * Returns xScroll
     * @return xScroll
     */
    public double getScrollX() {
        return xScroll;
    }

    /**
     * Returns yScroll
     * @return yscroll
     */
    public double getScrollY() {
        return yScroll;
    }

    /**
     * Sets xScroll to the given value.
     * @param xScroll The value to which to set xScroll.
     */
    protected void setScrollX(int xScroll) {
        this.xScroll = xScroll;
    }

    /**
     * Sets yScroll to the given value.
     * @param yScroll The value to which to set yScroll.
     */
    protected void setScrollY(int yScroll) {
        this.yScroll = yScroll;
    }

    public Tile getMapTileObject(int x, int y) {
        return null;
    }

    /**
     * Returns the color value associated with the map tile at the given map coordinates.
     * @param x The tile's x coordinate in tile precision.
     * @param y The tile's y coordinate in tile precision.
     * @return The color value associated with the Tile residing at the given coordinates.
     */
    public int getMapTile(int x, int y) {
        return mapTiles[x + y * mapWidth];
    }

    /**
     * Sets the color value associated with the map tile at the given map coordinates.
     * @param x The tile's x coordinate in tile precision.
     * @param y The tile's y coordinate in tile precision.
     * @param col The new color to store at the given coordinates.
     */
    public void setMapTile(int x, int y, int col) {
        mapTiles[x + y * mapWidth] = col;
    }

    /**
     * Returns mapTiles
     * @return mapTiles
     */
    public int[] getMapTiles() {
        return mapTiles;
    }

    /**
     * Returns the color value associated with the trigger tile at the given map coordinates.
     * @param x The tile's x coordinate in tile precision.
     * @param y The tile's y coordinate in tile precision.
     * @return The color value associated with the Tile residing at the given coordinates.
     */
    public int getTriggerTile(int x, int y) {
        return triggerTiles[x + y * mapWidth];
    }

    /**
     * Sets the color value associated with the trigger tile at the given map coordinates.
     * @param x The tile's x coordinate in tile precision.
     * @param y The tile's y coordinate in tile precision.
     * @param col The new color to store at the given coordinates.
     */
    public void setTriggerTile(int x, int y, int col) {
        triggerTiles[x + y * mapWidth] = col;
    }

    /**
     * Returns triggerTiles
     * @return triggerTiles
     */
    protected int[] getTriggerTiles() {
        return triggerTiles;
    }

    /**
     * Puts a Trigger object in HashMap triggers
     * @param key The Trigger's hex value RGB color key (i.e. 0xff0000)
     * @param trigger The Trigger object associated with the color
     */
    public void putTrigger(int key, Trigger trigger) {
        triggers.put(key, trigger);
    }

    /**
     * Gets a Trigger object from HashMap triggers
     * @param x the x coordinate of the Trigger
     * @param y the y coordinate of the Trigger
     * @return the Trigger object at the given coordinates.
     */
    public Trigger getTrigger(int x, int y) {
        int key = triggerTiles[x + y * mapWidth];
        return triggers.get(key);
    }

    /**
     * Returns screenWidth
     * @return screenWidth
     */
    public int getScreenWidth() {
        return engine.getScreenWidth();
    }

    /**
     * Returns screenHeight
     * @return screenHeight
     */
    public int getScreenHeight() {
        return engine.getScreenHeight();
    }

    /**
     * Returns screenScale
     * @return screenScale
     */
    public int getScreenScale() {
        return engine.getScreenScale();
    }

    /**
     * Returns mapWidth
     * @return mapWidth
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * Returns mapHeight
     * @return mapHeight
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     * Returns the screen pixels.
     * @return int[] pixels
     */
    public int[] getScreenPixels() {
        return engine.getScreenPixels();
    }
}
