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

    // TODO: delegate entity methods to EntityProcessor (maybe)
    public void addEntity(Entity entity) {
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
