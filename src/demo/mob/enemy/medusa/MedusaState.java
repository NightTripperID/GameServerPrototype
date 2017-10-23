package demo.mob.enemy.medusa;

import demo.mob.Mob;
import demo.mob.MobState;
import demo.mob.player.Player;
import demo.spritesheets.SpriteSheets;
import gamestate.GameState;
import graphics.AnimSprite;

import java.util.Random;

abstract class MedusaState extends MobState {

    Random random = new Random();

    Player player;

    AnimSprite medusaUp = new AnimSprite(SpriteSheets.MEDUSA_UP, 16, 16, 2);
    AnimSprite medusaDown = new AnimSprite(SpriteSheets.MEDUSA_DOWN, 16, 16,2);

    MedusaState(Mob mob, GameState gameState, Player player) {
        super(mob, gameState);
        this.player = player;

        medusaUp.setFrameRate(20);
        medusaDown.setFrameRate(20);
    }
}
