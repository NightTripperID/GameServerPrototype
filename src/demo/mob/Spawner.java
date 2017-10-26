package demo.mob;

import demo.mob.enemy.Enemy;
import graphics.AnimSprite;
import graphics.Screen;
import graphics.Sprite;

public abstract class Spawner extends Enemy {

    protected int count;
    protected int countMax;

    protected Sprite spawnerSprite;

    public Spawner(int col, double x, double y, int countMax, AnimSprite animSprite) {
        this(col, x, y, countMax);
        currSprite = animSprite;
    }

    public Spawner(int col, double x, double y, int countMax, Sprite spawnerSprite) {
        this(col, x, y, countMax);
        this.spawnerSprite = spawnerSprite;
    }

    private Spawner(int col, double x, double y, int countMax) {
        super(col, x, y, 1, 1, 16, 16, 5, 0, true);
        this.countMax = countMax;
    }

    @Override
    public void update() {
        super.update();
        if (currSprite != null)
            currSprite.update();

        if (count++ == countMax) {
            count = 0;
            spawn();
        }
    }

    @Override
    public void render(Screen screen) {
        if (spawnerSprite == null)
            if (wounded)
                screen.renderSpriteColorFill(x - gameState.getScrollX(), y - gameState.getScrollY(), 0xffffff, currSprite.getSprite());
            else
                screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
        else if (wounded)
            screen.renderSpriteColorFill(x - gameState.getScrollX(), y - gameState.getScrollY(), 0xffffff, spawnerSprite);
        else
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), spawnerSprite);
    }

    protected abstract void spawn();
}
