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

import com.github.nighttripperid.littleengine.model.physics.NumWrap;
import com.github.nighttripperid.littleengine.model.physics.PointDouble;
import com.github.nighttripperid.littleengine.model.physics.PointDoubleW;
import com.github.nighttripperid.littleengine.model.physics.Rect;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VectorMath {
    private VectorMath() {
    }

    public static boolean rayVsRect(PointDouble rayOrigin, PointDouble rayDirection, Rect target,
                                    PointDouble contactPoint, PointDouble contactNormal, NumWrap<Double> tHitNear) {

        contactPoint.set(0.0d, 0.0d);
        contactNormal.set(0.0d, 0.0d);

        // Cache division
        PointDouble invDir = new PointDouble(1.0d, 1.0d).div(rayDirection); // inverse direction

        // Calculate intersections with rectangle bounding axes
        PointDoubleW tNear = target.pos.minus(rayOrigin).times(invDir).wrap();
        PointDoubleW tFar = target.pos.plus(target.size).minus(rayOrigin).times(invDir).wrap();

        if (Double.isNaN(tFar.y.num) || Double.isNaN(tFar.x.num)) return false;
        if (Double.isNaN(tNear.y.num) || Double.isNaN(tNear.x.num)) return false;

        // Sort distances
        if (tNear.x.num > tFar.x.num) NumWrap.swap(tNear.x, tFar.x);
        if (tNear.y.num > tFar.y.num) NumWrap.swap(tNear.y, tFar.y);

        // Early rejection
        if (tNear.x.num > tFar.y.num || tNear.y.num > tFar.x.num) return false;

        // Closest 'time' will be the first contact
        tHitNear.num = Math.max(tNear.x.num, tNear.y.num);

        // Furthest 'time' is contact on opposite side of target
        double tHitFar = Math.min(tFar.x.num, tFar.y.num);

        // Reject if ray direction is pointing away from object
        if (tHitFar < 0) return false;

        // Contact point of collision from parametric line equation
        // contact_point = ray_origin + t_hit_near * ray_dir
        contactPoint.set(rayOrigin.plus(PointDouble.of(tHitNear.num).times(rayDirection)));

        if (tNear.x.num > tNear.y.num)
            if (invDir.x < 0)
                contactNormal.set(1.0d, 0.0d);
            else
                contactNormal.set(-1.0d, 0.0d);
        else if (tNear.x.num < tNear.y.num)
            if (invDir.y < 0)
                contactNormal.set(0.0d, 1.0d);
            else
                contactNormal.set(0.0d, -1.0d);

        // Note if t_near == t_far, collision is principally in a diagonal
        // so pointless to resolve. By returning a CN={0,0} even though it's
        // considered a hit, the resolver won't change anything.
        return true;

    }

    public static boolean dynamicRectVsRect(Rect dynamicRect, double timeStep, Rect staticRect, PointDouble contactPoint,
                                            PointDouble contactNormal, NumWrap<Double> contactTime) {

        // Check if dynamic rectangle is actually moving - we assume rectangles are NOT in collision to start
        if (dynamicRect.vel.x == 0 && dynamicRect.vel.y == 0)
            return false;

        // Expand target rectangle by source dimensions
        Rect expandedTarget = new Rect();
        // expandedTarget.pos = staticRect.pos - dynamicRect.size / 2
        expandedTarget.pos.set(staticRect.pos.minus(dynamicRect.size.div(PointDouble.of(2.0d))));
        // expandedTarget.size = staticRect.size + dynamicRect.size;
        expandedTarget.size.set(staticRect.size.plus(dynamicRect.size));

        // if (RayVsRect(r_dynamic->pos + r_dynamic->size / 2, r_dynamic->vel * fTimeStep, &expanded_target, contact_point, contact_normal, contact_time))
        //				return (contact_time >= 0.0f && contact_time < 1.0f);
        if (rayVsRect(dynamicRect.pos.plus(dynamicRect.size.div(PointDouble.of(2.0d))),
                dynamicRect.vel.times(PointDouble.of(timeStep)),
                expandedTarget, contactPoint,
                contactNormal, contactTime)) {
            return(contactTime.num >= 0.0d && contactTime.num < 1.0d);
        } else {
            return false;
        }
    }

    public static boolean resolveDynamicRectVsRect(Rect dynamicRect, double timeStep, Rect staticRect) {
        PointDouble contactPoint = PointDouble.of(0.0d);
        PointDouble contactNormal = PointDouble.of(0.0d);
        NumWrap<Double> contactTime = new NumWrap<>(0.0d);
        if(dynamicRectVsRect(dynamicRect, timeStep, staticRect, contactPoint, contactNormal, contactTime)) {
            dynamicRect.contact[0] = (contactNormal.y > 0) ? staticRect : null;
            dynamicRect.contact[1] = (contactNormal.x < 0) ? staticRect : null;
            dynamicRect.contact[2] = (contactNormal.y < 0) ? staticRect : null;
            dynamicRect.contact[3] = (contactNormal.y < 0) ? staticRect : null;

            dynamicRect.vel.set(
                    dynamicRect.vel.plus(
                            contactNormal
                                    .times(new PointDouble(Math.abs(dynamicRect.vel.x), Math.abs(dynamicRect.vel.y)))
                                    .times(PointDouble.of(1.0d).minus(PointDouble.of(contactTime.num)))
                    ));
            return true;
        }
        return false;
    }
}
