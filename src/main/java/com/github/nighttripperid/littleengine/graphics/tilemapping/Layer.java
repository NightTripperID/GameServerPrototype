package com.github.nighttripperid.littleengine.graphics.tilemapping;

import lombok.Data;

@Data
public class Layer {
    private String name;
    private int[] data;
    private int x;
    private int y;
    private int width;
    private int height;
    private int opacity;
    private boolean visible;
}
