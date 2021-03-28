package com.github.nighttripperid.littleengine.model.physics;

public class CollisionBody extends Rect {
    public VectorF2D vel;
    public Rect[] contacts = new Rect[4];

    public CollisionBody() {
        super();
        vel = VectorF2D.of(0.0f);
    }

    public CollisionBody(Rect area) {
        super(area.pos, area.size);
        this.vel = VectorF2D.of(0.0f);
    }

    public CollisionBody(VectorF2D pos, VectorF2D size) {
        super(pos, size);
        this.vel = VectorF2D.of(0.0f);
    }
}
