package demo.overlay;

import demo.mob.player.Player;
import demo.mob.player.inventory.Inventory;
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

    private Inventory inventory;

    private int numKeys;
    private int numPotions;

    public Overlay(Player player) {
        this.inventory = player.inventory;
    }

    @Override
    public void initialize(GameState gameState) {
        super.initialize(gameState);
    }

    @Override
    public void update() {
        numKeys = inventory.getCount("doorkey");
        numPotions = inventory.getCount("potion");

    }

    @Override
    public void render(Screen screen) {
        screen.fillRect(0, 0, gameState.getScreenWidth(), 32, 0x000000);
        screen.drawRect(0, 0, gameState.getScreenWidth(), 32, 0xffffff);

//        for (int i = 0; i < getHealth(); i++)
//            screen.renderSprite(16 + (i << 4), 12, heartSprite.getSprite());

        screen.renderSprite(gameState.getScreenWidth() - 56, 12, doorkeySprite.getSprite());
        screen.renderString8x8(gameState.getScreenWidth() - 48, 12, 0xffffff, "x" + numKeys);
    }

    @Override
    public boolean removed() {
        return false;
    }
}
