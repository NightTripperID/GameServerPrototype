package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.graphics.AnimSprite;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;

public class SpriteUtil {

    private SpriteUtil() {
    }

    /**
     * Returns an array of Sprites that are retrieved from a SpriteSheet.
     * @param sheet The SpriteSheet from which the Sprite array is retrieved.
     * @return The array of Sprites retrieved from the SpriteSheet.
     */
    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.spriteWidth * sheet.spriteHeight);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.spriteWidth * sheet.spriteHeight];

        for (int yp = 0; yp < sheet.getHeight() / sheet.spriteHeight; yp++) {
            for (int xp = 0; xp < sheet.getWidth() / sheet.spriteWidth; xp++) {

                for (int y = 0; y < sheet.spriteHeight; y++) {
                    for (int x = 0; x < sheet.spriteWidth; x++) {
                        int xo = x + xp * sheet.spriteWidth;
                        int yo = y + yp * sheet.spriteHeight;
                        pixels[x + y * sheet.spriteWidth] = sheet.getPixelBuffer()[xo + yo * sheet.getWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.spriteWidth, sheet.spriteHeight);
            }
        }
        return sprites;
    }

    /**
     * Rotate specified sprite by the specified angle.
     * @param sprite: The specified com.bitburger.graphics.Sprite object.
     * @param angle: The angle in radians.
     * @return the rotated sprite
     */
    public static Sprite rotate(Sprite sprite, double angle){
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    /**
     * Advances the frame of an AnimSprite by its frame rate.
     * @param animSprite The animSprite to update
     */
    public AnimSprite updateAnimFrame(AnimSprite animSprite) {
        AnimSprite copy = new AnimSprite(animSprite);
        if(++copy.time % copy.frameRate == 0) {
            if (copy.getFrame() == copy.length)
                copy.setFrame(0);
            copy.sprite = copy.getSpriteSheet().getSprites()[copy.getFrame()];
            copy.setFrame(copy.getFrame() + 1);
        }
        return copy;
    }

    /**
     * Rotates the Sprite using an affine matrix
     * @param pixels The pixels representing the Sprite
     * @param width The Sprite width
     * @param height The Sprite height
     * @param angle The angle in radians by which to rotate the Sprite
     * @return the affine matrix rotated pixel array
     */
    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];

        double nx_x = rot_x(-angle, 1.0, 0.0);
        double nx_y = rot_y(-angle, 1.0, 0.0);
        double ny_x = rot_x(-angle, 0.0, 1.0);
        double ny_y = rot_y(-angle, 0.0, 1.0);

        double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col = 0;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height)
                    col = 0xffff00ff;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    /**
     * Returns the new x coordinate of a pixel rotated by a specified angle.
     * @param angle The given rotation angle in radians.
     * @param x The initial x coordinate of the given pixel.
     * @param y The initial y coordinate of the given pixel.
     * @return The new x coordinate of the given pixel.
     */
    private static double rot_x(double angle, double x, double y) {
        double cos = Math.cos(angle-Math.PI/2);
        double sin = Math.sin(angle- Math.PI/2);
        return x * cos + y * -sin;
    }

    /**
     * Returns the new y coordinate of a pixel rotated by a specified angle.
     * @param angle The given rotation angle in radians.
     * @param x The initial x coordinate of the given pixel.
     * @param y The initial y coordinate of the given pixel.
     * @return The new y coordinate of the given pixel.
     */
    private static double rot_y(double angle, double x, double y) {
        double cos = Math.cos(angle-Math.PI/2);
        double sin = Math.sin(angle-Math.PI/2);
        return x * sin + y * cos;
    }

}
