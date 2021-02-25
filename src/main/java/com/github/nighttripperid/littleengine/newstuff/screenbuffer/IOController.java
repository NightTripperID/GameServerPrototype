package com.github.nighttripperid.littleengine.newstuff.screenbuffer;

import com.github.nighttripperid.littleengine.engine.Keyboard;
import com.github.nighttripperid.littleengine.engine.Mouse;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class IOController extends Canvas {

    @Setter
    private String title;

    private final JFrame jFrame;
    private final BufferedImage bufferedImage;
    private int[] pixels;

    public IOController(int width, int height, int scale) {
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();

        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);

        jFrame = new JFrame();
        jFrame.setResizable(false);
        jFrame.setTitle(title);
        jFrame.add(this);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        addKeyListener(new Keyboard());

        Mouse mouse = new Mouse(scale);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        addMouseWheelListener(mouse);
    }

    public static void updateInput() {
        Keyboard.update();
        Mouse.update();
    }

    public void renderBufferToScreen(ScreenBuffer screenBuffer) {
        BufferStrategy bufferStrategy = getBufferStrategy();

        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        for(int i = 0; i < pixels.length; i++) {
            pixels[i] = screenBuffer.getPixels()[i];
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.MAGENTA);
        graphics.fillRect(0, 0, screenBuffer.getWidth(), screenBuffer.getHeight());
        graphics.drawImage(bufferedImage, 0, 0,
                screenBuffer.getWidth() * screenBuffer.getScale(),
                screenBuffer.getHeight() * screenBuffer.getScale(), null);
        graphics.dispose();
        bufferStrategy.show();
    }
}
