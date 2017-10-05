package demo.player;

import demo.mob.Mob;
import demo.spritesheets.AnimSprites;
import gamestate.GameState;
import graphics.Screen;

import java.awt.event.MouseEvent;

public class Player extends Mob {

    public Player(int x, int y) {
        super(x, y, 1, 1, 16, 16);
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
        currSprite = AnimSprites.PLAYER_DOWN;
        currState = new PlayerStateStanding(this);
    }

    @Override
    public void render(Screen screen) {
        screen.renderSprite(x, y, getWidth(), getHeight(), currSprite.getSprite().pixels);
    }
}
