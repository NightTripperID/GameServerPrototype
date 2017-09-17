package server;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.Intent;
import graphics.Screen;
import input.Keyboard;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.lang.reflect.InvocationTargetException;

public class Server extends Canvas implements Runnable {

    private static final long serialVersionUID = 20170915;
    private Thread thread;

    private final String title;

    private boolean running;

    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;

    private Screen screen;

    private final GameStateManager gsm = new GameStateManager();
    private final Keyboard keyboard = new Keyboard();

    private final int windowWidth;
    private final int windowHeight;
    private final int windowScale;

    public Server(int windowWidth, int windowHeight, int windowScale, String title) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.windowScale = windowScale;

        this.title = title;

        image = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

        Dimension size = new Dimension(windowWidth * windowScale, windowHeight * windowScale);
        setPreferredSize(size);

        screen = new Screen(windowWidth, windowHeight);
        frame = new JFrame();

        addKeyListener(keyboard);
    }

    private synchronized void start() {
        running = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    private void stop() {
        running = false;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

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
    }

    private void update() {
        keyboard.update();
        gsm.peek().onUpdate();
    }

    private void render() {

        BufferStrategy bs = getBufferStrategy();

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        screen.clear();

        gsm.peek().onRender(screen);

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.getPixel(i);
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.MAGENTA);
        g.fillRect(0, 0, windowWidth, windowHeight);
        g.drawImage(image, 0, 0, windowWidth * windowScale, windowHeight * windowScale, null);
        g.dispose();
        bs.show();
    }

    public void startServer(@NotNull Intent intent) {
        frame.setResizable(false);
        frame.setTitle(title);
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            GameState gs = intent.getGsc().getConstructor(Server.class).newInstance(this);
            gs.setIntent(intent);
            gs.onCreate();
            gsm.push(gs);
        } catch (NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            System.out.println("Exception in " + getClass().getSimpleName() + ".startServer(intent): " + e.getMessage());
        }

        start();
    }

    public void swapGameState(@NotNull Intent intent) {

        try {
            GameState gs = intent.getGsc().getConstructor(Server.class).newInstance(this);
            gs.setIntent(intent);
            gs.onCreate();
            gsm.swap(gs).onDestroy();
        } catch(NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            System.out.println("Exception in " + getClass().getSimpleName() + ".finishGameState(intent): " + e.getMessage());
        }
    }

    public void finishGameState() {
        gsm.pop().onDestroy();
    }

    public void startNewGameState(@NotNull Intent intent) {

        try {
            GameState gs = intent.getGsc().getConstructor(Server.class).newInstance(this);
            gs.setIntent(intent);
            gs.onCreate();
            gsm.push(gs);
        } catch(NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            System.out.println("Exception in " + getClass().getSimpleName() + ".startnewGameState(intent)" + e.getMessage());
        }
    }

    public int getScreenWidth() {
        return screen.getWidth();
    }

    public int getScreenHeight() {
        return screen.getHeight();
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }
}
