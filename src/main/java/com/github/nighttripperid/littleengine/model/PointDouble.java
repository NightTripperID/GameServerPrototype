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

    public NumWrap<Double> x;
    public NumWrap<Double> y;

    public PointDouble(){
        x = new NumWrap<>(0D);
        y = new NumWrap<>(0D);
    }

    public PointDouble(Double x, Double y) {
        this();
        if (x == null) this.x = null;
        else this.x.num = x;
        if (y == null) this.y = null;
        else this.y.num = y;
    }

    public PointDouble plus(PointDouble that) {
        return new PointDouble(this.x.num + that.x.num, this.y.num + that.y.num);
    }

    public PointDouble minus(PointDouble that) {
        return new PointDouble(this.x.num - that.x.num, this.y.num - that.y.num);
    }

    public PointDouble times(PointDouble that) {
        return new PointDouble(this.x.num * that.x.num, this.y.num * that.y.num);
    }

    public PointDouble div(PointDouble that) {

        return new PointDouble(that.x.num == 0 ? null : this.x.num / that.x.num, that.y.num == 0 ? null : this.y.num / that.y.num);
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        PointDouble that = (PointDouble) p;
        return Double.compare(that.x.num, this.x.num) == 0 && Double.compare(that.y.num, this.y.num) == 0;
    }
}
