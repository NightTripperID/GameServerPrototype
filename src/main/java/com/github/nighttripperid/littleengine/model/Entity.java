package com.github.nighttripperid.littleengine.model;

public interface Entity {
    int getRenderPriority();
    int getRenderLayer();
    boolean isRemoved();
}
