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

import com.github.nighttripperid.littleengine.model.NumWrap;
import com.github.nighttripperid.littleengine.model.PointDouble;
import com.github.nighttripperid.littleengine.model.Rect;
import lombok.extern.slf4j.Slf4j;

// https://www.youtube.com/watch?v=DPfxjQ6sqrc for reference
@Slf4j
public class VectorMath {
    private VectorMath() {
    }

    public static double hypotenuse(PointDouble p1, PointDouble p2) {
        PointDouble diff = p1.minus(p2);
        return Math.sqrt(Math.pow(diff.x.num, 2) + Math.pow(diff.y.num, 2));
    }

    public static double dotProduct(PointDouble p1, PointDouble p2) {
        return (p1.x.num * p2.x.num) + (p1.y.num * p2.y.num);
    }

    public static PointDouble unitVector(PointDouble p1, PointDouble p2) {
        PointDouble diff = p1.minus(p2);
        double denom = hypotenuse(p1, p2);

        return p1.equals(p2) ?
                new PointDouble(0D, 0D) :
                new PointDouble(diff.x.num / denom, diff.y.num / denom);
    }

    public static PointDouble lerp(PointDouble p1, PointDouble p2, double percent) { // linear interpolation
        if (percent < 0 || percent > 1) {
            log.error("Error: percent must be less than zero or greater than one, but is {} ", percent);
            throw new IllegalArgumentException();
        }
        return new PointDouble((p1.x.num + ((p2.x.num - p1.x.num) * percent)),
                p1.y.num + ((p2.y.num - p1.y.num) * percent));
    }

    public static <T extends Number> void swap(NumWrap<T> n1, NumWrap<T> n2) {
        NumWrap<T> temp = new NumWrap<>();
        temp.num = n1.num;
        n1.num = n2.num;
        n2.num = temp.num;
    }

    public static boolean dynamicRectVsRect(Rect in, Rect target, PointDouble contactPoint, PointDouble contactNormal,
                                            NumWrap<Double> time) {
        if (in.vel.x.num == 0 && in.vel.y.num == 0)
            return false;

        Rect expandedTarget = new Rect();
        expandedTarget.pos = target.pos.minus(in.size.div(new PointDouble(2D, 2D)));
        expandedTarget.size = target.size.plus(in.size);

        if(rayVsRect(in.pos.plus(in.size.div(new PointDouble(2D, 2d))),
                     in.vel, expandedTarget, contactPoint, contactNormal, time)) {
            return time.num <= 1;
        }
        return false;
    }

    public static boolean rayVsRect(PointDouble rayOrigin, PointDouble rayDir, Rect target,
                                    PointDouble contactPoint, PointDouble contactNormal, NumWrap<Double> t_hitNear) {

        PointDouble t_near = target.pos.minus(rayOrigin).div(rayDir);
        PointDouble t_far = target.pos.plus(target.size).minus(rayOrigin).div(rayDir);

        swap(t_near.x, t_far.x);
        swap(t_near.y, t_far.y);

        if (t_near.x.num > t_far.y.num || t_near.y.num > t_far.x.num) return false;

        t_hitNear.num = Math.max(t_near.x.num, t_near.y.num);
        double t_hitFar = Math.min(t_far.x.num, t_far.y.num);

        if (t_hitFar < 0) return false;

        contactPoint = rayOrigin.plus(new PointDouble(rayDir.x.num * t_hitNear.num, rayDir.y.num * t_hitNear.num));

        if (t_near.x.num > t_near.y.num)
            if (rayDir.x.num < 0)
                contactNormal = new PointDouble(1D, 0D);
            else
                contactNormal = new PointDouble(-1D, 0D);
        else if (t_near.x.num < t_near.y.num)
            if(rayDir.y.num < 0)
                contactNormal = new PointDouble(0D, 1D);
            else
                contactNormal = new PointDouble(0D, -1D);

        return true;
    }
}
