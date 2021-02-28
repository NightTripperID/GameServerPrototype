package com.github.nighttripperid.littleengine.model.graphics;

import com.github.nighttripperid.littleengine.model.PointDouble;
import lombok.Data;

@Data
public class ScreenBuffer {
    private int width;
    private int height;
    private int scale;
    private int[] pixels;
    private PointDouble scroll;
}
