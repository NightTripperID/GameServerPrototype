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

package com.github.nighttripperid.littleengine.deprecated.engine;

import com.github.nighttripperid.littleengine.deprecated.gamestate.GameState;
import com.github.nighttripperid.littleengine.deprecated.gamestate.Intent;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * The object that represents the kernel of the game engine. Contains the central loop that updates the game logic
 * and renders the graphics. Provides basic callbacks so GameStates can request resources and information.
 */
public final class Engine extends Canvas {

    private Thread thread;

    private String title = "";

    private boolean running;

    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;

    private Screen screen;

    private final GameStateManager gsm;
    private final Keyboard keyboard;
    private final Mouse mouse;

    /**
     * Creates a new Engine with a specified screen width, height and scale, and a title to appear in the window's title bar.
     * @param screenWidth The given width for the screen.
     * @param screenHeight The given height for the screen.
     * @param screenScale The given scale for the screen.
     * @param title The given title for the screen.
     */
    public Engine(int screenWidth, int screenHeight, int screenScale, String title) {

        this.title = title;

        image = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        Dimension size = new Dimension(screenWidth * screenScale, screenHeight * screenScale);
        setPreferredSize(size);

        screen = new Screen(screenWidth, screenHeight, screenScale);
        frame = new JFrame();

        gsm = new GameStateManager();

        keyboard = new Keyboard();
        addKeyListener(keyboard);

        mouse = new Mouse(screenScale);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
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
    private Runnable mainLoop = () -> {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000D / 60D;
        double delta = 0D;
        int frames = 0;
        int updates = 0;

        requestFocus();

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
                frame.setTitle(title + " | " + updates + " ups " + " | " + frames + " fps");
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
        keyboard.update();
        mouse.update();
        gsm.peek().update();
    }

    /**
     * Runs the core render method. Renders all Sprites and the displays them on the Canvas.
     */
    private void render() {

        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        gsm.peek().render(screen);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.getPixel(i);
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
        g.drawImage(image, 0, 0, screen.getWidth() * screen.getScale(), screen.getHeight() * screen.getScale(), null);
        g.dispose();
        bs.show();
    }

    /**
     * Prepares the JFrame for rendering, pushes the GameState represented by the Intent, and runs the start() method,
     * setting the game into motion.
     * @param intent The intent representing the first GameState.
     */
    public void start(Intent intent) {
        frame.setResizable(false);
        frame.setTitle(title);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        pushGameState(intent);

        start();
    }

    /**
     * Instantiates the GameState represented by the Intent and pushes it onto the top of the GameStateManager's
     * gameStateStack and runs its onCreate() method.
     * @param intent The intent representing the GameState to be instantiated and pushed onto the stack.
     */
    public void pushGameState(Intent intent) {

        try {
            GameState gs = intent.getGameStateClass().newInstance();
            gs.setIntent(intent);
            gs.onCreate(this);
            gsm.push(gs);
        } catch(InstantiationException | IllegalAccessException e) {
            System.out.println("Exception in " + getClass().getSimpleName() +
                    ".pushGameState(intent):" + e.getMessage());
        }
    }

    /**
     * Pops the active GameState from the top of the GameStateManager's gameStateStack and runs its onDestroy() method.
     */
    public void popGameState() {
        gsm.pop().onDestroy();
    }

    /**
     * Pops the active GameState, runs its onDestroy() method, then pushes the newly instantiated GameState and runs its
     * onCreate() method.
     * @param intent The intent representing the GameState to be instantiated and pushed onto the stack.
     */
    public void swapGameState(Intent intent) {

        try {
            GameState gs = intent.getGameStateClass().newInstance();
            gs.setIntent(intent);
            gs.onCreate(this);
            gsm.swap(gs).onDestroy();
        } catch(InstantiationException | IllegalAccessException e) {
            System.out.println("Exception in " + getClass().getSimpleName() +
                    ".swapGameState(intent): " + e.getMessage());
        }
    }

    /**
     * Returns the screen's width.
     * @return the screen's width.
     */
    public int getScreenWidth() {
        return screen.getWidth();
    }

    /**
     * Returns the screen's height.
     * @return the screen's height.
     */
    public int getScreenHeight() {
        return screen.getHeight();
    }

    /**
     * Returns the screen's scale.
     * @return the screen's scale.
     */
    public int getScreenScale() {
        return screen.getScale();
    }

    /**
     * Returns the game's pixel array (the screen buffer)
     * @return the screen's pixel array
     */
    public int[] getScreenPixels() {
        return screen.getPixels();
    }
}
