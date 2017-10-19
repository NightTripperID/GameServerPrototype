package demo.mob;

import com.sun.istack.internal.NotNull;
import demo.mob.explosion.Explosion;
import demo.mob.treasure.Potion;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import graphics.AnimSprite;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public abstract class Mob extends Entity implements Updatable, Renderable, Serializable {

    public static final long serialVersionUID = 201709271703L;

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    public Direction direction;

    protected AnimSprite currSprite;

    protected MobState currState;

    private boolean removed;

    protected Random random = new Random();

    private int col; // color coding
    public double x, y;
    public double xa, ya;
    private double xSpeed, ySpeed;
    private double xDir, yDir;
    private int width, height;
    private int health, damage;
    private boolean friendly, vulnerable;

    public Mob(int col, double x, double y, int xDir, int yDir, int width, int height,
               int health, int damage, boolean friendly, boolean vulnerable) {

        this.col = col;

        this.x = x;
        this.y = y;

        setxDir(xDir);
        setyDir(yDir);
        setWidth(width);
        setHeight(height);

        if(health < 1)
            throw new IllegalArgumentException("health must be at least 1");
        this.health = health;
        this.damage = damage;
        this.friendly = friendly;
        this.vulnerable = vulnerable;

        direction = Direction.DOWN;
    }

    @Override
    public void update() {

        if(currState != null)
            currState.update();
        if(health <= 0) {
            setRemoved(true);
            Entity explosion = new Explosion(0xff00ff, x, y); // TODO: needs switch statement to spawn explosion
            explosion.initialize(gameState);        // TODO: of appropriate size (8x8, 16x16, etc)
            gameState.addEntity(explosion);

            if(!friendly) {
                switch(random.nextInt(10)) {
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
    }

    public boolean tileCollision(int xa, int ya) {
        for (int corner = 0; corner < 4; corner++) {
            Point p = getTileCorner(xa, ya, corner);
            if (gameState.getMapTileObject(p.x, p.y).solid())
                return true;
        }
        return false;
    }

    public boolean triggerCollision(int xa, int ya) {
        for (int corner = 0; corner < 4; corner++) {
            Point p = getTileCorner(xa, ya, corner);
            if (gameState.getMapTileObject(p.x, p.y).trigger())
                return true;
        }
        return false;
    }

    public Runnable getTileTrigger(int xa, int ya) {
        for (int corner = 0; corner < 4; corner++) {
            Point p = getTileCorner(xa, ya, corner);
            if (gameState.getMapTileObject(p.x, p.y).trigger())
                return gameState.getTrigger(p.x, p.y);
        }
        return null;
    }

    protected Point getTileCorner(int xa, int ya, int corner) {
        int xt = ((int) (x + xa) + corner % 2 * 2 + 6) / 16;
        int yt = ((int) (y + ya) + corner / 2 * 2 + 12) / 16;
        return new Point(xt, yt);
    }

    public AnimSprite getCurrSprite() {
        return currSprite;
    }

    public MobState getCurrState() {
        return currState;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public double getxDir() {
        return xDir;
    }

    public double getyDir() {
        return yDir;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setCurrSprite(@NotNull AnimSprite currSprite) {
        this.currSprite = currSprite;
    }

    public void setCurrState(@NotNull MobState currState) {
        this.currState = currState;
    }

    public void setxSpeed(double xSpeed) {
        if (xSpeed < 0)
            throw new IllegalArgumentException("xSpeed must be set to 0 or greater");

        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        if (ySpeed < 0)
            throw new IllegalArgumentException("ySpeed must be set to 0 or greater");

        this.ySpeed = ySpeed;
    }

    public void setxDir(double xDir) {
        if (xDir == 1 || xDir == -1)
            this.xDir = xDir;
        else
            throw new IllegalArgumentException("xDir must be set to 1 or -1");
    }

    public void setyDir(double yDir) {
        if (yDir == 1 || yDir == -1)
            this.yDir = yDir;
        else
            throw new IllegalArgumentException("yDir must be set to 1 or -1");
    }

    public void setWidth(int width) {
        if (width < 1)
            throw new IllegalArgumentException("width must be set to 1 or greater");

        this.width = width;
    }

    public void setHeight(int height) {
        if (height < 1)
            throw new IllegalArgumentException("height must be set to 1 or greater");

        this.height = height;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public boolean removed() {
        return removed;
    }

    public boolean collidesWith(@NotNull Mob mob) {
        if(x + xa + width > mob.x + mob.xa && x + xa < mob.x + mob.xa + mob.getWidth())
            if(y + ya + height > mob.y + mob.ya && y + ya < mob.y + mob.ya + mob.getHeight())
                return true;

        return false;
    }

    public boolean friendly() {
        return friendly;
    }

    public boolean vulnerable() {
        return vulnerable;
    }

    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;
    }

    public void assignDamage(int damage) {
        if(damage < 0)
            throw new IllegalArgumentException("damage must be > 0");
        health -= damage;
    }

    public void addHealth(int health) {
        if(health < 0)
            throw new IllegalArgumentException("added health must be > 0");
        this.health += health;
    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void runCollision(@NotNull Mob mob) {
        if(friendly() != mob.friendly())
            if(mob.vulnerable)
                mob.assignDamage(damage);
    }

    protected void commitMove(double xa, double ya) {
        if (xa != 0 && ya != 0) {
            commitMove(xa, 0);
            commitMove(0, ya);
            return;
        }

        if (!tileCollision((int) xa, (int) ya)) {
            x += xa;
            y += ya;
        }
    }

    public int getCol() {
        return col;
    }
}