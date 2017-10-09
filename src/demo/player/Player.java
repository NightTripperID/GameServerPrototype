package demo.player;

import com.sun.istack.internal.NotNull;
import demo.area.Area_1_1;
import demo.mob.Mob;
import demo.spritesheets.PlayerSprites;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import entity.Updatable;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;

public class Player extends Mob {

    private boolean damageGrace;
    private int graceCount;
    private static final int MAX_GRACE_COUNT = 1 * 90;

    public Player(int x, int y) {
        super(x, y, 1, 1, 16, 16, 3, 0, true);
    }

    @Override
    public void initialize(@NotNull GameState gameState) {
        super.initialize(gameState);

        currSprite = PlayerSprites.PLAYER_DOWN;
        currState = new PlayerStateStanding(this, gameState);
    }

    @Override
    public void update() {
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

        if (graceCount == MAX_GRACE_COUNT)
            damageGrace = false;
        else
            graceCount++;
    }

    @Override
    public void render(@NotNull Screen screen) {
        screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    @Override
    public void runCollision(@NotNull Updatable updatable) {
        super.runCollision(updatable);
        Mob target = (Mob) updatable;
        if(!(isFriendly() & target.isFriendly()))
            if(!(currState instanceof PlayerStateKnockback) && !damageGrace)
            currState = new PlayerStateKnockback(this, gameState, (PlayerState) currState);
    }

    @Override
    protected void assignDamage(int damage) {
        if (!damageGrace) {
            damageGrace = true;
            super.assignDamage(damage);
        }
        System.out.println(getHealth());
    }
}
