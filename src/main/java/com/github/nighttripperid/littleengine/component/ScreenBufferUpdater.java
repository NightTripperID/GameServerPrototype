package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.constant.Font5x5;
import com.github.nighttripperid.littleengine.constant.Font8x8;
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.gamestate.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.graphics.Tile;

import java.util.Arrays;

public class ScreenBufferUpdater {

    private final ScreenBuffer screenBuffer;

    /**
     * Creates a screen with specified dimensions.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    public ScreenBufferUpdater(int width, int height, int scale) {
        if (width < 1)
            throw new IllegalArgumentException("Screen width must be at least 1 pixel");
        if (height < 1)
            throw new IllegalArgumentException("Screen height must be at least 1 pixel");

        screenBuffer = new ScreenBuffer();
        screenBuffer.setWidth(width);
        screenBuffer.setHeight(height);
        screenBuffer.setScale(scale);
        screenBuffer.setPixels(new int[width * height]);
    }

    public void renderEntityToScreenBuffer(Entity entity, GameMap gameMap) {
        renderSprite(entity.getPosition().x - gameMap.getScroll().x,
                entity.getPosition().y - gameMap.getScroll().y, entity.getSprite());
    }

    public void renderTileLayerToScreenBuffer(GameMap gameMap, String layerName) {

        if (gameMap.getMapTileHashMap().get(layerName) == null) {
            return;
        }

        screenBuffer.setScroll(gameMap.getScroll());

        int x0 = (int) gameMap.getScroll().x >> gameMap.getTileBitShift();
        int x1 = (((int) gameMap.getScroll().x + screenBuffer.getWidth()) + gameMap.getTileSize())
                >> gameMap.getTileBitShift();
        int y0 = (int) gameMap.getScroll().y >> gameMap.getTileBitShift();
        int y1 = (((int) gameMap.getScroll().y + screenBuffer.getHeight()) + gameMap.getTileSize())
                >> gameMap.getTileBitShift();

        Integer[] mapTiles = gameMap.getMapTileHashMap().get(layerName);
        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                if (mapTiles != null) {
                    renderTile(x << gameMap.getTileBitShift(), y << gameMap.getTileBitShift(),
                            gameMap.getMapTileObject(mapTiles, x, y));
                }
            }

        }
    }


    // TODO: move pure render functions to a separate util class (maybe)

    /**
     * Fills screen buffer with specified color.
     *
     * @param col The color to fill the screen with.
     */
    public void fill(int col) {
        Arrays.fill(screenBuffer.getPixels(), col);
    }

    /**
     * Overwrites screen buffer with black (0x000000).
     */
    public void clearScreenBuffer() {
        Arrays.fill(screenBuffer.getPixels(), 0x000000);
    }

    /**
     * Draws a hollow rectangle at specified coordinates using specified width, height, and color.
     *
     * @param x      The x coordinate of the rectangle's top left corner.
     * @param y      The y coordinate of the rectangle's top left corner.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param col    The specified color for the rectangle's outline using rgb hex notation (e.g. 0xaabbcc)
     */
    public void drawRect(double x, double y, int width, int height, int col) {
        for (int yy = (int) y; yy < (int) y + height; yy++) {
            if (yy < 0 || yy >= screenBuffer.getHeight())
                continue;
            for (int xx = (int) x; xx < (int) x + width; xx++) {
                if (xx < 0 || xx >= screenBuffer.getWidth())
                    continue;
                if (xx == (int) x || xx == (int) x + width - 1 || yy == (int) y || yy == (int) y + height - 1)
                    screenBuffer.getPixels()[xx + yy * screenBuffer.getWidth()] = col;
            }
        }
    }

    /**
     * Draws a solid rectangle at specified coordinates using specified width, height, and color.
     *
     * @param x      The x coordinate of the rectangle's top left corner.
     * @param y      The y coordinate of the rectangle's top left corner.
     * @param width  The width of the rectangle.
     * @param height The height of the rectangle.
     * @param col    The specified color of the rectangle using rgb hex notation (e.g. 0xaabbcc)
     */
    public void fillRect(double x, double y, int width, int height, int col) {
        for (int yy = (int) y; yy < y + height; yy++) {
            if (yy < 0 || yy >= screenBuffer.getHeight())
                continue;
            for (int xx = (int) x; xx < x + width; xx++) {
                if (xx < 0 || xx >= screenBuffer.getWidth())
                    continue;
                screenBuffer.getPixels()[xx + yy * screenBuffer.getWidth()] = col;
            }
        }
    }

    /**
     * Renders an 8x8 character at specified coordinates using specified color and character array representation.
     * Use with included class Font8x8 only.
     *
     * @param x         The x coordinate of the character's top left corner.
     * @param y         The y coordinate of the character's top right corner.
     * @param col       The specified color of the character using rgb hex notation (e.g. 0xaabbcc)
     * @param character The character to be rendered.
     */
    private void renderChar8x8(double x, double y, int col, Character[] character) {
        for (int yy = 0; yy < 8; yy++)
            for (int xx = 0; xx < 8; xx++)
                if (character[xx + (yy << 3)] == '#')
                    renderPixel(x + xx, y + yy, col);
    }

    /**
     * Renders a 5x5 character at specified coordinates using specified color and character array representation.
     * Use with included class Font5x5 only.
     *
     * @param x         The x coordinate of the character's top left corner.
     * @param y         The y coordinate of the character's top left corner.
     * @param col       The specified color of the character using rgb hex notation (e.g. 0xaabbcc)
     * @param character The character to be rendered.
     */
    private void renderChar5x5(double x, double y, int col, Character[] character) {
        for (int yy = 0; yy < 5; yy++)
            for (int xx = 0; xx < 5; xx++)
                if (character[xx + yy * 5] == '#')
                    renderPixel(x + xx, y + yy, col);
    }

    /**
     * Renders specified string using specified color and included 8x8 font at specified coordinates.
     *
     * @param x      The x coordinate of the string's top left corner.
     * @param y      The y coordinate of the string's top left corner.
     * @param col    The specified color for the string using rgb hex notation (e.g. 0xaabbcc)
     * @param string The string to be rendered.
     */
    public void renderString8x8(double x, double y, int col, String string) {
        for (int i = 0; i < string.length(); i++)
            renderChar8x8(x + (i << 3), y, col, Font8x8.getChar(string.charAt(i)));
    }

    /**
     * Renders specified string using specified color and included 5x5 font at specified coordinates.
     *
     * @param x      The x coordinate of the string's top left corner.
     * @param y      The y coordinate of the string's top left corner.
     * @param col    The specified color for the string using rgb hex notation (e.g. 0xaabbcc)
     * @param string The string to be rendered.
     */
    public void renderString5x5(double x, double y, int col, String string) {
        for (int i = 0; i < string.length(); i++)
            renderChar5x5(x + (i * 5), y, col, Font5x5.getChar(string.charAt(i)));
    }

    /**
     * Renders the given pixel array on the Screen.
     *
     * @param pixels The pixels to Renderable.
     */
    public void renderPixels(int[] pixels) {
        if (pixels.length != screenBuffer.getPixels().length)
            throw new IllegalArgumentException("pixel lengths (i.e. screen dimensions) must match.");

        System.arraycopy(pixels, 0, screenBuffer.getPixels(), 0, pixels.length);
    }

    /**
     * Renders pixel at specified index using specified color.
     *
     * @param index The screen buffer index of the pixel.
     * @param col   The specified color for the pixel using rgb hex notation (e.g. 0xaabbcc)
     */
    public void renderPixel(int index, int col) {
        if (index < 0 || index >= screenBuffer.getWidth() * screenBuffer.getHeight())
            return;

        screenBuffer.getPixels()[index] = col;
    }

    /**
     * Renders pixel at specified coordinates using specified color.
     *
     * @param x   The x coordinate of the pixel.
     * @param y   The y coordinate of the pixel.
     * @param col The specified color for the pixel using rgb hex notation (e.g. 0xaabbcc).
     */
    public void renderPixel(double x, double y, int col) {
        if (x < 0 || x >= screenBuffer.getWidth() || y < 0 || y >= screenBuffer.getHeight())
            return;

        screenBuffer.getPixels()[(int) x + (int) y * screenBuffer.getWidth()] = col;
    }

    /**
     * Renders a tile at given Screen coordinates.
     *
     * @param xPos The x position of the Tile in pixel precision.
     * @param yPos The y position of the Tile in pixel precision.
     * @param tile The Tile to Renderable.
     */
    public void renderTile(int xPos, int yPos, Tile tile) {
        int tileW = tile.getSprite().width;
        int tileH = tile.getSprite().height;

        xPos -= screenBuffer.getScroll().x;
        yPos -= screenBuffer.getScroll().y;
        for (int y = 0; y < tileH; y++) {
            int yAbs = y + yPos;
            for (int x = 0; x < tileW; x++) {
                int xAbs = x + xPos;
                if (xAbs < 0 || xAbs >= screenBuffer.getWidth() || yAbs < 0 || yAbs >= screenBuffer.getHeight()
                        || tile.getSprite().pixels[x + y * tileW] == 0xFFFF00FF)
                    continue;
                screenBuffer.getPixels()[xAbs + yAbs * screenBuffer.getWidth()] = tile.getSprite().pixels[x + y * tileW];
            }
        }
    }

    /**
     * Renders specified Sprite at provided screen coordinates.
     *
     * @param x      The x coordinate on screen.
     * @param y      The y coordinate on screen.
     * @param sprite The sprite to Renderable.
     */
    public void renderSprite(double x, double y, Sprite sprite) {
        for (int yy = 0; yy < sprite.height; yy++) {
            if (yy + y < 0 || yy + y >= this.screenBuffer.getHeight())
                continue;
            for (int xx = 0; xx < sprite.width; xx++) {
                if (xx + x < 0 || xx + x >= this.screenBuffer.getWidth())
                    continue;
                if (sprite.pixels[xx + yy * sprite.width] != 0xffff00ff)
                    screenBuffer.getPixels()[xx + (int) x + (yy + (int) y) * screenBuffer.getWidth()]
                            = sprite.pixels[xx + yy * sprite.width];
            }
        }
    }

    /**
     * Renders specified Sprite at provided screen coordinates with the given scale.
     *
     * @param x      The x coordinate on screen.
     * @param y      The y coordinate on screen.
     * @param scale  The scale at which to Renderable the Sprite.
     * @param sprite The sprite to Renderable.
     */
    public void renderSprite(double x, double y, int scale, Sprite sprite) {
        for (int yy = 0; yy < sprite.height * scale; yy += scale) {
            for (int xx = 0; xx < sprite.width * scale; xx += scale) {
                for (int yyy = yy; yyy < yy + scale; yyy++) {
                    if (yyy + y < 0 || yyy + y >= screenBuffer.getHeight())
                        continue;
                    for (int xxx = xx; xxx < xx + scale; xxx++) {
                        if (xxx + x < 0 || xxx + x >= screenBuffer.getWidth())
                            continue;
                        if (sprite.pixels[(xx / scale) + (yy / scale) * sprite.width] != 0xffff00ff)
                            screenBuffer.getPixels()[xxx + (int) x + (yyy + (int) y) * screenBuffer.getWidth()]
                                    = sprite.pixels[(xx / scale) + (yy / scale) * sprite.width];
                    }
                }
            }
        }
    }

    /**
     * Renders specified Sprite at provided screen coordinates with the given scale and fills the sprite with the
     * specified color.
     *
     * @param x      The x coordinate on screen.
     * @param y      The y coordinate on screen.
     * @param scale  The scale at which to Renderable the Sprite.
     * @param color  The color to fill the Sprite with.
     * @param sprite The Sprite to Renderable.
     */
    public void renderSpriteColorFill(double x, double y, int scale, int color, Sprite sprite) {
        for (int yy = 0; yy < sprite.height * scale; yy += scale) {
            for (int xx = 0; xx < sprite.width * scale; xx += scale) {
                for (int yyy = yy; yyy < yy + scale; yyy++) {
                    if (yyy + y < 0 || yyy + y >= screenBuffer.getHeight())
                        continue;
                    for (int xxx = xx; xxx < xx + scale; xxx++) {
                        if (xxx + x < 0 || xxx + x >= screenBuffer.getWidth())
                            continue;
                        if (sprite.pixels[(xx / scale) + (yy / scale) * sprite.width] != 0xffff00ff)
                            screenBuffer.getPixels()[xxx + (int) x + (yyy + (int) y) * screenBuffer.getWidth()] = color;
                    }
                }
            }
        }
    }

    /**
     * Renders a Sprite at given screen coordinates and fills the sprite with the
     * a color.
     *
     * @param x      The x coordinate on screen.
     * @param y      The y coordinate on screen.
     * @param color  The color to fill the Sprite with.
     * @param sprite The Sprite to Renderable.
     */
    public void renderSpriteColorFill(double x, double y, int color, Sprite sprite) {
        for (int yy = 0; yy < sprite.height; yy++) {
            if (yy + y < 0 || yy + y >= screenBuffer.getHeight())
                continue;
            for (int xx = 0; xx < sprite.width; xx++) {
                if (xx + x < 0 || xx + x >= screenBuffer.getWidth())
                    continue;
                if (sprite.pixels[xx + yy * sprite.width] != 0xffff00ff)
                    screenBuffer.getPixels()[xx + (int) x + (yy + (int) y) * screenBuffer.getWidth()] = color;
            }
        }
    }

    /**
     * Gets pixel at specified xy coordinates
     *
     * @param x the given x coordinate.
     * @param y the given y coordinate.
     * @return The pixel, represented by an rgb color (0x000000 thru 0xffffff).
     */
    public int getPixel(int x, int y) {
        return screenBuffer.getPixels()[x + y * screenBuffer.getWidth()];
    }

    /**
     * Gets pixel at specified index.
     *
     * @param index The index of the pixel.
     * @return The pixel, represented by an rgb color (0x000000 thru 0xffffff).
     */
    public int getPixel(int index) {
        return screenBuffer.getPixels()[index];
    }

    /**
     * Gets the screen's width
     *
     * @return the width
     */
    public int getWidth() {
        return screenBuffer.getWidth();
    }

    /**
     * Gets the screen's height
     *
     * @return the height
     */
    public int getHeight() {
        return screenBuffer.getHeight();
    }

    /**
     * Gets the screen's screenScale
     *
     * @return the screenScale
     */
    public int getScale() {
        return screenBuffer.getScale();
    }

//    /**
//     * Sets the coordinate offsets in relation to the tilemap to be rendered.
//     *
//     * @param xScroll The x offset in relation to the tilemap.
//     * @param yScroll The y offset in relation to the tilemap.
//     */
//    public void setScroll(double xScroll, double yScroll) {
//        screenBuffer.setXScroll(xScroll);
//        screenBuffer.setYScroll(yScroll);
//    }

    public void setScroll(PointDouble scroll) {
        screenBuffer.setScroll(scroll);
    }

    /**
     * Returns the pixel array representing the screen as a whole.
     *
     * @return the pixel array representing the screen
     */
    public int[] getPixels() {
        int[] pixels = new int[screenBuffer.getPixels().length];
        System.arraycopy(screenBuffer.getPixels(), 0, pixels, 0, screenBuffer.getPixels().length);
        return pixels;
    }

    public ScreenBuffer getScreenBuffer() {
        return screenBuffer;
    }
}
