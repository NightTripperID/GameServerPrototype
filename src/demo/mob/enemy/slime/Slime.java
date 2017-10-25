package demo.mob.enemy.slime;

import demo.mob.Mob;
import demo.mob.enemy.Enemy;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;

public class Slime extends Enemy {

    Mob player;

    public Slime(int col, double x, double y, Mob player) {
        super(col, x, y, 1, 1, 16, 16, 2, 1, true);
        currSprite = new AnimSprite(SpriteSheets.SLIME, 16, 16, 4);
        currSprite.setFrameRate(random.nextInt(5) + 13);
        this.player = player;
        currState = new SlimeStateFlying(this, gameState);
    }
}
