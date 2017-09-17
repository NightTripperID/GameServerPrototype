package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * graphics.SpriteSheet is an image that contains images that can be selected to make graphics.Sprite objects. Image data is loaded from a png file
 * that is typically comprised of multiple sprite images, e.g. the animation frames for a video game character such as Mario.
 * @author Noah
 *
 */
public class SpriteSheet {

    private URL url;
    public final int size; // pixel precision
    public final int spriteWidth, spriteHeight;// pixel precision
    private int width, height; // pixel precision
    public int[] pixels;

    private Sprite[] sprites;

    /**
     * Creates a graphics.SpriteSheet object of specified size dimensions in pixel precision. A size of 16 means
     * a graphics.SpriteSheet object 16 pixels wide and 16 pixels tall.
     * @param path
     * @param size
     */
    public SpriteSheet(String path, int size) {
        url = getClass().getClassLoader().getResource(path);
        this.size = size;
        spriteWidth = size;
        spriteHeight = size;
        pixels = new int[size * size];
        load();
    }

    /**
     * Creates a graphics.SpriteSheet object of specified width and height in pixel precision. A width = 16 and a height = 32 means
     * a graphics.SpriteSheet object 16 pixels wide and 32 pixels tall.
     * @param path: The url path of the image file.
     * @param width: Width in tiles.
     * @param height: Height in tiles.
     */
    public SpriteSheet(String path, int width, int height) {
        url = getClass().getClassLoader().getResource(path);
        size = -1;
        spriteWidth = width;
        spriteHeight = height;
        pixels = new int[spriteWidth * spriteHeight];
        load();
    }

    /**
     * Creates a new graphics.SpriteSheet object from another graphics.SpriteSheet. This constructor is typically used to create
     * a smaller graphics.SpriteSheet object from a larger one. Width and height are in tile precision, meaning width = 5 and
     * height = 5 creates a graphics.SpriteSheet object 5 sprite tiles wide and 5 sprite tiles tall, each sprite being spriteSize
     * pixels wide and spriteSize pixels tall.
     * @param sheet: the graphics.SpriteSheet object to copy from.
     * @param x: the starting x coordinate to copy from in tile precision.
     * @param y: the starting y coordinate to copy from in tile precision.
     * @param width: the width of the new graphics.SpriteSheet in tile precision.
     * @param height: the height of the new graphics.SpriteSheet in tile precision.
     * @param spriteSize: the width and height of the sprite tiles in pixel precision.
     */
    public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
        int xx = x * spriteSize;
        int yy = y * spriteSize;
        int w = width * spriteSize;
        int h = height * spriteSize;
        spriteWidth = w;
        spriteHeight = h;
        if (spriteWidth == spriteHeight)
            size = spriteWidth;
        else
            size = -1;
        pixels = new int[(w * h)];
        for (int y0 = 0; y0 < h; y0++) {
            int yp = yy + y0;
            for (int x0 = 0; x0 < w; x0++) {
                int xp = xx + x0;
                pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.spriteWidth];
            }
        }
        int frame = 0;
        sprites = new Sprite[width * height];
        for (int ya = 0; ya < height; ya++) {
            for (int xa = 0; xa < width; xa++) {
                int[] spritePixels = new int[spriteSize * spriteSize];
                for (int y0 = 0; y0 < spriteSize; y0++) {
                    for (int x0 = 0; x0 < spriteSize; x0++) {
                        spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize)
                                + (y0 + ya * spriteSize) * spriteWidth];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
                sprites[frame++] = sprite;
            }
        }
    }

    /**
     * Loads image from specified URL to graphics.SpriteSheet object's pixel buffer.
     */
    private void load() {

        try {
            System.out.print("Trying to load: " + url.toString() + "... ");
            BufferedImage image = ImageIO.read(url);
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0, width, height, pixels, 0, width);
            System.out.println("Success!");
        } catch (IOException e) {
            System.err.println("failed!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("failed!");
        }
    }

    public Sprite[] getSprites() {
        return sprites;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
}