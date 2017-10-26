package demo.mob.player;

import com.sun.istack.internal.NotNull;
import demo.audio.Sfx;
import demo.zone.Zone;
import demo.mob.Mob;
import demo.mob.player.inventory.Inventory;
import demo.spritesheets.SpriteSheets;
import demo.tile.TileCoord;
import gamestate.GameState;
import graphics.AnimSprite;
import graphics.Screen;

public class Player extends Mob {

    public final Inventory inventory = new Inventory();

    private static final int MAX_GRACE_COUNT = 1 * 90;
    private int graceCount = MAX_GRACE_COUNT;

    static final int MAX_HEALTH = 6;
    public static final int MAX_POTIONS = 4;

    private boolean visible = true;

    private TileCoord respawn;

    public Player(int x, int y) {
        super(0x00ffff, x, y, 1, 1, 16, 16, 3, 0, true, true);
    }

    public Player() {
        this(0, 0);
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
            setVulnerable(true);
            visible = true;
            graceCount = MAX_GRACE_COUNT;
            addHealth(3);
            ((Zone) gameState).changeZone(gameState.getClass(), respawn);
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
            if(getHealth() > 0)
                Sfx.HERO_HURT.play();
            else
                Sfx.HERO_DEAD.play();
        }
    }

    public void setRespawn(TileCoord respawn) {
        this.respawn = respawn;
    }
}
