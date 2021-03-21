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

public class VectorF2DW {

    public NumW<Float> x;
    public NumW<Float> y;

    public VectorF2DW(){
        x = new NumW<>(0.0f);
        y = new NumW<>(0.0f);
    }

    public VectorF2DW(Float x, Float y) {
        this();
        if (x == null) this.x = null;
        else this.x.num = x;
        if (y == null) this.y = null;
        else this.y.num = y;
    }

    public VectorF2DW plus(VectorF2DW that) {
        return new VectorF2DW(this.x.num + that.x.num, this.y.num + that.y.num);
    }

    public VectorF2DW minus(VectorF2DW that) {
        return new VectorF2DW(this.x.num - that.x.num, this.y.num - that.y.num);
    }

    public VectorF2DW times(VectorF2DW that) {
        return new VectorF2DW(this.x.num * that.x.num, this.y.num * that.y.num);
    }

    public VectorF2DW div(VectorF2DW that) {
        return new VectorF2DW(this.x.num / that.x.num, this.y.num / that.y.num);
    }

    public void set(Float x, Float y) {
        this.x.num = x;
        this.y.num = y;
    }

    public void set(VectorF2D p) {
        this.x.num = p.x;
        this.y.num = p.y;
    }

    public void set(VectorF2DW p) {
        this.x.num = p.x.num;
        this.y.num = p.y.num;
    }

    public static VectorF2DW of (Float d) {
        return new VectorF2DW(d, d);
    }


    public VectorF2D unwrap() {
        return new VectorF2D(x.num, y.num);
    }
}