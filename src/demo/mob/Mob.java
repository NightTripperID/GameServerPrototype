package demo.mob;

import entity.Entity;
import entity.Renderable;
import entity.Updatable;

public abstract class Mob extends Entity implements Updatable, Renderable {

    protected double x;
    protected double y;

    protected double xa;
    protected double ya;

    protected double xSpeed;
    protected double ySpeed;

    protected double xDir;
    protected double yDir;

    protected int width;
    protected int height;


    protected Mob(int x, int y, int xDir, int yDir, int width, int height) {
        this.x = x;
        this.y = y;
        this.xDir = xDir;
        this.yDir = yDir;
        this.width = width;
        this.height = height;
    }
}
