package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.player.inventory.Inventory;
import demo.spritesheets.SpriteSheets;
import demo.tile.DemoTile;
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

    static final int MAX_HEALTH = 6;
    public static final int MAX_POTIONS = 4;

    private boolean visible = true;

    private TileCoord lastSpawn;

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
            addHealth(3);
            Bundle bundle = new Bundle();
            bundle.putExtra("player", this);
            bundle.putExtra("tileCoord", lastSpawn);
            Intent intent = new Intent(FadeOut.class);
            intent.putExtra("bundle", bundle);
            intent.putExtra("nextGameState", gameState.getClass());
            intent.putExtra("pixels", gameState.getScreenPixels());
            gameState.swapGameState(intent);
        }

        if (graceCount == MAX_GRACE_COUNT) {
            setVulnerable(true);
            visible = true;
        } else {
            if(graceCount++ % 2 == 0)
                visible = !visible;
        }
    }

    @Override
    public void render(@NotNull Screen screen) {
        if(visible)
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    @Override
    public void assignDamage(int damage) {
        if (vulnerable()) {
            setVulnerable(false);
            graceCount = 0;
            if(!(currState instanceof PlayerStateKnockback))
                currState = new PlayerStateKnockback(this, gameState, (PlayerState) currState);
            super.assignDamage(damage);
        }
    }

    public void setRespawn(TileCoord lastSpawn) {
        this.lastSpawn = lastSpawn;
    }
}
