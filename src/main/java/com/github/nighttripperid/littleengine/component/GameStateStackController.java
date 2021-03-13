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

import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.entity.GameStateTransition;
import com.github.nighttripperid.littleengine.model.gamestate.GameState;
import com.github.nighttripperid.littleengine.model.gamestate.Intent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Stack;

@Slf4j
public class GameStateStackController {
    private final Stack<GameState> gameStateStack = new Stack<>();
    @Getter
    private GameState activeGameState;


    public final void pushGameState(Intent intent) {
        GameState gameState = buildGameState(intent);
        gameStateStack.push(gameState);
        activeGameState = gameStateStack.peek();
    }

    public final void popGameState() {
        gameStateStack.pop().onDestroy();
    }

    public final void swapGameState(Intent intent) {
        GameState gameState = buildGameState(intent);
        gameStateStack.pop().onDestroy();
        gameStateStack.push(gameState);
        activeGameState = gameStateStack.peek();
    }

    public final void performGameStateTransition(GameStateTransition gameStateTransition) {
        if (gameStateTransition != null)
            gameStateTransition.perform(this);
    }

    private GameState buildGameState(Intent intent) {
        try {
            GameState gameState = intent.getGameStateClass().newInstance();
            gameState.setIntent(intent);
            gameState.onCreate();
            gameState.getEntityData().getEntities().forEach(Entity::onCreate);
            gameState.getEntityData().getEntities().forEach(entity -> {
                if (entity.getInitGfxRoutine() != null) {
                    entity.getInitGfxRoutine().run(gameState.getEntityData().getSpriteMaps());
                }
            });
            gameState.getGameMap().getTileset().getAnimatedTiles().forEach(tile -> {
               tile.getInitGfxRoutine().run(gameState.getGameMap().getTileset().getSpriteMaps());
            });
            return gameState;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("Error swapping gameState: {} " + e.getMessage());
        }
        return null;
    }
}
