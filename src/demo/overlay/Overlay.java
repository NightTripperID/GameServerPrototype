package demo.overlay;

import demo.mob.player.Player;
import demo.spritesheets.Sprites;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import gamestate.GameState;
import graphics.Screen;

public class Overlay extends Entity implements Updatable, Renderable{

    private int numYellowKeys;
    private int numBlueKeys;
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
        numYellowKeys = player.inventory.getCount("doorkey");
        numBlueKeys = player.inventory.getCount("blue_doorkey");
        numPotions = player.inventory.getCount("potion");

    }

    @Override
    public void render(Screen screen) {

        int screenW = gameState.getScreenWidth();
        int blueDoorkeyOfs = screenW - 80;
        int numBlueKeysOfs = screenW - 72;
        int yellowDoorkeyOfs = screenW - 40;
        int numYellowKeysOfs = screenW - 32;

        screen.fillRect(0, 0, screenW, 32, 0x000000);
        screen.drawRect(0, 0, screenW, 32, 0xffffff);

        for (int i = 0; i < player.getHealth(); i++)
            screen.renderSprite(16 + (i << 4), 12, Sprites.HEART);

        int w = 60;
        int potionFrameOfs = (screenW >> 1) - (w >> 1);

        screen.renderString5x5(potionFrameOfs + (("potions".length() * 5) >> 1) - 2, 4, 0xffffff, "potions");

        screen.drawRect(potionFrameOfs, 10, 60, 12, 0xffffff);

        for (int i = 0; i < numPotions; i++)
            screen.renderSprite(potionFrameOfs + 2 + (i << 4), 12, Sprites.POTION);

        screen.renderSprite(blueDoorkeyOfs, 12, Sprites.BLUE_DOORKEY);
        screen.renderString8x8(numBlueKeysOfs, 12, 0xffffff, "x" + numBlueKeys);

        screen.renderSprite(yellowDoorkeyOfs, 12, Sprites.YELLOW_DOORKEY);
        screen.renderString8x8(numYellowKeysOfs, 12, 0xffffff, "x" + numYellowKeys);
    }

    @Override
    public boolean removed() {
        return false;
    }
}
