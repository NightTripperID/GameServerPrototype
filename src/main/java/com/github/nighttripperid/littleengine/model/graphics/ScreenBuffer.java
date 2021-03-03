package com.github.nighttripperid.littleengine.model.graphics;

import com.github.nighttripperid.littleengine.model.PointDouble;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ScreenBuffer {
    private int width;
    private int height;
    private int scale;
    private int[] pixels;
    private PointDouble scroll;

    public ScreenBuffer(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.pixels = new int[width * height];
        this.scroll = new PointDouble(0D, 0D);
    }
}
