package demo.mob.enemy;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.explosion.Explosion;
import demo.mob.item.Potion;
import demo.audio.Sfx;
import entity.Entity;
import graphics.Screen;
import graphics.Sprite;

public abstract class Enemy extends Mob {

    protected boolean wounded;
    private int count;

    public Enemy(int col, double x, double y, int xDir, int yDir, int width, int height, int health, int damage, boolean vulnerable) {
        super(col, x, y, xDir, yDir, width, height, health, damage, false, vulnerable);
    }

    @Override
    public void update() {
        super.update();

        final int woundCount = 3;
        if (wounded) {
            if (count++ == woundCount) {
                count = 0;
                wounded = false;
            }
        }

        if (getHealth() <= 0) {
            die();
            dropItem();
        }
    }

    @Override
    public void render(@NotNull Screen screen) {
        if (wounded)
            screen.renderSpriteColorFill(x - gameState.getScrollX(), y - gameState.getScrollY(), 0xffffff,
                    currSprite.getSprite());
        else
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(), currSprite.getSprite());
    }

    protected void renderWithAngle(@NotNull Screen screen, double angle) {
        if (wounded)
            screen.renderSpriteColorFill(x - gameState.getScrollX(), y - gameState.getScrollY(), 0xffffff,
                    Sprite.rotate(currSprite.getSprite(), angle));
        else
            screen.renderSprite(x - gameState.getScrollX(), y - gameState.getScrollY(),
                    Sprite.rotate(currSprite.getSprite(), angle));
    }

    protected void die() {
        setRemoved(true);
        Entity explosion = new Explosion(0xff00ff, x, y); // TODO: needs switch statement to spawn explosion
        explosion.initialize(gameState);        // TODO: of appropriate size (8x8, 16x16, etc)
        gameState.addEntity(explosion);
        Sfx.ENEMY_EXPLODE.play();
    }

    protected void dropItem() {
        if (!friendly()) {
            switch (random.nextInt(10)) {
                case 0:
                    Entity potion = new Potion(0xffff0000, x, y);
                    potion.initialize(gameState);
                    gameState.addEntity(potion);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void assignDamage(int damage) {
        super.assignDamage(damage);

        if (damage > 0) {
            wounded = true;
            Sfx.ENEMY_HIT.play();
        }

    }
}
