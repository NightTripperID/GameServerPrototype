package graphics;

public class AnimSprite extends Sprite {

    public int frame;
    private Sprite sprite;
    private int rate = 5;
    private int time;
    private int length = -1;

    public AnimSprite(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;

        if(length > sheet.getSprites().length)
            System.err.println("Error: animation length is too long...");

        sprite = sheet.getSprites()[0];
    }

    public void update() {
        if(++time % rate == 0) {
            if (frame == length)
                frame = 0;
            sprite = sheet.getSprites()[frame++];
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setFrameRate(int rate) {
        this.rate = rate;
    }

    public void setFrame(int frame) {
        if(frame < 0 || frame > sheet.getSprites().length) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("frame index is out of bounds!");
            e.printStackTrace();
        }

        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }
}
