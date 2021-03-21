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

import com.github.nighttripperid.littleengine.model.physics.NumW;
import com.github.nighttripperid.littleengine.model.physics.VectorF2D;
import com.github.nighttripperid.littleengine.model.physics.VectorF2DW;
import com.github.nighttripperid.littleengine.model.physics.Rect;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VectorMath {
    private VectorMath() {
    }

    boolean pointVsRect(VectorF2D p, Rect r) {
        return (p.x >= r.pos.x && p.y >= r.pos.y && p.x < r.pos.x + r.size.x && p.y < r.pos.y + r.size.y);
    }

    public static boolean rayVsRect(VectorF2D rayOrigin, VectorF2D rayDirection, Rect target,
                                    VectorF2D contactPoint, VectorF2D contactNormal, NumW<Float> tHitNear) {

        contactPoint.set(0.0f, 0.0f);
        contactNormal.set(0.0f, 0.0f);

        // Cache division
        VectorF2D invDir = new VectorF2D(1.0f, 1.0f).div(rayDirection); // inverse direction

        // Calculate intersections with rectangle bounding axes
        VectorF2DW tNear = target.pos.minus(rayOrigin).times(invDir).wrap();
        VectorF2DW tFar = target.pos.plus(target.size).minus(rayOrigin).times(invDir).wrap();

        if (Float.isNaN(tFar.y.num) || Float.isNaN(tFar.x.num)) return false;
        if (Float.isNaN(tNear.y.num) || Float.isNaN(tNear.x.num)) return false;

        // Sort distances
        if (tNear.x.num > tFar.x.num) NumW.swap(tNear.x, tFar.x);
        if (tNear.y.num > tFar.y.num) NumW.swap(tNear.y, tFar.y);

        // Early rejection
        if (tNear.x.num > tFar.y.num || tNear.y.num > tFar.x.num) return false;

        // Closest 'time' will be the first contact
        tHitNear.num = Math.max(tNear.x.num, tNear.y.num);

        // Furthest 'time' is contact on opposite side of target
        float tHitFar = Math.min(tFar.x.num, tFar.y.num);

        // Reject if ray direction is pointing away from object
        if (tHitFar < 0) return false;

        // Contact point of collision from parametric line equation
        // contact_point = ray_origin + t_hit_near * ray_dir
        contactPoint.set(rayOrigin.plus(VectorF2D.of(tHitNear.num).times(rayDirection)));

        if (tNear.x.num > tNear.y.num)
            if (invDir.x < 0)
                contactNormal.set(1.0f, 0.0f);
            else
                contactNormal.set(-1.0f, 0.0f);
        else if (tNear.x.num < tNear.y.num)
            if (invDir.y < 0)
                contactNormal.set(0.0f, 1.0f);
            else
                contactNormal.set(0.0f, -1.0f);

        // Note if t_near == t_far, collision is principally in a diagonal
        // so pointless to resolve. By returning a CN={0,0} even though it's
        // considered a hit, the resolver won't change anything.
        return true;

    }

    public static boolean RectVsRect(Rect r1, Rect r2) {
        return (r1.pos.x < r2.pos.x + r2.size.x &&
                r1.pos.x + r1.size.x > r2.pos.x &&
                r1.pos.y < r2.pos.y + r2.size.y &&
                r1.pos.y + r1.size.y > r2.pos.y);
    }

    public static boolean dynamicRectVsRect(Rect dynamicRect, float timeStep, Rect staticRect, VectorF2D contactPoint,
                                            VectorF2D contactNormal, NumW<Float> contactTime) {

        // Check if dynamic rectangle is actually moving - we assume rectangles are NOT in collision to start
        if (dynamicRect.vel.x == 0 && dynamicRect.vel.y == 0)
            return false;

        // Expand target rectangle by source dimensions
        Rect expandedTarget = new Rect();
        // expandedTarget.pos = staticRect.pos - dynamicRect.size / 2
        expandedTarget.pos.set(staticRect.pos.minus(dynamicRect.size.div(VectorF2D.of(2.0f))));
        // expandedTarget.size = staticRect.size + dynamicRect.size;
        expandedTarget.size.set(staticRect.size.plus(dynamicRect.size));

        // if (RayVsRect(r_dynamic->pos + r_dynamic->size / 2, r_dynamic->vel * fTimeStep, &expanded_target, contact_point, contact_normal, contact_time))
        //				return (contact_time >= 0.0f && contact_time < 1.0f);
        if (rayVsRect(dynamicRect.pos.plus(dynamicRect.size.div(VectorF2D.of(2.0f))),
                dynamicRect.vel.times(VectorF2D.of(timeStep)),
                expandedTarget, contactPoint,
                contactNormal, contactTime)) {
            return(contactTime.num >= 0.0f && contactTime.num < 1.0f);
        } else {
            return false;
        }
    }

    public static boolean resolveDynamicRectVsRect(Rect dynamicRect, float timeStep, Rect staticRect) {
        VectorF2D contactPoint = VectorF2D.of(0.0f);
        VectorF2D contactNormal = VectorF2D.of(0.0f);
        NumW<Float> contactTime = new NumW<>(0.0f);
        if(dynamicRectVsRect(dynamicRect, timeStep, staticRect, contactPoint, contactNormal, contactTime)) {
            dynamicRect.contact[0] = (contactNormal.y > 0) ? staticRect : null;
            dynamicRect.contact[1] = (contactNormal.x < 0) ? staticRect : null;
            dynamicRect.contact[2] = (contactNormal.y < 0) ? staticRect : null;
            dynamicRect.contact[3] = (contactNormal.x > 0) ? staticRect : null;

            dynamicRect.vel.set(
                    dynamicRect.vel.plus(
                            contactNormal
                                    .times(new VectorF2D(Math.abs(dynamicRect.vel.x), Math.abs(dynamicRect.vel.y)))
                                    .times(VectorF2D.of(1.0f).minus(VectorF2D.of(contactTime.num)))
                    ));
            return true;
        }
        return false;
    }
}
