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

import com.github.nighttripperid.littleengine.model.Actor;
import com.github.nighttripperid.littleengine.model.behavior.RenderTask;
import com.github.nighttripperid.littleengine.model.scene.GameMap;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;

import java.util.Arrays;
import java.util.List;

public class ScreenBufferUpdater {

    private final ScreenBuffer screenBuffer;
    private final RenderTaskHandler renderTaskHandler;

    public ScreenBufferUpdater(RenderTaskHandler renderTaskHandler, ScreenBuffer screenBuffer) {
        this.screenBuffer = screenBuffer;
        this.renderTaskHandler = renderTaskHandler;
        this.renderTaskHandler.setScreenBuffer(this.screenBuffer);
    }

    public void renderEntities(List<Actor> actors, GameMap gameMap) {
        actors.sort(Actor::compareTo);
        actors.forEach(actor -> {
            if(actor.getSprite() != null ) {
                renderSprite(actor.getPhysBody().pos.x - gameMap.getScroll().x,
                        actor.getPhysBody().pos.y - gameMap.getScroll().y, actor.getSprite());
            }
        });
    }

    public void renderTileLayer(GameMap gameMap, int layerId) {

        if (!gameMap.getTileMap().hasLayer(layerId)) return;
        screenBuffer.setScroll(gameMap.getScroll());

        int x0 = (int) (float) gameMap.getScroll().x >> gameMap.getTileBitShift();
        int x1 = (((int) (float) gameMap.getScroll().x + screenBuffer.getWidth()) + (int) (float) gameMap.getTileSize().x)
                >> gameMap.getTileBitShift();
        int y0 = (int) (float) gameMap.getScroll().y >> gameMap.getTileBitShift();
        int y1 = (((int) (float) gameMap.getScroll().y + screenBuffer.getHeight()) + (int) (float) gameMap.getTileSize().y)
                >> gameMap.getTileBitShift();

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                if (gameMap.getTileMap().getTileId(layerId, x, y) == null ||
                        gameMap.getTileMap().getTileId(layerId, x, y) == 0) {
                    continue;
                }
                renderSprite((x << gameMap.getTileBitShift()) - screenBuffer.getScroll().x,
                        (y << gameMap.getTileBitShift()) - screenBuffer.getScroll().y,
                        gameMap.getTileMap().getTile(gameMap.getTileset(), layerId, x, y).getSprite());
            }
        }
    }

    public void clearScreenBuffer() {
        Arrays.fill(screenBuffer.getPixels(), 0x00008b);
    }

    public void renderSprite(float x, float y, Sprite sprite) {
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

    public void renderSprite(float x, float y, int scale, Sprite sprite) {
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

    public void processRenderTasks(List<RenderTask> renderTasks) {
        renderTasks.sort(RenderTask::compareTo);
        renderTasks.forEach(renderTaskHandler::process);
    }

    public ScreenBuffer getScreenBuffer() {
        return screenBuffer;
    }
}
