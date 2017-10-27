package graphics;

import com.sun.istack.internal.NotNull;

/**
 * Object that contains a pixel buffer matching specified dimensions and methods for rendering
 * pixels on screen.
 * @author Noah Dering
 *
 */
public class Screen {

    private final int width, height, scale;

    private int[] pixels;

    private double xOfs, yOfs;

    /**
     * Creates a screen with specified dimensions.
     * @param width The width of the screen.
     * @param height The height of the screen.
     */
    public Screen(int width, int height, int scale) {

        if(width < 1)
            throw new IllegalArgumentException("Screen width must be at least 1 pixel");
        if(height < 1)
            throw new IllegalArgumentException("Screen height must be at least 1 pixel");

        this.width = width;
        this.height = height;
        this.scale = scale;
        pixels = new int[width * height];
    }

    /**
     * Fills screen buffer with specified color.
     * @param col The color to fill the screen with.
     */
    public void fill(int col) {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = col;
    }

    /**
     * Overwrites screen buffer with black (0x000000).
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = 0x000000;
    }

    /**
     * Draws a hollow rectangle at specified coordinates using specified width, height, and color.
     * @param x The x coordinate of the rectangle's top left corner.
     * @param y The y coordinate of the rectangle's top left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param col The specified color for the rectangle's outline using rgb hex notation (e.g. 0xaabbcc)
     */
    public void drawRect(double x, double y, int width, int height, int col) {

        for (int yy = (int)y; yy < (int)y + height; yy++) {
            if (yy < 0 || yy >= this.height)
                continue;
            for (int xx = (int)x; xx < (int)x + width; xx++) {
                if (xx < 0 || xx >= this.width)
                   continue;
                if (xx == (int)x || xx == (int)x + width - 1 || yy == (int)y || yy == (int)y + height - 1)
                    pixels[xx + yy * this.width] = col;
            }
        }
    }

    /**
     * Draws a solid rectangle at specified coordinates using specified width, height, and color.
     * @param x The x coordinate of the rectangle's top left corner.
     * @param y The y coordinate of the rectangle's top left corner.
     * @param width The width of the rectangle.
     * @param height The height of the rectangle.
     * @param col The specified color of the rectangle using rgb hex notation (e.g. 0xaabbcc)
     */
    public void fillRect(double x, double y, int width, int height, int col) {
        for (int yy = (int)y; yy < y + height; yy++) {
            if(yy < 0 || yy >= this.height)
                continue;
            for (int xx = (int)x; xx < x + width; xx++) {
                if (xx < 0 || xx >= this.width)
                    continue;
                pixels[xx + yy * this.width] = col;
            }
        }
    }

    /**
     * Renders an 8x8 character at specified coordinates using specified color and character array representation.
     * Use with included class Font8x8 only.
     *
     * @param x The x coordinate of the character's top left corner.
     * @param y The y coordinate of the character's top right corner.
     * @param col The specified color of the character using rgb hex notation (e.g. 0xaabbcc)
     * @param character The character to be rendered.
     */
    private void renderChar8x8(double x, double y, int col, @NotNull char[] character) {

        for (int yy = 0; yy < 8; yy++)
            for (int xx = 0; xx < 8; xx++)
                if (character[xx + (yy << 3)] == '#')
                    renderPixel(x + xx, y + yy, col);
    }

    /**
     * Renders a 5x5 character at specified coordinates using specified color and character array representation.
     * Use with included class Font5x5 only.
     *
     * @param x The x coordinate of the character's top left corner.
     * @param y The y coordinate of the character's top left corner.
     * @param col The specified color of the character using rgb hex notation (e.g. 0xaabbcc)
     * @param character The character to be rendered.
     */
    private void renderChar5x5(double x, double y, int col, @NotNull char[] character) {

        for (int yy = 0; yy < 5; yy++)
            for (int xx = 0; xx < 5; xx++)
                if (character[xx + yy * 5] == '#')
                    renderPixel(x + xx, y + yy, col);
    }

    /**
     * Renders specified string using specified color and included 8x8 font at specified coordinates.
     * @param x The x coordinate of the string's top left corner.
     * @param y The y coordinate of the string's top left corner.
     * @param col The specified color for the string using rgb hex notation (e.g. 0xaabbcc)
     * @param string The string to be rendered.
     */
    public void renderString8x8(double x, double y, int col, @NotNull String string) {

        for (int i = 0; i < string.length(); i++)
            renderChar8x8(x + (i << 3), y, col, Font8x8.getChar(string.charAt(i)));
    }

    /**
     * Renders specified string using specified color and included 5x5 font at specified coordinates.
     * @param x The x coordinate of the string's top left corner.
     * @param y The y coordinate of the string's top left corner.
     * @param col The specified color for the string using rgb hex notation (e.g. 0xaabbcc)
     * @param string The string to be rendered.
     */
    public void renderString5x5(double x, double y, int col, @NotNull String string) {

        for (int i = 0; i < string.length(); i++)
            renderChar5x5(x + (i * 5), y, col, Font5x5.getChar(string.charAt(i)));
    }

    public void renderPixels(int[] pixels) {
        if(pixels.length != this.pixels.length)
            throw new IllegalArgumentException("pixel lengths (i.e. screen dimensions) must match.");

        int index = 0;
        for(int p : pixels)
            this.pixels[index++] = p;
    }

    /**
     * Renders pixel at specified index using specified color.
     * @param index The screen buffer index of the pixel.
     * @param col The specified color for the pixel using rgb hex notation (e.g. 0xaabbcc)
     */
    public void renderPixel(int index, int col) {
        if(index < 0 || index >= width * height)
            return;

        pixels[index] = col;
    }

    /**
     * Renders pixel at specified coordinates using specified color.
     * @param x The x coordinate of the pixel.
     * @param y The y coordinate of the pixel.
     * @param col The specified color for the pixel using rgb hex notation (e.g. 0xaabbcc).
     */
    public void renderPixel(double x, double y, int col) {
        if(x < 0 || x >= width || y < 0 || y >= height)
            return;

        pixels[(int)x + (int)y * width] = col;
    }

    /**
     * Renders specified sprite at specified screen coordinates.
     * @param x The x coordinate on screen.
     * @param y The y coordinate on screen.
     * @param sprite The sprite to render.
     */
    public void renderSprite(double x, double y, @NotNull Sprite sprite) {
        for (int yy = 0; yy < sprite.getHeight(); yy++) {
            if (yy + y <  0 || yy + y >= this.height)
                continue;
            for (int xx = 0; xx < sprite.getWidth(); xx++) {
                if (xx + x < 0 || xx + x >= this.width)
                    continue;
                if (sprite.pixels[xx + yy * sprite.getWidth()] != 0xffff00ff)
                    pixels[xx + (int) x + (yy + (int) y) * width] = sprite.pixels[xx + yy * sprite.getWidth()];
            }
        }
    }

    public void renderSprite(double x, double y, int scale, @NotNull Sprite sprite) {
        for (int yy = 0; yy < sprite.getHeight() * scale; yy += scale) {
            for (int xx = 0; xx < sprite.getWidth() * scale; xx += scale) {
                for (int yyy = yy; yyy < yy + scale; yyy++) {
                    if (yyy + y < 0 || yyy + y >= this.height)
                        continue;
                    for (int xxx = xx; xxx < xx + scale; xxx++) {
                       if (xxx + x < 0 || xxx + x >= this.width)
                           continue;
                        if (sprite.pixels[(xx / scale) + (yy / scale) * sprite.getWidth()] != 0xffff00ff)
                            pixels[xxx + (int) x + (yyy + (int) y) * this.width]
                                    = sprite.pixels[(xx / scale) + (yy / scale) * sprite.getWidth()];
                    }
                }
            }
        }
    }

    public void renderSprite(double x, double y, int scale, int colorFill, @NotNull Sprite sprite) {
        for (int yy = 0; yy < sprite.getHeight() * scale; yy += scale) {
            for (int xx = 0; xx < sprite.getWidth() * scale; xx += scale) {
                for (int yyy = yy; yyy < yy + scale; yyy++) {
                    if (yyy + y < 0 || yyy + y >= this.height)
                        continue;
                    for (int xxx = xx; xxx < xx + scale; xxx++) {
                        if (xxx + x < 0 || xxx + x >= this.width)
                            continue;
                        if (sprite.pixels[(xx / scale) + (yy / scale) * sprite.getWidth()] != 0xffff00ff)
                            pixels[xxx + (int) x + (yyy + (int) y) * this.width] = colorFill;
                    }
                }
            }
        }
    }

    public void renderSpriteColorFill(double x, double y, int color, @NotNull Sprite sprite) {
        for (int yy = 0; yy < sprite.getHeight(); yy++) {
            if (yy + y <  0 || yy + y >= height)
                continue;
            for (int xx = 0; xx < sprite.getWidth(); xx++) {
                if (xx + x < 0 || xx + x >= width)
                    continue;
                if (sprite.pixels[xx + yy * sprite.getWidth()] != 0xffff00ff)
                    pixels[xx + (int) x + (yy + (int) y) * this.width] = color;
            }
        }
    }

    public void renderTile(int xPos, int yPos, @NotNull Tile tile) {
        int tileW = tile.getSprite().getWidth();
        int tileH = tile.getSprite().getHeight();

        xPos -= xOfs;
        yPos -= yOfs;
        for(int y = 0; y < tileH; y++) {
            int yAbs = y + yPos;
            for(int x = 0; x < tileW; x++) {
                int xAbs = x + xPos;
                if(xAbs < 0 || xAbs >= width || yAbs < 0 || yAbs >= height)
                    continue;
                pixels[xAbs + yAbs * width] = tile.getSprite().pixels[x + y * tileW];
            }
        }
    }

    /**
     * Gets pixel specified at index.
     * @param index The index of the pixel.
     * @return The pixel, represented by an rgb color (0x000000 thru 0xffffff).
     */
    public int getPixel(int index) {
        return pixels[index];
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

    /**
     * Gets the screen's screenScale
     * @return the screenScale
     */
    public int getScale() {
        return scale;
    }

    public void setOffset(double xOfs, double yOfs) {
        this.xOfs = xOfs;
        this.yOfs = yOfs;
    }

    public int[] getPixels() {
        return  pixels;
    }
}