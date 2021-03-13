package com.github.nighttripperid.littleengine.model.physics;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Rect {
    public PointDouble pos;
    public PointDouble size;
    public PointDouble vel;

    public Rect[] contact = new Rect[4];

    public Rect() {
        pos = PointDouble.of(0.0d);
        size = PointDouble.of(0.0d);
        vel = PointDouble.of(0.0d);
    }

    public Rect(PointDouble pos, PointDouble size) {
        this.pos = pos;
        this.size = size;
        this.vel = PointDouble.of(0.0d);
    }
}