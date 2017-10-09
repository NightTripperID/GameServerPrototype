package entity;

import com.sun.istack.internal.NotNull;

public interface Updatable {
    void update();

    boolean removed();

    default void runCollision(@NotNull Updatable updatable) {
    }

    default boolean collidesWith(@NotNull Updatable updatable) {
        return false;
    }
}
