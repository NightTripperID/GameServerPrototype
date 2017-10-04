package demo.mob;

import com.sun.istack.internal.NotNull;
import entity.Entity;
import entity.Renderable;
import entity.Updatable;
import graphics.AnimSprite;

public abstract class Mob extends Entity implements Updatable, Renderable {

    protected AnimSprite currSprite;

    protected MobState currState;

    public double x;
    public double y;

    public double xa;
    public double ya;

    private double xSpeed;
    private double ySpeed;

    private double xDir;
    private double yDir;

    private int width;
    private int height;


    protected Mob(int x, int y, int xDir, int yDir, int width, int height) {

        this.x = x;
        this.y = y;

        setxDir(xDir);
        setyDir(yDir);
        setWidth(width);
        setHeight(height);
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
        if(xSpeed < 0)
            throw new IllegalArgumentException("xSpeed must be set to 0 or greater");

        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        if(ySpeed < 0)
            throw new IllegalArgumentException("ySpeed must be set to 0 or greater");

        this.ySpeed = ySpeed;
    }

    public void setxDir(double xDir) {
        if(xDir == 1 || xDir == -1)
            this.xDir = xDir;
        else
            throw new IllegalArgumentException("xDir must be set to 1 or -1");
    }

    public void setyDir(double yDir) {
        if(yDir == 1 || yDir == -1)
            this.yDir = yDir;
        else
            throw new IllegalArgumentException("yDir must be set to 1 or -1");
    }

    public void setWidth(int width) {
        if(width < 1)
            throw new IllegalArgumentException("width must be set to 1 or greater");

        this.width = width;
    }

    public void setHeight(int height) {
        if(height < 1)
            throw new IllegalArgumentException("height must be set to 1 or greater");

        this.height = height;
    }
}
