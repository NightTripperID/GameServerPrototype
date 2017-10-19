package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.area.Area_1_1;
import demo.mob.Mob;
import demo.mob.player.inventory.Inventory;
import demo.spritesheets.SpriteSheets;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.AnimSprite;
import graphics.Screen;

public class Player extends Mob {

    public final Inventory inventory = new Inventory();

    private static final int MAX_GRACE_COUNT = 1 * 90;
    private int graceCount = MAX_GRACE_COUNT;

//    private int numKeys = 0;
//    private int numPotions = 0;
//
//    private AnimSprite heartSprite = new AnimSprite(SpriteSheets.HEART, 8, 8, 1);
//    private AnimSprite doorkeySprite = new AnimSprite(SpriteSheets.DOORKEY, 8, 8, 1);
//    private AnimSprite potionSprite = new AnimSprite(SpriteSheets.POTION, 8, 8, 1);

    public static final int MAX_HEALTH = 6;
    public static final int MAX_POTIONS = 4;

    private boolean visible = true;

    public Player(int x, int y) {
        super(0x00ffff, x, y, 1, 1, 16, 16, 3, 0, true, true);
    }

    @Override
    public void initialize(@NotNull GameState gameState) {
        super.initialize(gameState);
        currSprite = new AnimSprite(SpriteSheets.PLAYER_DOWN, 16, 16, 4);
        currState = new PlayerStateStanding(this, gameState);
    }

    @Override
    public void update() {
        currState.update();

        if(getHealth()<= 0) {
            Bundle bundle = new Bundle();
            bundle.putExtra("player", new Player(0, 0));
            bundle.putExtra("tileCoord", new TileCoord(14, 17, 16));
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("bundle", bundle);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", gameState.getScreenPixels());
            gameState.swapGameState(intent);
        }

//        numKeys = inventory.getCount("doorkey");
//        numPotions = inventory.getCount("potion");

        if (graceCount == MAX_GRACE_COUNT) {
            setVulnerable(true);
            visible = true;
        } else {
            if(graceCount++ % 2 == 0)
                visible = ! visible;
        }
    }

    @Override
    public void render(@NotNull Screen screen) {
        if(visible)
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());

//        int screenW = gameState.getScreenWidth();
//        int doorkeyOfs = screenW - 40;
//        int numKeysOfs = screenW - 32;
//
//        screen.fillRect(0, 0, screenW, 32, 0x000000);
//        screen.drawRect(0, 0, screenW, 32, 0xffffff);
//
//        for (int i = 0; i < getHealth(); i++)
//            screen.renderSprite(16 + (i << 4), 12, heartSprite.getSprite());
//
//        int w = 60;
//        int potionFrameOfs = (screenW >> 1) - (w >> 1);
//
//        screen.renderString5x5(potionFrameOfs + (("potions".length() * 5) >> 1) - 2, 4, 0xffffff, "potions");
//
//        screen.drawRect(potionFrameOfs, 10, 60, 12, 0xffffff);
//
//        for (int i = 0; i < numPotions; i++)
//            screen.renderSprite(potionFrameOfs + 2 + (i << 4), 12, potionSprite.getSprite());
//
//        screen.renderSprite(doorkeyOfs, 12, doorkeySprite.getSprite());
//        screen.renderString8x8(numKeysOfs, 12, 0xffffff, "x" + numKeys);
    }

    @Override
    public void runCollision(@NotNull Mob mob) {
        super.runCollision(mob);
        if(this.friendly() != mob.friendly())
            if(!(currState instanceof PlayerStateKnockback))
                currState = new PlayerStateKnockback(this, gameState, (PlayerState) currState);
    }

    @Override
    public void assignDamage(int damage) {
        if (vulnerable()) {
            setVulnerable(false);
            graceCount = 0;
            super.assignDamage(damage);
        }
    }
}
