package demo.mob.enemy.slime;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.enemy.Enemy;
import demo.spritesheets.SpriteSheets;
import graphics.AnimSprite;
import graphics.Screen;
import graphics.Sprite;

public class Slime extends Enemy {

    private int count;
    private final int skip = random.nextInt(3) + 1;

    private Mob player;

    public Slime(int col, double x, double y, Mob player) {
        super(col, x, y, 1, 1, 16, 16, 2, 1, true);
        currSprite = new AnimSprite(SpriteSheets.SLIME, 16, 16, 4);
        currSprite.setFrameRate(random.nextInt(5) + 13);
        this.player = player;
    }

    @Override
    public void update() {

        super.update();
        currSprite.update();

        if (count++ / skip == 1) {
            count = 0;
            move();
        }
    }

    private void move() {
        if (x == player.x)
            setxSpeed(0);
        else
            setxSpeed(1);

        if (y == player.y)
            setySpeed(0);
        else
            setySpeed(1);

        if (x > player.x)
            setxDir(-1);
        else if (x < player.x)
            setxDir(1);

        if (y > player.y)
            setyDir(-1);
        else if (y < player.y)
            setyDir(1);

        xa = getxSpeed() * getxDir();
        ya = getySpeed() * getyDir();

        commitMove(xa, ya);
    }
}
