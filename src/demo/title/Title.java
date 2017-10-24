package demo.title;

import demo.area.Area_1_1;
import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import graphics.Sprite;
import server.Server;

public class Title extends GameState {

    private Sprite titleSprite = Sprites.TITLE_SPRITE;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);
    }

    @Override
    public void update() {
        super.update();
        if(getKeyboard().enterPressed || getKeyboard().spacePressed)
            startDemo();
    }

    @Override
    public void render(Screen screen) {

        final int scale = 3;

        screen.fill(0xffffff);

        screen.renderSprite(
                (getScreenWidth() / 2) - (titleSprite.getWidth() / 2) * scale,
                (getScreenHeight() / 2) - (titleSprite.getHeight() / 2) * scale,
                scale,
                titleSprite);
    }

    private void startDemo() {
        Bundle bundle = new Bundle();
        bundle.putExtra("tileCoord", new TileCoord(14, 17, 16));
        Player player = new Player(0, 0);
        player.inventory.add("potion");
        bundle.putExtra("player", player);

        Intent intent = new Intent(FadeOut.class);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("nextGameState", Area_1_1.class);
        intent.putExtra("bundle", bundle);
        intent.putExtra("fadeRate", 3);

        swapGameState(intent);
    }
}
