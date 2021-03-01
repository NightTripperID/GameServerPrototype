package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.constant.Font5x5;
import com.github.nighttripperid.littleengine.constant.Font8x8;
import com.github.nighttripperid.littleengine.model.gamestate.RenderRequest;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;

import java.util.Arrays;

public class RenderRequestProcessor {

    public void process(RenderRequest renderRequest, ScreenBuffer screenBuffer) {
        renderRequest.process(this, screenBuffer);
    }

    public void fill(int col, ScreenBuffer screenBuffer) {
        Arrays.fill(screenBuffer.getPixels(), col);
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
    public void drawRect(double x, double y, int width, int height, int col, ScreenBuffer screenBuffer) {
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
    public void fillRect(double x, double y, int width, int height, int col, ScreenBuffer screenBuffer) {
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
    private void renderChar8x8(double x, double y, int col, Character[] character, ScreenBuffer screenBuffer) {
        for (int yy = 0; yy < 8; yy++)
            for (int xx = 0; xx < 8; xx++)
                if (character[xx + (yy << 3)] == '#')
                    renderPixel(x + xx, y + yy, col, screenBuffer);
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
    private void renderChar5x5(double x, double y, int col, Character[] character, ScreenBuffer screenBuffer) {
        for (int yy = 0; yy < 5; yy++)
            for (int xx = 0; xx < 5; xx++)
                if (character[xx + yy * 5] == '#')
                    renderPixel(x + xx, y + yy, col, screenBuffer);
    }

    /**
     * Renders specified string using specified color and included 8x8 font at specified coordinates.
     *
     * @param x      The x coordinate of the string's top left corner.
     * @param y      The y coordinate of the string's top left corner.
     * @param col    The specified color for the string using rgb hex notation (e.g. 0xaabbcc)
     * @param string The string to be rendered.
     */
    public void renderString8x8(double x, double y, int col, String string, ScreenBuffer screenBuffer) {
        for (int i = 0; i < string.length(); i++)
            renderChar8x8(x + (i << 3), y, col, Font8x8.getChar(string.charAt(i)), screenBuffer);
    }

    /**
     * Renders specified string using specified color and included 5x5 font at specified coordinates.
     *
     * @param x      The x coordinate of the string's top left corner.
     * @param y      The y coordinate of the string's top left corner.
     * @param col    The specified color for the string using rgb hex notation (e.g. 0xaabbcc)
     * @param string The string to be rendered.
     */
    public void renderString5x5(double x, double y, int col, String string, ScreenBuffer screenBuffer) {
        for (int i = 0; i < string.length(); i++)
            renderChar5x5(x + (i * 5), y, col, Font5x5.getChar(string.charAt(i)), screenBuffer);
    }

    /**
     * Renders the given pixel array on the Screen.
     *
     * @param pixels The pixels to Renderable.
     */
    public void renderPixels(int[] pixels, ScreenBuffer screenBuffer) {
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
    public void renderPixel(int index, int col, ScreenBuffer screenBuffer) {
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
    public void renderPixel(double x, double y, int col, ScreenBuffer screenBuffer) {
        if (x < 0 || x >= screenBuffer.getWidth() || y < 0 || y >= screenBuffer.getHeight())
            return;

        screenBuffer.getPixels()[(int) x + (int) y * screenBuffer.getWidth()] = col;
    }

}
