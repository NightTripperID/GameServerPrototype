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


import com.github.nighttripperid.littleengine.model.gamestate.Intent;
import com.github.nighttripperid.littleengine.model.graphics.ScreenBuffer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Game {

    private Thread thread;
    private String title = "";
    private boolean running;

    private IOController ioController;
    private final GameStateUpdater gameStateUpdater;

    public Game(int width, int height, int scale, String title) {
        this.title = title;
        ioController = new IOController(width, height, scale);

        ScreenBufferUpdater screenBufferUpdater = new ScreenBufferUpdater(new RenderRequestProcessor(),
                                                  new ScreenBuffer(width, height, scale));

        gameStateUpdater = new GameStateUpdater(screenBufferUpdater, new GameStateStackController());
    }

    private synchronized void start() {
        running = true;
        thread = new Thread(mainLoop, "LittleEngine");
        thread.start();
    }

    private void stop() {
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            log.error("Error stopping game loop: {}", e.getMessage());
        }
    }

    private final Runnable mainLoop = () -> {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000D / 60D;
        double delta = 0D;
        int frames = 0;
        int updates = 0;

        ioController.requestFocus();

        double before, after, elapsed = 0;
        while (running) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                before = System.nanoTime() / 1000000000D;
                update(elapsed); // 60 times per second
                after = System.nanoTime() / 1000000000D;
                elapsed = after - before;
                delta--;
                updates++;
            }

            render(); // as fast as possible
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                ioController.setTitle(title + " | " + updates + " ups " + " | " + frames + " fps");
                frames = 0;
                updates = 0;
            }
        }
        stop();
    };

    private void update(double elapsedTime) {
        ioController.updateInput();
        gameStateUpdater.update(elapsedTime);
        gameStateUpdater.renderToScreenBuffer();
    }

    private void render() {
        ioController.renderBufferToScreen(gameStateUpdater.getScreenBufferUpdater().getScreenBuffer());
    }

    public void start(Intent intent) {
        gameStateUpdater.getGameStateStackController().pushGameState(intent);
        start();
    }
}
