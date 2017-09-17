package exception;

public class EmptyGSMException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Game State Manager does not contain a GameState. You popped the last GameState in the stack!";
    }
}
