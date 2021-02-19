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

package com.github.nighttripperid.littleengine.engine;

import com.github.nighttripperid.littleengine.gamestate.GameState;

import java.util.Stack;

/**
 * Wrapper that manages the pushing, popping, and swapping of GameStates to and from the GameState stack.
 */
class GameStateManager {

    private Stack<GameState> gameStateStack = new Stack<>();

    /**
     * Pushes a new GameState onto gameStateStack.
     * @param gs the new GameState to push onto gameStateStack.
     */
    void push(GameState gs) {
        gameStateStack.push(gs);
    }

    /**
     * Pops the top GameState from the gameStateStack.
     * @return the GameState popped from the gameStateStack.
     */
    GameState pop() {
        return gameStateStack.pop();
    }

    /**
     * Returns the top GameState on gameStateStack.
     * @return The top GameState on the gameStateStack,
     */
    GameState peek() {
        return gameStateStack.peek();
    }

    /**
     * Pops the top GameState from the gameStateStack.
     * @param gs The new GameState to push onto gameStateStack.
     * @return The old GameState that was popped from gameStateStack.
     */
    GameState swap(GameState gs) {
        GameState old = gameStateStack.pop();
        gameStateStack.push(gs);
        return old;
    }
}