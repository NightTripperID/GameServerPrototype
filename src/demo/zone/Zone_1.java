package demo.zone;

import demo.tile.Tile;
import demo.tile.Tiles;

public abstract class Zone_1 extends Zone {

    @Override
    public Tile getMapTileObject(int x, int y) {
        if (x < 0 || y < 0 || x >= getMapWidth() || y >= getMapHeight())
            return Tiles.voidTile;

        switch (getMapTiles()[x + y * getMapWidth()]) {
            case 0xff267f00:
                return Tiles.cactus;
            case 0xffa5ff7f:
                return Tiles.cross;
            case 0xff0094ff:
                return Tiles.dungeonDoorLocked;
            case 0xffb200ff:
                return Tiles.dungeonDoorOpen;
            case 0xffe90064:
                return Tiles.floorSwitchUp;
            case 0xffbc0051:
                return Tiles.floorSwitchDown;
            case 0xff7f92ff:
                return Tiles.grave;
            case 0xff606060:
                return Tiles.pillarTop;
            case 0xff808080:
                return Tiles.pillarSide;
            case 0xfffcdd5d:
                return Tiles.sand;
            case 0xffffaa50:
                return Tiles.sign;
            case 0xffffffff:
                return Tiles.skelly;
            case 0xff21007f:
                return Tiles.stoneDoorway;
            case 0xffc0c0c0:
                return Tiles.stoneGravelFloor;
            case 0xff303030:
                return Tiles.stoneStairsDown;
            case 0xffff8800:
                return Tiles.stoneStairsUp;
            case 0xffd0d0d0:
                return Tiles.stoneThreshold;
            case 0xffa0a0cd:
                return Tiles.stoneTileFloor;
            case 0xff7fffc5:
                return Tiles.water;
            default:
                return Tiles.voidTile;
        }
    }
}
