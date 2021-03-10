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
package com.github.nighttripperid.littleengine.model;

public class PointDouble {

    public Double x;
    public Double y;

    public PointDouble(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public PointDouble plus(PointDouble that) {
        return new PointDouble(this.x + that.x, this.y + that.y);
    }

    public PointDouble minus(PointDouble that) {
        return new PointDouble(this.x - that.x, this.y - that.y);
    }

    public PointDouble times(PointDouble that) {
        return new PointDouble(this.x * that.x, this.y * that.y);
    }

    public PointDouble div(PointDouble that) {
        return new PointDouble(this.x / that.x, this.y / that.y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(PointDouble p) {
        this.x = p.x;
        this.y = p.y;
    }

    public void set(PointDoubleW p) {
        this.x = p.x.num;
        this.y = p.y.num;
    }

    public PointDoubleW wrap() {
        return new PointDoubleW(this.x, this.y);
    }

    public Double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public Double mag2() {
        return x * x + y * y;
    }

    public PointDouble norm() {
        double r = 1 / mag();
        return new PointDouble(x * r, y * r);
    }

    public static PointDouble of(Double num) {
        return new PointDouble(num, num);
    }
}
