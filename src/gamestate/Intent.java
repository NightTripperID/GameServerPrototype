package gamestate;

import com.sun.istack.internal.NotNull;

public class Intent extends Bundle {

    private Class<? extends GameState> gameStateClass;

    private Bundle bundle;

    public Intent(Class<? extends  GameState> gameStateClass) {
        this.gameStateClass = gameStateClass;
    }

    public final Class<? extends GameState> getGsc() {
        return gameStateClass;
    }

    public void setBundle(@NotNull Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }
}
