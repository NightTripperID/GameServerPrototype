package com.github.nighttripperid.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * SpriteSheet is an image that contains images that can be selected to make com.bitburger.graphics.Sprite objects. Image data is loaded from a png file
 * that is typically comprised of multiple sprite images, e.g. the animation frames for a video game character such as Mario.
 *
 */
public class SpriteSheet {

    private URL url;
    public final int spriteWidth, spriteHeight;// pixel precision
    private int width, height; // pixel precision
    public int[] pixels;

    private Sprite[] sprites;

    /**
     * Creates a SpriteSheet object of specified width and height in pixel precision. A width = 16 and a height = 32 means
     * a SpriteSheet object 16 pixels wide and 32 pixels tall.
     * @param path: The url path of the image file.
     * @param spriteWidth: Width in pixels.
     * @param spriteHeight: Height in pixels.
     */
    public SpriteSheet(String path, int spriteWidth, int spriteHeight) {
        url = getClass().getClassLoader().getResource(path);
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        load();
    }

    /**
     * Creates a new SpriteSheet object from another SpriteSheet. This constructor is typically used to create
     * a smaller SpriteSheet object from a larger one. Width and height are in tile precision, meaning width = 5 and
     * height = 5 creates a SpriteSheet object 5 sprite tiles wide and 5 sprite tiles tall, each sprite being spriteSize
     * pixels wide and spriteSize pixels tall.
     * @param sheet: the SpriteSheet object to copy from.
     * @param xOfs: the starting x coordinate to copy from in tile precision.
     * @param yOfs: the starting y coordinate to copy from in tile precision.
     * @param width: the width of the new SpriteSheet in tile precision.
     * @param height: the height of the new SpriteSheet in tile precision.
     * @param spriteWidth: the width of the sprite tiles in pixel precision.
     * @param spriteHeight: the height of the sprite tiles in pixel precision.
     */
    public SpriteSheet(SpriteSheet sheet, int xOfs, int yOfs, int width, int height, int spriteWidth, int spriteHeight) {
        int x = xOfs * spriteWidth;
        int y = yOfs * spriteHeight;
        int w = width * spriteWidth;
        int h = height * spriteHeight;
        this.spriteWidth = w;
        this.spriteHeight = h;
        pixels = new int[w * h];
        for (int yy = 0; yy < h; yy++) {
            int yp = y + yy;
            for (int xx = 0; xx < w; xx++) {
                int xp = x + xx;
                pixels[xx + yy * w] = sheet.pixels[xp + yp * sheet.spriteWidth];
            }
        }
        int frame = 0;
        sprites = new Sprite[width * height];
        for (int yTile = 0; yTile < height; yTile++) {
            for (int xTile = 0; xTile < width; xTile++) {
                int[] spritePixels = new int[spriteWidth * spriteHeight];
                for (int yPixel = 0; yPixel < spriteHeight; yPixel++) {
                    for (int xPixel = 0; xPixel < spriteWidth; xPixel++) {
                        spritePixels[xPixel + yPixel * spriteWidth] = pixels[(xPixel + xTile * spriteWidth)
                                + (yPixel + yTile * spriteHeight) * this.spriteWidth];
                    }
                }
                Sprite sprite = new Sprite(spritePixels, spriteWidth, spriteHeight);
                sprites[frame++] = sprite;
            }
        }
    }

    /**
     * Loads image from specified URL to SpriteSheet object's pixel buffer.
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
        }
    }

    /**
     * Return array of Sprites contained by the SpriteSheet.
     * @return Array of Sprites contained by the SpriteSheet.
     */
    public Sprite[] getSprites() {
        return sprites;
    }

    /**
     * Returns the Sprite's width.
     * @return The Sprite's width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the Sprite's height.
     * @return The Sprite's height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the pixel array that represents the Sprite.
     * @return The pixel array that represents the Sprite.
     */
    public int[] getPixels() {
        return pixels;
    }
}