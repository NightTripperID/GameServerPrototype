package gamestate;

import com.sun.istack.internal.NotNull;
import exception.EmptyGSMException;

import java.util.Stack;

public class GameStateManager {

    private Stack<GameState> gameStateStack = new Stack<>();

    public void push(@NotNull GameState gs) {
        gameStateStack.push(gs);
    }

    public GameState pop() {

        if(gameStateStack.size() > 1)
            return gameStateStack.pop();
        else throw new EmptyGSMException();
    }

    public GameState peek() {
        return gameStateStack.peek();
    }

    public GameState swap(@NotNull GameState gs) {
        GameState old = gameStateStack.pop();
        gameStateStack.push(gs);
        return old;
    }
}
