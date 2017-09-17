package test.mobs;

import com.sun.istack.internal.NotNull;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import gamestate.GameState;

public abstract class Mob extends Entity implements Updatable, Renderable {

    protected double x;
    protected double y;

    protected double xSpeed;
    protected double ySpeed;

    protected int xDir;
    protected int yDir;

    protected int width;
    protected int height;

    protected Mob(@NotNull GameState gs, double x, double y, double xSpeed, double ySpeed, int xDir, int yDir) {

        this(x, y, xSpeed, ySpeed, xDir, yDir);
        gameState = gs;

    }

    protected Mob(double x, double y, double xSpeed, double ySpeed, int xDir, int yDir) {
        this.x = x;
        this.y = y;

        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        this.xDir = xDir;
        this.yDir = yDir;
    }
}
