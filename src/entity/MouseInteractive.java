package entity;

import com.sun.istack.internal.NotNull;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public interface MouseInteractive {

    default void mouseClicked(@NotNull MouseEvent event) {
    }

    default void mousePressed(@NotNull MouseEvent event) {
    }

    default void mouseReleased(@NotNull MouseEvent event) {
    }

    default void mouseEntered(@NotNull MouseEvent event) {
    }

    default void mouseExited(@NotNull MouseEvent event) {
    }

    default void mouseWheelMoved(@NotNull MouseWheelEvent event) {
    }

    default void mouseDragged(@NotNull MouseEvent event) {
    }

    default void mouseMoved(@NotNull MouseEvent event) {
    }
}
