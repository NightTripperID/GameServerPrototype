/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.nighttripperid.littleengine.component;

import com.github.nighttripperid.littleengine.model.entity.Entity;
import com.github.nighttripperid.littleengine.model.gamestate.GameMap;
import com.github.nighttripperid.littleengine.model.entity.RenderRequest;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

public class ScreenBufferUpdater {

    private final ScreenBuffer screenBuffer;
    private final RenderRequestProcessor renderRequestProcessor;

    public ScreenBufferUpdater(RenderRequestProcessor renderRequestProcessor, ScreenBuffer screenBuffer) {
        this.screenBuffer = screenBuffer;
        this. renderRequestProcessor = renderRequestProcessor;
        this.renderRequestProcessor.setScreenBuffer(this.screenBuffer);
    }

    public void renderEntities(List<Entity> entities, GameMap gameMap) {
        entities.sort(Entity::compareTo);
        entities.forEach(entity -> {
            if(entity.getSprite() != null ) {
                renderSprite(entity.getRect().pos.x - gameMap.getScroll().x,
                        entity.getRect().pos.y - gameMap.getScroll().y, entity.getSprite());
            }
        });
    }

    public void renderTileLayer(GameMap gameMap, int layerId) {

        screenBuffer.setScroll(gameMap.getScroll());

        int x0 = (int) (double) gameMap.getScroll().x >> gameMap.getTileBitShift();
        int x1 = (((int) (double) gameMap.getScroll().x + screenBuffer.getWidth()) + gameMap.getTileSize())
                >> gameMap.getTileBitShift();
        int y0 = (int) (double) gameMap.getScroll().y >> gameMap.getTileBitShift();
        int y1 = (((int) (double) gameMap.getScroll().y + screenBuffer.getHeight()) + gameMap.getTileSize())
                >> gameMap.getTileBitShift();

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                if (gameMap.getTileMap().hasLayer(layerId)) {
                    renderSprite((x << gameMap.getTileBitShift()) - screenBuffer.getScroll().x,
                            (y << gameMap.getTileBitShift()) - screenBuffer.getScroll().y,
                            gameMap.getTileMap().getTile(gameMap.getTileset(), layerId, x, y).getSprite());
                }
            }
        }
    }

    public void clearScreenBuffer() {
        Arrays.fill(screenBuffer.getPixels(), 0x000000);
    }

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


    public void processRenderRequests(List<RenderRequest> renderRequests) {
        renderRequests.sort(RenderRequest::compareTo);
        renderRequests.forEach(renderRequestProcessor::process);
    }

    public ScreenBuffer getScreenBuffer() {
        return screenBuffer;
    }
}
