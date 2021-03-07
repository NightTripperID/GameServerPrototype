package com.github.nighttripperid.littleengine.model;

public class NumWrap<T extends Number> {
    public T num;

    public NumWrap() {
    }
    public NumWrap(T num) {
        this.num = num;
    }
}
