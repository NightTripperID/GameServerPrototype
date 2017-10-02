package demo.mob;

import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;

import java.io.Serializable;

public class Player extends Mob implements Serializable {

    public static final long serialVersionUID = 201709271703L;

    private AnimSprite playerUp = new AnimSprite(SpriteSheets.PLAYER_UP, 16, 16, 4);
    private AnimSprite playerDown = new AnimSprite(SpriteSheets.PLAYER_DOWN, 16, 16, 4);
    private AnimSprite playerLeft = new AnimSprite(SpriteSheets.PLAYER_LEFT, 16, 16, 4);
    private AnimSprite playerRight = new AnimSprite(SpriteSheets.PLAYER_RIGHT, 16, 16, 4);

    private AnimSprite currSprite;

    public Player(int x, int y) {
        super(x, y, 0, 0, 8, 8);
        playerUp.setFrameRate(10);
        playerDown.setFrameRate(10);
        playerLeft.setFrameRate(10);
        playerRight.setFrameRate(10);

        currSprite = playerDown;
    }


    @Override
    public void onUpdate() {
        currSprite.update();

        if(gameState.getKeyboard().upHeld) {
            currSprite = playerUp;
        }

        if(gameState.getKeyboard().downHeld) {
            currSprite = playerDown;
        }

        if(gameState.getKeyboard().leftHeld) {
            currSprite = playerLeft;
        }

        if(gameState.getKeyboard().rightHeld) {
            currSprite = playerRight;
        }
    }

    @Override
    public void onRender(Screen screen) {
        screen.renderSprite(x, y, 16, 16, currSprite.getSprite().pixels);
    }
}
