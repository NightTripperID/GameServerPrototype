package demo.congratulations;

import demo.spritesheets.Sprites;
import gamestate.GameState;
import graphics.Screen;
import graphics.Sprite;

public class Congratulations extends GameState {

    private Sprite axeSprite = Sprites.AXE;

    @Override
    public void render(Screen screen) {
        super.render(screen);

        screen.fill(0x000088);

        final int axeScale = 4;

        int axeSpriteX = (getScreenWidth() / 2) - (axeSprite.getWidth() / 2) * axeScale;
        int axeSpriteY = (getScreenHeight() / 2) - (axeSprite.getHeight() / 2) * axeScale;

        screen.renderSprite(
                axeSpriteX,
                axeSpriteY,
                axeScale,
                axeSprite);

        String str1 = "congratulations, you vanquished the slime king.";
        String str2 = "you have earned your place in 8-bit valhalla.";

        screen.renderString5x5(
                (getScreenWidth() / 2) - (str1.length() * 5 / 2),
                axeSpriteY + axeSprite.getHeight() * axeScale + 8,
                0xffffff,
                str1
        );

        screen.renderString5x5(
                (getScreenWidth() / 2) - (str2.length() * 5 / 2),
                axeSpriteY + axeSprite.getHeight() * axeScale + 20,
                0xffffff,
                str2
        );
    }
}
