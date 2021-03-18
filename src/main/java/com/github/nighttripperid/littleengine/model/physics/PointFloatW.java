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

public class PointFloatW {

    public NumWrap<Float> x;
    public NumWrap<Float> y;

    public PointFloatW(){
        x = new NumWrap<>(0.0f);
        y = new NumWrap<>(0.0f);
    }

    public PointFloatW(Float x, Float y) {
        this();
        if (x == null) this.x = null;
        else this.x.num = x;
        if (y == null) this.y = null;
        else this.y.num = y;
    }

    public PointFloatW plus(PointFloatW that) {
        return new PointFloatW(this.x.num + that.x.num, this.y.num + that.y.num);
    }

    public PointFloatW minus(PointFloatW that) {
        return new PointFloatW(this.x.num - that.x.num, this.y.num - that.y.num);
    }

    public PointFloatW times(PointFloatW that) {
        return new PointFloatW(this.x.num * that.x.num, this.y.num * that.y.num);
    }

    public PointFloatW div(PointFloatW that) {
        return new PointFloatW(this.x.num / that.x.num, this.y.num / that.y.num);
    }

    public void set(Float x, Float y) {
        this.x.num = x;
        this.y.num = y;
    }

    public void set(PointFloat p) {
        this.x.num = p.x;
        this.y.num = p.y;
    }

    public void set(PointFloatW p) {
        this.x.num = p.x.num;
        this.y.num = p.y.num;
    }

    public static PointFloatW of (Float d) {
        return new PointFloatW(d, d);
    }


    public PointFloat unwrap() {
        return new PointFloat(x.num, y.num);
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        PointFloatW that = (PointFloatW) p;
        return Float.compare(that.x.num, this.x.num) == 0 && Float.compare(that.y.num, this.y.num) == 0;
    }
}