package demo.transition;

import com.sun.istack.internal.NotNull;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;

public class FadeOut extends Fade {

    @Override
    @SuppressWarnings("unchecked")
    public void update() {

        final int fadeRate = 6;

        for(int i = 0; i < pixels.length; i++) {
            if(pixels[i] == 0x000000)
                continue;

            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0x00ff00) >> 8;
            int b = pixels[i] & 0x0000ff;

            r -= fadeRate;
            g -= fadeRate;
            b -= fadeRate;

            pixels[i] = (r << 16) | (g << 8) |  b;

            if(pixels[i] < 0x000000)
                pixels[i] = 0x000000;

            if(pixels[i] == 0x000000)
                count++;
        }

        if(count == pixels.length) {

            Class<? extends GameState> nextGameState =
                    (Class<? extends GameState>) getIntent().getSerializableExtra("nextGameState");

            Bundle bundle = getIntent().getBundle();

            Intent intent = new Intent(nextGameState);

            intent.setBundle(bundle);

            swapGameState(intent);
        }

    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderPixels(pixels);
    }
}
