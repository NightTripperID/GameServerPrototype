package com.github.nighttripperid.littleengine.model;

public class PointInt {
    public int x;
    public int y;

    public PointInt(){
        x = 0;
        y = 0;
    }

    public PointInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PointInt plus(PointInt that) {
        return new PointInt(this.x + that.x, this.y + that.y);
    }

    public PointInt minus(PointInt that) {
        return new PointInt(this.x - that.x, this.y - that.y);
    }

    public PointInt times(PointInt that) {
        return new PointInt(this.x * that.x, this.y * that.y);
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) return true;
        if (p == null || getClass() != p.getClass()) return false;
        PointInt pointInt = (PointInt) p;
        return x == pointInt.x && y == pointInt.y;
    }
}
