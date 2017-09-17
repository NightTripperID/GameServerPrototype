package test.gamestates;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import graphics.Screen;
import server.Server;

public class PauseMenu extends GameState {

    public PauseMenu(@NotNull Server server) {
        super(server);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onUpdate() {
        if(getKeyboard().enterPressed)
            finish();
    }

    @Override
    public void onRender(@NotNull Screen screen) {

        int boxW = 50;
        int boxH = 50;

        int x = (getScreenWidth() >> 1) - (boxW >> 1);
        int y = (getScreenHeight() >> 1) - (boxH >> 1);

        screen.drawRect(x, y, boxW, boxH, 0xffff00);

        String text = "pause menu";


        int textX = x + boxW - ((text.length() >> 2) << 3);
        int textY = y + boxH - ((text.length() >> 2) << 3);

        screen.renderString8x8(textX, textY, 0x0000ff, text);
    }

    @Override
    public void onDestroy() {

    }
}
