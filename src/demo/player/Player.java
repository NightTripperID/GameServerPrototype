package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.spritesheets.PlayerSprites;
import gamestate.GameState;
import graphics.Screen;

public class Player extends Mob {

    public Player(int x, int y) {
        super(x, y, 1, 1, 16, 16);
    }

    @Override
    public void initialize(@NotNull GameState gameState) {
        super.initialize(gameState);
        currSprite = PlayerSprites.PLAYER_DOWN;
        currState = new PlayerStateStanding(this);
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderSprite(x, y, currSprite.getSprite());
    }
}
