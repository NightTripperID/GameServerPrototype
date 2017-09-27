package input;

import java.awt.event.*;

public class Mouse extends MouseAdapter {

    private MouseCallback mouseCallback;

    public interface MouseCallback extends MouseListener, MouseWheelListener, MouseMotionListener { }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        mouseCallback.mouseClicked(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseCallback.mousePressed(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseCallback.mouseReleased(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseCallback.mouseMoved(mouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseCallback.mouseDragged(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        mouseCallback.mouseEntered(mouseEvent);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        mouseCallback.mouseExited(mouseEvent);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        mouseCallback.mouseWheelMoved(mouseWheelEvent);
    }

    public void setMouseCallback(MouseCallback mouseCallback) {
        this.mouseCallback = mouseCallback;
    }
}
