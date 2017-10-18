package demo.tile;

import java.io.Serializable;

public class TileCoord implements Serializable {

    public static final long serialVersionUID = 201710151331L;

    private int x, y;

    public final int tileSize;

    public TileCoord(int x, int y, int tileSize) {
        this.tileSize = tileSize;
        this.x = x * tileSize;
        this.y = y * tileSize;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[] getXY() {
        int[] result = new int[2];
        result[0] = x;
        result[1] = y;

        return result;
    }
}
