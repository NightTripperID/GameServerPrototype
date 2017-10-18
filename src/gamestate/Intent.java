package gamestate;

public class Intent extends Bundle {

    private Class<? extends GameState> gameStateClass;

    public Intent(Class<? extends  GameState> gameStateClass) {
        this.gameStateClass = gameStateClass;
    }

    public final Class<? extends GameState> getGsc() {
        return gameStateClass;
    }
}
