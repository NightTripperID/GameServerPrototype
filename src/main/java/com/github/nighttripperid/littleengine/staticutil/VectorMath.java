/*
 * Copyright (c) 2021, BitBurger, Evan Doering
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *     Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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

    public static PointDouble lerp(PointDouble p1, PointDouble p2, double percent) { // linear interpolation
        if (percent < 0 || percent > 1) {
            log.error("Error: percent must be less than zero or greater than one, but is {} ", percent);
            throw new IllegalArgumentException();
        }
        return new PointDouble((p1.x + ((p2.x - p1.x) * percent)),
                p1.y + ((p2.y - p1.y) * percent));
    }
}
