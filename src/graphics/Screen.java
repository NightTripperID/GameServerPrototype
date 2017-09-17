package graphics;

import com.sun.istack.internal.NotNull;
import graphics.Sprite;

/**
 * Object that contains a pixel buffer matching specified dimensions and methods for rendering
 * pixels on screen.
 * @author Noah Dering
 *
 */
public class Screen {

    private int width, height;

    private int[] pixels;

    /**
     * Creates a screen with specified dimensions.
     * @param width
     * @param height
     */
    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    /**
     * Fills screen buffer with specified color.
     * @param col
     */
    public void fill(int col) {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = col;
    }

    /**
     * Overwrites screen buffer with black (0x000000).
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0x000000;;
        }
    }

    /**
     * Draws a hollow rectangle at specified coordinates using specified width, height, and color.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param col
     */
    public void drawRect(double x, double y, int width, int height, int col) {
        for (int yy = (int)y; yy < (int)y + height; yy++) {
            for (int xx = (int)x; xx < (int)x + width; xx++) {

                if (xx == (int)x || xx == (int)x + width - 1 || yy == (int)y || yy == (int)y + height - 1)
                    if (xx > 0 && xx < this.width && yy > 0 && yy < this.height) pixels[xx + yy * this.width] = col;
            }
        }
    }

    /**
     * Draws a solid rectangle at specified coordinates using specified width, height, and color.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param col
     */
    public void fillRect(double x, double y, int width, int height, int col) {
        for (int yy = (int)y; yy < y + height; yy++) {
            for (int xx = (int)x; xx < x + width; xx++) {
                if (xx > 0 && xx < this.width && yy > 0 && yy < this.height) pixels[xx + yy * this.width] = col;
            }
        }
    }

    /**
     * Renders an 8x8 character at specified coordinates using specified color and character array representation.
     * Use with included class Font8x8 only.
     *
     * @param x
     * @param y
     * @param col
     * @param character
     */
    private void renderChar8x8(double x, double y, int col, char[] character) {

        for (int yy = 0; yy < 8; yy++) {
            for (int xx = 0; xx < 8; xx++) {
                if (character[xx + (yy << 3)] == '#') {
                    drawRect(x + xx, y + yy, 1, 1, col);
                }
            }
        }
    }

    /**
     * Renders a 5x5 character at specified coordinates using specified color and character array representation.
     * Use with included class Font5x5 only.
     *
     * @param x
     * @param y
     * @param col
     * @param character
     */
    private void renderChar5x5(double x, double y, int col, int[] character) {

        for (int yy = 0; yy < 5; yy++) {
            for (int xx = 0; xx < 5; xx++) {
                if (character[xx + (yy * 5)] == '#') {
                    drawRect(x + xx, y + yy, 1, 1, col);
                }
            }
        }
    }

    /**
     * Renders specified string using specified color and included 8x8 font at specified coordinates.
     * @param x
     * @param y
     * @param col
     * @param string
     */
    public void renderString8x8(double x, double y, int col, @NotNull String string) {

        for (int i = 0; i < string.length(); i++) {
            renderChar8x8(x + (i << 3), y, col, Font8x8.getChar(string.charAt(i)));
        }
    }

    /**
     * Renders specified string using specified color and included 5x5 font at specified coordinates.
     * @param x
     * @param y
     * @param col
     * @param string
     */
    public void renderString5x5(double x, double y, int col, @NotNull String string) {

        for (int i = 0; i < string.length(); i++) {
            renderChar5x5(x + (i * 5), y, col, Font5x5.getChar(string.charAt(i)));
        }
    }

    /**
     * Renders pixel at specified index using specified color.
     * @param index
     * @param col
     */
    public void renderPixel(int index, int col) {
        pixels[index] = col;
    }

    /**
     * Renders pixel at specified coordinates using specified color.
     * @param x
     * @param y
     * @param col
     */
    public void renderPixel(double x, double y, int col) {
        pixels[(int)x * (int)y] = col;
    }

    /**
     * Renders specified sprite at its contained coordinates.
     * @param sprite
     */
    public void renderSprite(@NotNull Sprite sprite) {
        for (int y = 0; y < sprite.height; y++) {
            for (int x = 0; x < sprite.width; x++) {
                if (x + sprite.getX() < this.width && y + sprite.getY() < this.height)
                    if (sprite.pixels[x + y * sprite.width] != 0xffff00ff)
                        pixels[x + sprite.getX() + (y + sprite.getY()) * width] = sprite.pixels[x + y * sprite.width];
            }
        }
    }

    /**
     * Renders specified sprite at specified coordinates using specified width, height, and pixels.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param pixels
     */
    public void renderSprite(double x, double y, int width, int height, int[] pixels) {

        for (int yy = 0; yy < height; yy++) {
            for (int xx = 0; xx < width; xx++) {
                if (xx + x < this.width && yy + y < this.height) {
                    if (pixels[xx + yy * width] != 0xffff00ff)
                        pixels[xx + (int)x + (yy + (int)y) * this.width] = pixels[xx + yy * width];
                }
            }
        }
    }

    /**
     * Gets pixel specified at index.
     * @param index
     * @return
     */
    public int getPixel(int index) {
        return pixels[index];
    }

    /**
     * Sets pixel specified at index to specified color.
     * @param index
     * @param col
     */
    public void setPixel(int index, int col) {
        pixels[index] = col;
    }

    /**
     * Returns a reference to the screen's pixel buffer. This method must be callable from outside the onRender loop, and is
     * therefore static. As such, pixels must be static, implying that all instances of screen will have the same pixel buffer
     * and contain the same image.
     * @return
     */
    public int[] getPixels() {
        return pixels;
    }

    /**
     * Gets the screen's width
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the screen's height
     * @return the height
     */
    public int getHeight() {
        return height;
    }
}
