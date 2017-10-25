package demo.congratulations;

import gamestate.GameState;
import graphics.Screen;

public class Congratulations extends GameState {

    @Override
    public void render(Screen screen) {
        super.render(screen);

        screen.fill(0x000088);
        screen.renderString8x8(0, 0, 0xffffff, "congratulations, faggot.");
    }
}
