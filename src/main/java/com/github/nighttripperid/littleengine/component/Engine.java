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

import java.util.stream.Collectors;

/**
 * The object that represents the kernel of the game engine. Contains the central loop that updates the game logic
 * and renders the graphics. Provides basic callbacks so GameStates can request resources and information.
 */
public final class Engine {

    private Thread thread;
    private String title = "";
    private boolean running;

    private IOController ioController;
    private final ScreenBufferUpdater screenBufferUpdater;
    private final GameStateUpdater gameStateUpdater;

    /**
     * Creates a new Engine with a specified screen width, height and scale, and a title to appear in the window's title bar.
     * @param screenWidth The given width for the screen.
     * @param screenHeight The given height for the screen.
     * @param screenScale The given scale for the screen.
     * @param title The given title for the screen.
     */
    public Engine(int screenWidth, int screenHeight, int screenScale, String title) {
        this.title = title;
        ioController = new IOController(screenWidth, screenHeight, screenScale);
        screenBufferUpdater = new ScreenBufferUpdater(screenWidth, screenHeight, screenScale);
        gameStateUpdater = new GameStateUpdater();
    }

    /**
     * Launches the game thread, setting the game into motion.
     */
    private synchronized void start() {
        running = true;
        thread = new Thread(mainLoop, "LittleEngine");
        thread.start();
    }

    /**
     * Stops the game Thread, effectively halting the game.
     */
    private void stop() {
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * The runnable containing the core game loop. The update loop (containing the game logic) is timed to run at
     * approximately 60 ticks per second. The Renderable loop (containing all graphics rendering calls) runs as fast as
     * possible, executing once per loop iteration (actual speed depends on hardware).
     */
    private final Runnable mainLoop = () -> {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000D / 60D;
        double delta = 0D;
        int frames = 0;
        int updates = 0;

        ioController.requestFocus();

        while (running) {

            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                update(); // 60 times per second
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

    /**
     * Runs the core update method. Updates input devices and the active GameState.
     */
    private void update() {
        IOController.updateInput();
        gameStateUpdater.update();
    }

    /**
     * Runs the core render method. Renders all Sprites and the displays them on the Canvas.
     */
    private void render() {
        screenBufferUpdater.clearScreenBuffer();

        screenBufferUpdater.renderTileLayer(
                gameStateUpdater.getActiveGameState().getGameMap(), "Tile Layer 1");

        screenBufferUpdater.renderTileLayer(
                gameStateUpdater.getActiveGameState().getGameMap(), "Tile Layer 2");

        screenBufferUpdater.renderEntities(
                gameStateUpdater.getActiveGameState().getEntities(),
                gameStateUpdater.getActiveGameState().getGameMap());

        screenBufferUpdater.processRenderRequests(
                gameStateUpdater.getActiveGameState().getEntities().stream()
                        .flatMap(entity -> entity.getRenderRequests().stream())
                        .collect(Collectors.toList()));

        screenBufferUpdater.renderTileLayer(
                gameStateUpdater.getActiveGameState().getGameMap(), "Tile Layer 3");

        ioController.renderBufferToScreen(screenBufferUpdater.getScreenBuffer());
    }

    /**
     * Prepares the JFrame for rendering, pushes the GameState represented by the Intent, and runs the start() method,
     * setting the game into motion.
     * @param intent The intent representing the first GameState.
     */
    public void start(Intent intent) {
        gameStateUpdater.pushGameNewState(intent);
        start();
    }
}
