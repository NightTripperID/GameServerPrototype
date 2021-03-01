package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.gamestate.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameState;
import com.github.nighttripperid.littleengine.model.gamestate.Intent;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class GameStateUpdater {

    private Stack<GameState> gameStateStack = new Stack<>();
    private GameState activeGameState;

    /**
     * Called when Engine executes the update loop. Any pending entities are added to the Entity list. The update method
     * of each Entity is called. Finally, any entities flagged for removal are removed.
     */
    public void update() {
        addPendingEntities();
        activeGameState.getEntities().sort(Entity::compareTo);
        activeGameState.getEntities().forEach(this::runEntityScript);
        removeMarkedEntities();
    }

    void runEntityScript(Entity entity) {
        // TODO: implement groovy integration for entity updates (maybe)
        entity.runScript(activeGameState.getGameMap());
    }

    /**
     * Pushes a new GameState onto the GameStateManager's GameState stack.
     * @param intent The intent that contains the class metadata of the new GameState.
     */
    public final void pushGameNewState(Intent intent) {

        try {
            GameState gameState = intent.getGameStateClass().newInstance();
            gameState.setIntent(intent);
            gameState.onCreate();
            gameState.getEntities().forEach(Entity::onCreate);
            gameStateStack.push(gameState);
            activeGameState = gameStateStack.peek();
        } catch(InstantiationException | IllegalAccessException e) {
            log.error("Error pushing gameState: {}", e.getMessage());
        }
    }

    /**
     * Pops this GameState from the GameStateManager's GameState stack.
     */
    public final void popGameState() {
        gameStateStack.pop().onDestroy();
    }

    /**
     * Pops this GameState from the GameStateManager's stack and pushes a new GameState in its place.
     * @param intent The intent containing the class metadata of the new GameState.
     */
    public final void swapGameState(Intent intent) {
        try {
            GameState gameState = intent.getGameStateClass().newInstance();
            gameState.setIntent(intent);
            gameState.onCreate();
            gameStateStack.pop().onDestroy();
            gameStateStack.push(gameState);
        } catch(InstantiationException | IllegalAccessException e) {
            log.error("Error swapping gameState: {} " + e.getMessage());
        }
    }

    /**
     * Adds a new entity to ArrayList pendingEntities. Pending Entites are added to ArraList entities on the next
     * update cycle. Runs Entity#onCreate.
     * @param entity The new entity to add.
     */
    public void addEntity(Entity entity) { // delegate to EntityProcessor
        entity.onCreate();
        activeGameState.getPendingEntities().add(entity);
    }

    /**
     * Adds all pending entities to ArrayList entities during the last update cycle.
     * Clears ArrayList pendingEntities once all pending entities are added.
     */
    private void addPendingEntities() {
        activeGameState.getEntities().addAll(activeGameState.getPendingEntities());
        activeGameState.getPendingEntities().clear();
    }

    /**
     * Removes all entities that were marked for removal in this update cycle. Runs marked entities' onDestroy method.
     */
    private void removeMarkedEntities() {
        for(int i = 0; i < activeGameState.getEntities().size(); i++) {
            if(activeGameState.getEntities().get(i).isRemoved()) {
                activeGameState.getEntities().get(i).onDestroy();
                activeGameState.getEntities().remove(activeGameState.getEntities().get(i--));
            }
        }
    }

    public GameState getActiveGameState() {
        return activeGameState;
    }

//    /**
//     * Returns this GameState's Intent (It should be the intent from which this GameState was created).
//     * @return the GameState's Intent.
//     */
//    protected final Intent getIntent() {
//        return intent;
//    }
//
//    /**
//     * Sets this GameState's intent. (It should be the intent from which this GameState was created. The engine
//     * sets this, and the user should probably never call this method).
//     * @param intent The Intent to set.
//     */
//    public final void setIntent(Intent intent) {
//        this.intent = intent;
//    }
//
//    /**
//     * Increments scrollX by the given value.
//     * @param xScroll The value by which to increment scrollX.
//     */
//    public void scrollX(double xScroll) {
//        this.xScroll += xScroll;
//    }
//
//    /**
//     * Increments scrollY by the given value.
//     * @param yScroll The value by which to increment scrollY.
//     */
//    public void scrollY(double yScroll) {
//        this.yScroll += yScroll;
//    }
//
//    /**
//     * Returns xScroll
//     * @return xScroll
//     */
//    public double getScrollX() {
//        return xScroll;
//    }
//
//    /**
//     * Returns yScroll
//     * @return yscroll
//     */
//    public double getScrollY() {
//        return yScroll;
//    }
//
//    /**
//     * Sets xScroll to the given value.
//     * @param xScroll The value to which to set xScroll.
//     */
//    protected void setScrollX(int xScroll) {
//        this.xScroll = xScroll;
//    }
//
//    /**
//     * Sets yScroll to the given value.
//     * @param yScroll The value to which to set yScroll.
//     */
//    protected void setScrollY(int yScroll) {
//        this.yScroll = yScroll;
//    }
//
//
//    /**
//     * Returns the color value associated with the map tile at the given map coordinates.
//     * @param x The tile's x coordinate in tile precision.
//     * @param y The tile's y coordinate in tile precision.
//     * @return The color value associated with the Tile residing at the given coordinates.
//     */
//    public int getMapTile(int x, int y) {
//        return mapTiles[x + y * mapWidth];
//    }
//
//    /**
//     * Sets the color value associated with the map tile at the given map coordinates.
//     * @param x The tile's x coordinate in tile precision.
//     * @param y The tile's y coordinate in tile precision.
//     * @param col The new color to store at the given coordinates.
//     */
//    public void setMapTile(int x, int y, int col) {
//        mapTiles[x + y * mapWidth] = col;
//    }
//
//    /**
//     * Returns mapTiles
//     * @return mapTiles
//     */
//    public int[] getMapTiles() {
//        return mapTiles;
//    }
//
//    /**
//     * Returns the color value associated with the trigger tile at the given map coordinates.
//     * @param x The tile's x coordinate in tile precision.
//     * @param y The tile's y coordinate in tile precision.
//     * @return The color value associated with the Tile residing at the given coordinates.
//     */
//    public int getTriggerTile(int x, int y) {
//        return triggerTiles[x + y * mapWidth];
//    }
//
//    /**
//     * Sets the color value associated with the trigger tile at the given map coordinates.
//     * @param x The tile's x coordinate in tile precision.
//     * @param y The tile's y coordinate in tile precision.
//     * @param col The new color to store at the given coordinates.
//     */
//    public void setTriggerTile(int x, int y, int col) {
//        triggerTiles[x + y * mapWidth] = col;
//    }
//
//    /**
//     * Returns triggerTiles
//     * @return triggerTiles
//     */
//    protected int[] getTriggerTiles() {
//        return triggerTiles;
//    }
//
//    /**
//     * Puts a Trigger object in HashMap triggers
//     * @param key The Trigger's hex value RGB color key (i.e. 0xff0000)
//     * @param trigger The Trigger object associated with the color
//     */
//    public void putTrigger(int key, Trigger trigger) {
//        triggers.put(key, trigger);
//    }
//
//    /**
//     * Gets a Trigger object from HashMap triggers
//     * @param x the x coordinate of the Trigger
//     * @param y the y coordinate of the Trigger
//     * @return the Trigger object at the given coordinates.
//     */
//    public Trigger getTrigger(int x, int y) {
//        int key = triggerTiles[x + y * mapWidth];
//        return triggers.get(key);
//    }
//
//    /**
//     * Returns screenWidth
//     * @return screenWidth
//     */
//    public int getScreenWidth() {
//        return engine.getScreenWidth();
//    }
//
//    /**
//     * Returns screenHeight
//     * @return screenHeight
//     */
//    public int getScreenHeight() {
//        return engine.getScreenHeight();
//    }
//
//    /**
//     * Returns screenScale
//     * @return screenScale
//     */
//    public int getScreenScale() {
//        return engine.getScreenScale();
//    }
//
//    /**
//     * Returns mapWidth
//     * @return mapWidth
//     */
//    public int getMapWidth() {
//        return mapWidth;
//    }
//
//    /**
//     * Returns mapHeight
//     * @return mapHeight
//     */
//    public int getMapHeight() {
//        return mapHeight;
//    }
//
//    /**
//     * Returns the screen pixels.
//     * @return int[] pixels
//     */
//    public int[] getScreenPixels() {
//        return engine.getScreenPixels();
//    }
}
