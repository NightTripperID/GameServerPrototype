package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.PointDouble;
import lombok.extern.slf4j.Slf4j;

// https://www.youtube.com/watch?v=DPfxjQ6sqrc for reference
@Slf4j
public class VectorMath {
    private VectorMath() {
    }

    public static double hypotenuse(PointDouble p1, PointDouble p2) {
        PointDouble diff = p1.minus(p2);
        return Math.sqrt(Math.pow(diff.x, 2) + Math.pow(diff.y, 2));
    }

    public static double dotProduct(PointDouble p1, PointDouble p2) {
        return (p1.x * p2.x) + (p1.y * p2.y);
    }

    public static PointDouble unitVector(PointDouble p1, PointDouble p2) {
        PointDouble diff = p1.minus(p2);
        double denom = hypotenuse(p1, p2);

        return p1.equals(p2) ?
                new PointDouble(0, 0) :
                new PointDouble(diff.x / denom, diff.y / denom);
    }

    public static PointDouble lerp(PointDouble p1, PointDouble p2, double percent) {
        if (percent < 0 || percent > 1) {
            log.error("Error: percent must be less than zero or greater than one, but is {} ", percent);
            throw new IllegalArgumentException();
        }
        return new PointDouble((p1.x + ((p2.x - p1.x) * percent)),
                p1.y + ((p2.y - p1.y) * percent));
    }
}
