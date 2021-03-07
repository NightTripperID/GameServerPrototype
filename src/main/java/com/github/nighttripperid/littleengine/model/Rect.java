package com.github.nighttripperid.littleengine.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Rect {
    public PointDouble pos;
    public PointDouble size;
    public PointDouble vel;

    public Rect() {
        pos = new PointDouble();
        size = new PointDouble();
        vel = new PointDouble();
    }
}