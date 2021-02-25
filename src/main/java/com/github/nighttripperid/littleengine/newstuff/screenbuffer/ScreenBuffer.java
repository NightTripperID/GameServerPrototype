package com.github.nighttripperid.littleengine.newstuff.screenbuffer;

import lombok.Data;

@Data
public class ScreenBuffer {
    private int width;
    private int height;
    private int scale;
    private int[] pixels;
    private double xScroll;
    private double yScroll;
}
