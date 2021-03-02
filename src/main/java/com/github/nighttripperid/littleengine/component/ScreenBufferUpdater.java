package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.gamestate.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.gamestate.RenderRequest;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

public class ScreenBufferUpdater {

    private final ScreenBuffer screenBuffer;
    private final RenderRequestProcessor renderRequestProcessor;

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

        renderRequestProcessor = new RenderRequestProcessor();
    }

    public void renderEntities(List<Entity> entities, GameMap gameMap) {
        entities.forEach(entity -> {
            renderSprite(entity.getPosition().x - gameMap.getScroll().x,
                    entity.getPosition().y - gameMap.getScroll().y, entity.getSprite());
        });
    }

    public void renderTileLayer(GameMap gameMap, String layerName) {

        if (gameMap.getTileMapLookups().get(layerName) == null) {
            return;
        }

        screenBuffer.setScroll(gameMap.getScroll());

        int x0 = (int) gameMap.getScroll().x >> gameMap.getTileBitShift();
        int x1 = (((int) gameMap.getScroll().x + screenBuffer.getWidth()) + gameMap.getTileSize())
                >> gameMap.getTileBitShift();
        int y0 = (int) gameMap.getScroll().y >> gameMap.getTileBitShift();
        int y1 = (((int) gameMap.getScroll().y + screenBuffer.getHeight()) + gameMap.getTileSize())
                >> gameMap.getTileBitShift();

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                if (gameMap.getTileMapLookups().get(layerName) != null) {
                    renderSprite((x << gameMap.getTileBitShift()) - screenBuffer.getScroll().x,
                            (y << gameMap.getTileBitShift()) - screenBuffer.getScroll().y,
                            gameMap.getMapTileObject(layerName, x, y).getSprite());
                }
            }

        }
    }

    /**
     * Overwrites screen buffer with black (0x000000).
     */
    public void clearScreenBuffer() {
        Arrays.fill(screenBuffer.getPixels(), 0x000000);
    }

    /**
     * Renders specified Sprite at provided screen coordinates.
     *
     * @param x      The x coordinate on screen.
     * @param y      The y coordinate on screen.
     * @param sprite The sprite to Render.
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

    public void processRenderRequests(List<RenderRequest> renderRequests) {
        renderRequests.forEach(renderRequest ->
                renderRequest.process(renderRequestProcessor, screenBuffer));
    }

    public ScreenBuffer getScreenBuffer() {
        return screenBuffer;
    }
}
