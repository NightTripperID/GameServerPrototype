package server;

import com.sun.istack.internal.NotNull;
import gamestate.GameState;
import gamestate.GameStateManager;
import gamestate.Intent;
import graphics.Screen;
import input.Keyboard;
import input.Mouse;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Server extends Canvas implements Runnable {

    private static final long serialVersionUID = 20170915;
    private Thread thread;

    private final String title;

    private boolean running;

    private JFrame frame;
    private BufferedImage image;
    private int[] pixels;

    private Screen screen;

    private final GameStateManager gsm;
    private final Keyboard keyboard;
    private final Mouse mouse;

    public Server(int screenWidth, int screenHeight, int screenScale, String title) {

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
        gsm.peek().update();
    }

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

    public void startServer(@NotNull Intent intent) {
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

    public void pushGameState(@NotNull Intent intent) {

        try {
            GameState gs = intent.getGsc().newInstance();
            gs.setIntent(intent);
            gs.onCreate(this);
            gsm.push(gs);
        } catch(InstantiationException | IllegalAccessException e) {
            System.out.println("Exception in " + getClass().getSimpleName() +
                    ".pushGameState(intent):" + e.getMessage());
        }
    }

    public void popGameState() {
        gsm.pop().onDestroy();
    }

    public void swapGameState(@NotNull Intent intent) {

        try {
            GameState gs = intent.getGsc().newInstance();
            gs.setIntent(intent);
            gs.onCreate(this);
            gsm.swap(gs).onDestroy();
        } catch(InstantiationException | IllegalAccessException e) {
            System.out.println("Exception in " + getClass().getSimpleName() +
                    ".swapGameState(intent): " + e.getMessage());
        }
    }

    public Keyboard getKeyboard() {
        return keyboard;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public void setCustomMouseCursor(@NotNull Image image, @NotNull Point cursorHotspot, @NotNull String name) {
        Cursor custom = Toolkit.getDefaultToolkit().createCustomCursor(image, cursorHotspot, name);
        frame.setCursor(custom);
    }

    public int getScreenWidth() {
        return screen.getWidth();
    }

    public int getScreenHeight() {
        return screen.getHeight();
    }

    public int getScreenScale() {
        return screen.getScale();
    }

    public int[] getScreenPixels() {
        return screen.getPixels();
    }
}
