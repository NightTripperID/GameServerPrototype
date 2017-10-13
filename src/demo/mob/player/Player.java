package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.area.Area_1_1;
import demo.mob.Mob;
import demo.mob.player.inventory.Inventory;
import demo.spritesheets.SpriteSheets;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import entity.Updatable;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.AnimSprite;
import graphics.Screen;
import input.Mouse;

public class Player extends Mob {

    public final Inventory inventory = new Inventory();

    private int graceCount;
    private static final int MAX_GRACE_COUNT = 1 * 90;

    private int numKeys = 0;
    private int numPotions = 0;

    private AnimSprite heartSprite = new AnimSprite(SpriteSheets.HEART, 8, 8, 1);
    private AnimSprite doorkeySprite = new AnimSprite(SpriteSheets.DOORKEY, 8, 8, 1);
    private AnimSprite potionSprite = new AnimSprite(SpriteSheets.POTION, 8, 8, 1);

    private int charge;
    private static final int MAX_CHARGE = 100;
    private int[] chargeColors = {0xffff00, 0xffa500, 0xff0000, 0xff00ff};
    private int chargeColor;

    public Player(int x, int y) {
        super(x, y, 1, 1, 16, 16, 3, 0, true, true);
    }

    @Override
    public void initialize(@NotNull GameState gameState) {
        super.initialize(gameState);

        currSprite = new AnimSprite(SpriteSheets.PLAYER_DOWN, 16, 16, 4);
        currState = new PlayerStateStanding(this, gameState);

        if(!Mouse.button3Held)
            charge = 0;
    }

    @Override
    public void update() {

        chargeColor = chargeColors[charge / 33];

        currState.update();

        if(getHealth()<= 0) {
            Bundle bundle = new Bundle();
            TileCoord coord = new TileCoord(14, 17, 16);
            bundle.putExtra("player", new Player(coord.getX(), coord.getY()));
            Intent intent = new Intent(FadeOut.class);
            intent.setBundle(bundle);
            intent.putExtra("nextGameState", Area_1_1.class);
            intent.putExtra("pixels", gameState.getScreenPixels());
            gameState.swapGameState(intent);
        }

        numKeys = inventory.getCount("doorkey");
        numPotions = inventory.getCount("potion");

        if (graceCount == MAX_GRACE_COUNT)
            setVulnerable(true);
        else
            graceCount++;
    }

    @Override
    public void render(@NotNull Screen screen) {
        int screenW = gameState.getScreenWidth();
        int doorkeyOfs = screenW - 40;
        int numKeysOfs = screenW - 32;
        int chargeOfs = screenW - 80 - 70 + 16;

        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());

        screen.fillRect(0, 0, gameState.getScreenWidth(), 32, 0x000000);
        screen.drawRect(0, 0, gameState.getScreenWidth(), 32, 0xffffff);

        for (int i = 0; i < getHealth(); i++)
            screen.renderSprite(16 + (i << 4), 12, heartSprite.getSprite());

        for (int i = 0; i < numPotions; i++)
            screen.renderSprite(chargeOfs - 16 - (i << 4), 12, potionSprite.getSprite());

        screen.renderSprite(doorkeyOfs, 12, doorkeySprite.getSprite());
        screen.renderString8x8(numKeysOfs, 12, 0xffffff, "x" + numKeys);

        screen.renderString8x8(chargeOfs, 12, chargeColor, "charge %" + charge);
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

    void addCharge(int charge) {
        if(this.charge < MAX_CHARGE)
            this.charge += charge;

        if(this.charge > MAX_CHARGE)
            this.charge = MAX_CHARGE;
    }

    public void spendCharge() {
        charge = 0;
    }
}
