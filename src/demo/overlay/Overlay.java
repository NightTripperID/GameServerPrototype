package demo.overlay;

import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import gamestate.GameState;
import graphics.AnimSprite;
import graphics.Screen;

public class Overlay extends Entity implements Updatable, Renderable{

    private AnimSprite heartSprite = new AnimSprite(SpriteSheets.HEART, 8, 8, 1);
    private AnimSprite doorkeySprite = new AnimSprite(SpriteSheets.DOORKEY, 8, 8, 1);
    private AnimSprite potionSprite = new AnimSprite(SpriteSheets.POTION, 8, 8, 1);

    private int numKeys;
    private int numPotions;

    private Player player;

    public Overlay(Player player) {
        this.player = player;
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
    }

    @Override
    public void update() {
        numKeys = player.inventory.getCount("doorkey");
        numPotions = player.inventory.getCount("potion");

    }

    @Override
    public void render(Screen screen) {

        int screenW = gameState.getScreenWidth();
        int doorkeyOfs = screenW - 40;
        int numKeysOfs = screenW - 32;

        screen.fillRect(0, 0, screenW, 32, 0x000000);
        screen.drawRect(0, 0, screenW, 32, 0xffffff);

        for (int i = 0; i < player.getHealth(); i++)
            screen.renderSprite(16 + (i << 4), 12, heartSprite.getSprite());

        int w = 60;
        int potionFrameOfs = (screenW >> 1) - (w >> 1);

        screen.renderString5x5(potionFrameOfs + (("potions".length() * 5) >> 1) - 2, 4, 0xffffff, "potions");

        screen.drawRect(potionFrameOfs, 10, 60, 12, 0xffffff);

        for (int i = 0; i < numPotions; i++)
            screen.renderSprite(potionFrameOfs + 2 + (i << 4), 12, potionSprite.getSprite());

        screen.renderSprite(doorkeyOfs, 12, doorkeySprite.getSprite());
        screen.renderString8x8(numKeysOfs, 12, 0xffffff, "x" + numKeys);
    }

    @Override
    public boolean removed() {
        return false;
    }
}
