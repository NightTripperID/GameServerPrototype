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

public class VectorI2DW {
    public NumW<Integer> x;
    public NumW<Integer> y;

    public VectorI2DW(){
        x = new NumW<>(0);
        y = new NumW<>(0);
    }

    public VectorI2DW(int x, int y) {
        this();
        this.x.num = x;
        this.y.num = y;
    }

    public VectorI2DW plus(VectorI2DW that) {
        return new VectorI2DW(this.x.num + that.x.num, this.y.num + that.y.num);
    }

    public VectorI2DW minus(VectorI2DW that) {
        return new VectorI2DW(this.x.num - that.x.num, this.y.num - that.y.num);
    }

    public VectorI2DW times(VectorI2DW that) {
        return new VectorI2DW(this.x.num * that.x.num, this.y.num * that.y.num);
    }

    public VectorI2DW div(VectorI2DW that) {
        return new VectorI2DW(that.x.num == 0 ? null : this.x.num / that.x.num, that.y.num == 0 ? null : this.y.num / that.y.num);
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        VectorI2DW that = (VectorI2DW) p;
        return that.x.num.equals(this.x.num) && that.y.num.equals(this.y.num);
    }
}
