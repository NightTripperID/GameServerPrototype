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

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

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

        ScreenBufferUpdater screenBufferUpdater = new ScreenBufferUpdater(new RenderTaskHandler(),
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

    Clock clock = Clock.systemDefaultZone();


    private final Runnable mainLoop = () -> {
        long timer = System.currentTimeMillis();
        int frames = 0;
        ioController.requestFocus();

        Instant before;
        Instant after = clock.instant();
        Duration elapsed;

        while (running) {
            before = clock.instant();
            elapsed = Duration.between(after, before);
            after = before;

            update((double)(elapsed.toMillis()) / 1000);
            render();

            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                ioController.setTitle(title + " | " + frames + " fps");
                frames = 0;
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
