package com.bitburger.graphics;

/**
 * Object that represents a Sprite animation sequence,
 */
public class AnimSprite extends Sprite {

    private int frame;
    private Sprite sprite;
    private int rate = 5;
    private int time;
    private int length = -1;

    /**
     * Creates a new AnimSprite object from a specified SpriteSheet and frame rate.
     * @param sheet The given SpriteSheet
     * @param width The Sprite width in pixel precision.
     * @param height The Sprite height in pixel precision.
     * @param length The length of the AnimSprite in Sprite precision (i.e. the number of sprites in the animation sequence).
     * @param rate The given rate at which to animate the sprite (the higher the number, the slower the animation rate).
     */
    public AnimSprite(SpriteSheet sheet, int width, int height, int length, int rate) {
        this(sheet, width, height, length);
        this.rate = rate;
    }

    /**
     * Creates a new AnimSprite object from a specified SpriteSheet and frame rate.
     * @param sheet The given SpriteSheet
     * @param width The Sprite width in pixel precision.
     * @param height The Sprite height in pixel precision.
     * @param length The length of the AnimSprite in Sprite precision (i.e. the number of sprites in the animation sequence).
     */
    public AnimSprite(SpriteSheet sheet, int width, int height, int length) {
        super(sheet, width, height);
        this.length = length;

        if(length > sheet.getSprites().length)
            System.err.println("Error: animation length is too long...");

        sprite = sheet.getSprites()[0];
    }

    /**
     * Advances the animation frame by the AnimSprite's frame rate.
     */
    public void update() {
        if(++time % rate == 0) {
            if (frame == length)
                frame = 0;
            sprite = sheet.getSprites()[frame++];
        }
    }

    /**
     * Returns the current Sprite within the AnimSprite's current frame.
     * @return the Sprite within the AnimSprite's current frame.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Sets the AnimSprite's frame rate.
     * @param rate The given frame rate.
     */
    public void setFrameRate(int rate) {
        this.rate = rate;
    }

    /**
     * Sets the AnimSprites frame index.
     * @param frame The given frame index.
     */
    public void setFrame(int frame) {
        if(frame < 0 || frame > sheet.getSprites().length) {
            ArrayIndexOutOfBoundsException e = new ArrayIndexOutOfBoundsException("frame index is out of bounds!");
            e.printStackTrace();
        }

        this.frame = frame;
    }

    /**
     * Gets the AnimSprites current frame index.
     * @return The given frame index.
     */
    public int getFrame() {
        return frame;
    }
}
