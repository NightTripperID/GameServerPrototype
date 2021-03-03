package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.gamestate.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameState;
import com.github.nighttripperid.littleengine.model.gamestate.Intent;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class GameStateUpdater {

    private final Stack<GameState> gameStateStack = new Stack<>();
    private GameState activeGameState;

    /**
     * Called when Engine executes the update loop. Any pending entities are added to the Entity list. The update method
     * of each Entity is called. Finally, any entities flagged for removal are removed.
     */
    public void update() {
        addPendingEntities();
        activeGameState.getGameStateEntities().getEntities().sort(Entity::compareTo);
        activeGameState.getGameStateEntities().getEntities().forEach(this::runBehaviorScript);
        removeMarkedEntities();
    }

    void runBehaviorScript(Entity entity) {
        // TODO: implement groovy integration for entity updates (maybe)
        entity.getBehaviorScript().run(activeGameState.getGameMap());
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
            gameState.getGameStateEntities().getEntities().forEach(Entity::onCreate);
            gameState.getGameStateEntities().getEntities().forEach(entity -> {
                if (entity.getInitGFX() != null) {
                    entity.getInitGFX().run(gameState.getGameStateEntities().getEntityGFX());
                }
            });
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
        activeGameState.getGameStateEntities().getPendingEntities().add(entity);
    }

    /**
     * Adds all pending entities to ArrayList entities during the last update cycle.
     * Clears ArrayList pendingEntities once all pending entities are added.
     */
    private void addPendingEntities() {
        activeGameState.getGameStateEntities().getEntities()
                .addAll(activeGameState.getGameStateEntities().getPendingEntities());
        activeGameState.getGameStateEntities().getPendingEntities().clear();
    }

    /**
     * Removes all entities that were marked for removal in this update cycle. Runs marked entities' onDestroy method.
     */
    private void removeMarkedEntities() {
        for(int i = 0; i < activeGameState.getGameStateEntities().getEntities().size(); i++) {
            if(activeGameState.getGameStateEntities().getEntities().get(i).isRemoved()) {
                activeGameState.getGameStateEntities().getEntities().get(i).onDestroy();
                activeGameState.getGameStateEntities().getEntities()
                        .remove(activeGameState.getGameStateEntities().getEntities().get(i--));
            }
        }
    }

    public GameState getActiveGameState() {
        return activeGameState;
    }
}
