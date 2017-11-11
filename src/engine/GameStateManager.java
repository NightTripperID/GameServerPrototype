package engine;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;

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
    void push(@NotNull GameState gs) {
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
    GameState swap(@NotNull GameState gs) {
        GameState old = gameStateStack.pop();
        gameStateStack.push(gs);
        return old;
    }
}