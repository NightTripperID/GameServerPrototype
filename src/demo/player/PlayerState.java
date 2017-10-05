package demo.player;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.MobState;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import input.Keyboard;

abstract class PlayerState extends MobState {

    Keyboard keyboard;

    PlayerState(@NotNull Mob mob) {
        super(mob);
        keyboard = mob.getGameState().getKeyboard();
    }
}
