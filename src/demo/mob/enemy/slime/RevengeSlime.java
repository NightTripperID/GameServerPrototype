package demo.mob.enemy.slime;

import demo.mob.Mob;
import demo.spritesheets.SpriteSheets;
import demo.zone.Zone_3_1;
import graphics.AnimSprite;

public class RevengeSlime extends Slime {

    public static int NUM_REVENGE_SLIMES = 12;

    public RevengeSlime(int col, double x, double y, Mob player) {
        super(col, x, y, player);
        currSprite = new AnimSprite(SpriteSheets.REVENGE_SLIME, 16, 16, 4);
        currSprite.setFrameRate(random.nextInt(5) + 13);
    }

    @Override
    public void update() {
        super.update();
        if (getHealth() <= 0)
            if(gameState instanceof Zone_3_1)
                ((Zone_3_1) gameState).subtractSlime();
    }
}
