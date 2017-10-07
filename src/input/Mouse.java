package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {


    public static boolean button1, button2, button3;

    public static int mouseX, mouseY;

    public static boolean dragged;

    public final int screenScale;

    public Mouse(int screenScale) {
        if(screenScale < 1)
            throw new IllegalArgumentException("Screen scale must be greater than 0");

        this.screenScale = screenScale;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        int button = mouseEvent.getButton();

        if(button == MouseEvent.BUTTON1)
            button1 = true;

        if(button == MouseEvent.BUTTON2)
            button2 = true;

        if(button == MouseEvent.BUTTON3)
            button3 = true;

        dragged = true;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        int button = mouseEvent.getButton();

        if(button == MouseEvent.BUTTON1)
            button1 = false;

        if(button == MouseEvent.BUTTON2)
            button2 = false;

        if(button == MouseEvent.BUTTON3)
            button3 = false;

        dragged = false;
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        mouseX = mouseEvent.getX() / screenScale;
        mouseY = mouseEvent.getY() / screenScale;
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

        mouseX = mouseEvent.getX() / screenScale;
        mouseY = mouseEvent.getY() / screenScale;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
    }
}
