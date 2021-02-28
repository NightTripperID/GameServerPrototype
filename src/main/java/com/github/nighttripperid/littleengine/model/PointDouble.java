package com.github.nighttripperid.littleengine.model;

public class PointDouble {
    public double x;
    public double y;

    public PointDouble(){
        this.x = 0;
        this.y = 0;
    }

    public PointDouble(double x, double y) {
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

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        PointDouble that = (PointDouble) p;
        return Double.compare(that.x, this.x) == 0 && Double.compare(that.y, this.y) == 0;
    }
}
