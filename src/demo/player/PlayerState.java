package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import input.Keyboard;

abstract class PlayerState extends MobState {

    static final AnimSprite PLAYER_UP = new AnimSprite(SpriteSheets.PLAYER_UP, 16, 16, 4);
    static final AnimSprite PLAYER_DOWN = new AnimSprite(SpriteSheets.PLAYER_DOWN, 16, 16, 4);
    static final AnimSprite PLAYER_LEFT = new AnimSprite(SpriteSheets.PLAYER_LEFT, 16, 16, 4);
    static final AnimSprite PLAYER_RIGHT = new AnimSprite(SpriteSheets.PLAYER_RIGHT, 16, 16, 4);

    Keyboard keyboard;

    PlayerState(@NotNull Mob mob) {
        super(mob);
        keyboard = mob.getGameState().getKeyboard();
    }
}
