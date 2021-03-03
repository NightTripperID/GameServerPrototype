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

    public final void popGameState() {
        gameStateStack.pop().onDestroy();
    }

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

    public void addEntity(Entity entity) { // delegate to EntityProcessor
        entity.onCreate();
        activeGameState.getGameStateEntities().getPendingEntities().add(entity);
    }

    private void addPendingEntities() {
        activeGameState.getGameStateEntities().getEntities()
                .addAll(activeGameState.getGameStateEntities().getPendingEntities());
        activeGameState.getGameStateEntities().getPendingEntities().clear();
    }

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
