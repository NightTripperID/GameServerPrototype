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
package com.github.nighttripperid.littleengine.model.physics;

public class PointFloat {

    public Float x;
    public Float y;

    public PointFloat(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    public PointFloat plus(PointFloat that) {
        return new PointFloat(this.x + that.x, this.y + that.y);
    }

    public PointFloat minus(PointFloat that) {
        return new PointFloat(this.x - that.x, this.y - that.y);
    }

    public PointFloat times(PointFloat that) {
        return new PointFloat(this.x * that.x, this.y * that.y);
    }

    public PointFloat div(PointFloat that) {
        return new PointFloat(this.x / that.x, this.y / that.y);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(PointFloat p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void set(PointFloatW p) {
        this.x = p.x.num;
        this.y = p.y.num;
    }

    public PointFloatW wrap() {
        return new PointFloatW(this.x, this.y);
    }

    public Float mag() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Float mag2() {
        return x * x + y * y;
    }

    public PointFloat norm() {
        float r = 1 / mag();
        return new PointFloat(x * r, y * r);
    }

    public static PointFloat of(Float num) {
        return new PointFloat(num, num);
    }
}