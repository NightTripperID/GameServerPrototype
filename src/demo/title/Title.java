package demo.title;

import demo.zone.Zone_1_1;
import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import demo.zone.Zone_3_1;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import graphics.Sprite;
import server.Server;

public class Title extends GameState {

    private Sprite axeSprite = Sprites.AXE;
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

        final int axeScale = 6;
        final int titleScale = 2;

        screen.fill(0xffffff);

        int axeSpriteX = (getScreenWidth() / 2) - (axeSprite.getWidth() / 2) * axeScale;
        int axeSpriteY = 0;

        screen.renderSprite(
                axeSpriteX,
                axeSpriteY,
                axeScale,
                axeSprite);

        int titleSpriteX = (getScreenWidth() / 2) - (titleSprite.getWidth() / 2) * titleScale;
        int titleSpriteY = (getScreenHeight() / 2) - (titleSprite.getHeight() / 2) * titleScale;

        screen.renderSprite(
                titleSpriteX,
                titleSpriteY,
                titleScale,
                titleSprite);

        String str = "very short adventure";
        screen.renderString8x8(
                (getScreenWidth() / 2) - (str.length() * 8 / 2),
                titleSpriteY + titleSprite.getHeight() * titleScale + 8,
                0x000000,
                str
        );
    }

    private void startDemo() {
        Bundle bundle = new Bundle();
        bundle.putExtra("tileCoord", new TileCoord(14, 17, 16));
//        bundle.putExtra("tileCoord", new TileCoord(11, 15, 16));
        Player player = new Player();
        player.inventory.add("potion");
        player.inventory.add("potion");
        bundle.putExtra("player", player);

        Intent intent = new Intent(FadeOut.class);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("nextGameState", Zone_1_1.class);
//        intent.putExtra("nextGameState", Zone_3_1.class);
        intent.putExtra("bundle", bundle);
        intent.putExtra("fadeRate", 3);

        swapGameState(intent);
    }
}
