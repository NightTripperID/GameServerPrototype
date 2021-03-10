package com.github.nighttripperid.littleengine.model;

public class NumWrap<T extends Number> {
    public T num;

    public NumWrap() {
    }
    public NumWrap(T num) {
        this.num = num;
    }

    public static <T extends Number> void swap(NumWrap<T> n1, NumWrap<T> n2) {
        NumWrap<T> temp = new NumWrap<>();
        temp.num = n1.num;
        n1.num = n2.num;
        n2.num = temp.num;
    }
}
