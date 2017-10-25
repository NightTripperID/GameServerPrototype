package demo.mob.enemy.buzzard;

import demo.mob.enemy.Enemy;
import demo.spritesheets.SpriteSheets;
import demo.tile.DemoTile;
import graphics.AnimSprite;
import graphics.Screen;

import static demo.mob.enemy.buzzard.Buzzard.Rotation.CLOCKWISE;
import static demo.mob.enemy.buzzard.Buzzard.Rotation.COUNTERCLOCKWISE;

public class Buzzard extends Enemy {

    private AnimSprite buzzardClockwise = new AnimSprite(SpriteSheets.BUZZARD_CLOCKWISE, 16, 16, 2);
    private AnimSprite buzzardCounterclockwise = new AnimSprite(SpriteSheets.BUZZARD_COUNTERCLOCKWISE, 16, 16, 2);

    private int count;
    private double angle = .01;

    private double xCenter;
    private double yCenter;

    private Rotation rotation = CLOCKWISE;

    private static final int radius = DemoTile.SIZE * 4;

    private static final int FRAME_RATE = 30;

    public Buzzard(int col, double x, double y) {
        super(col, x, y, 1, 1, 16, 16, 1, 1, true);
        buzzardClockwise.setFrameRate(FRAME_RATE);
        buzzardCounterclockwise.setFrameRate(FRAME_RATE);

        rotation = random.nextInt(2) ==  1 ? CLOCKWISE : COUNTERCLOCKWISE;
        currSprite = rotation == CLOCKWISE ? buzzardClockwise : buzzardCounterclockwise;

        xCenter = x;
        yCenter = y;
    }

    @Override
    public void update() {
        super.update();
        currSprite.update();
        if(count++ % 2 == 0)
            move();
    }

    private void move() {

        // describe a circle

        if(rotation == CLOCKWISE)
            angle += .01;
        else
            angle -= .01;

        double dx = Math.cos(angle) * radius;
        double dy = Math.sin(angle) * radius;

        x = dx + xCenter;
        y = dy + yCenter;
    }

    @Override
    public void render(Screen screen) {
        renderWithAngle(screen, angle);
    }

    enum Rotation { CLOCKWISE, COUNTERCLOCKWISE }
}
