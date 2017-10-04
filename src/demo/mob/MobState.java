package demo.mob;

import com.sun.istack.internal.NotNull;



import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public abstract class MobState {

    protected Mob mob;

    public MobState(Mob mob) {
        this.mob = mob;
    }

    public abstract MobState update();


    public MobState mouseClicked(@NotNull MouseEvent event) {
        return this;
    }

    public MobState mousePressed(@NotNull MouseEvent event) {
        return this;
    }

    public MobState mouseReleased(@NotNull MouseEvent event) {
        return this;
    }

    public MobState mouseWheelMoved(@NotNull MouseWheelEvent event) {
        return this;
    }

    public MobState mouseDragged(@NotNull MouseEvent event) {
        return this;
    }

    public MobState mouseMoved(@NotNull MouseEvent event) {
        return this;
    }
}
