package input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class Mouse extends MouseAdapter {

    private MouseCallbacks mouseCallbacks;

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        mouseCallbacks.mouseClicked(mouseEvent);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        mouseCallbacks.mousePressed(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        mouseCallbacks.mouseReleased(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseCallbacks.mouseMoved(mouseEvent);
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseCallbacks.mouseDragged(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        mouseCallbacks.mouseEntered(mouseEvent);
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        mouseCallbacks.mouseExited(mouseEvent);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        mouseCallbacks.mouseWheelMoved(mouseWheelEvent);
    }

    public void setMouseCallbacks(MouseCallbacks mouseCallbacks) {
        this.mouseCallbacks = mouseCallbacks;
    }

    public interface MouseCallbacks {
        default void mouseClicked(MouseEvent mouseEvent) {}
        default void mousePressed(MouseEvent mouseEvent) {}
        default void mouseReleased(MouseEvent mouseEvent) {}
        default void mouseMoved(MouseEvent mouseEvent) {}
        default void mouseDragged(MouseEvent mouseEvent) {}
        default void mouseEntered(MouseEvent mouseEvent) {}
        default void mouseExited(MouseEvent mouseEvent) {}
        default void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {}
    }
}
