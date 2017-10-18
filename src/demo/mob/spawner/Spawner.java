package demo.mob.spawner;

import demo.mob.Mob;
import graphics.AnimSprite;
import graphics.Screen;
import graphics.Sprite;

public abstract class Spawner extends Mob {

    protected int count;
    protected final int countMax;

    protected Sprite spawnerSprite;

    public Spawner(int col, double x, double y, int countMax, AnimSprite animSprite, int frameRate) {
        this(col,x, y, countMax);
        currSprite = animSprite;
        currSprite.setFrameRate(frameRate);
    }

    public Spawner(int col, double x, double y, int countMax, Sprite spawnerSprite) {
        this(col, x, y, countMax);
        this.spawnerSprite = spawnerSprite;
    }

    private Spawner(int col, double x, double y, int countMax) {
        super(col, x, y, 1, 1, 16, 16, 5, 0, false, true);
        this.countMax = countMax;
    }

    @Override
    public void update() {
        super.update();
        if(currSprite != null)
            currSprite.update();

        if(count++ == countMax) {
            count = 0;
            spawn();
        }
    }

    @Override
    public void render(Screen screen) {
        if(spawnerSprite == null)
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
        else
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), spawnerSprite);
    }

    protected abstract void spawn();
}
