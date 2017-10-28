package gamestate;

import com.sun.istack.internal.NotNull;
import exception.EmptyGSMException;

import java.util.Stack;

/**
 * Wrapper that manages the pushing, popping, and swapping of GameStates to and from the GameState stack.
 */
public class GameStateManager {

    private Stack<GameState> gameStateStack = new Stack<>();

    /**
     * Pushes a new GameState onto gameStateStack.
     * @param gs the new GameState to push onto gameStateStack.
     */
    public void push(@NotNull GameState gs) {
        gameStateStack.push(gs);
    }

    /**
     * Pops the top GameState from the gameStateStack.
     * @return the GameState popped from the gameStateStack.
     */
    public GameState pop() {

        if(gameStateStack.size() > 1)
            return gameStateStack.pop();
        else throw new EmptyGSMException();
    }

    /**
     * Returns the top GameState on gameStateStack.
     * @return The top GameState on the gameStateStack,
     */
    public GameState peek() {
        return gameStateStack.peek();
    }

    /**
     * Pops the top GameState from the gameStateStack.
     * @param gs The new GameState to push onto gameStateStack.
     * @return The old GameState that was popped from gameStateStack.
     */
    public GameState swap(@NotNull GameState gs) {
        GameState old = gameStateStack.pop();
        gameStateStack.push(gs);
        return old;
    }
}
