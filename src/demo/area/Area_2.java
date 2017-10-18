package demo.area;

import demo.tile.Tile;
import demo.tile.Tiles;

public abstract class Area_2 extends Area {

    @Override
    public Tile getMapTileObject(int x, int y) {
        if (x < 0 || y < 0 || x >= getMapWidth() || y >= getMapHeight())
            return Tiles.voidTile;

        switch (getMapTiles()[x + y * getMapWidth()]) {
            case 0xff7f6a00:
                return Tiles.dirt;
            case 0xffd3b000:
                return Tiles.dirtThreshold;
            case 0xff0094ff:
                return Tiles.dungeonDoorLocked;
            case 0xffb200ff:
                return Tiles.dungeonDoorOpen;
            case 0xff404040:
                return Tiles.dungeonGate;
            case 0xff823400:
                return Tiles.earthTop;
            case 0xffc14d00:
                return Tiles.earthSide;
            case 0xffe90064:
                return Tiles.floorSwitchUp;
            case 0xffbc0051:
                return Tiles.floorSwitchDown;
            case 0xff7f00d5:
                return Tiles.lava;
            case 0xff808080:
                return Tiles.lavaGrate;
            case 0xffffb27f:
                return Tiles.obeliskBottom;
            case 0xffff7f7f:
                return Tiles.obeliskTop;
            case 0xff303030:
                return Tiles.stoneStairsDown;
            case 0xffff8800:
                return Tiles.stoneStairsUp;
            case 0xff7fffc5:
                return Tiles.water;
            case 0xffa5ff7f:
                return Tiles.wideDoorCenter;
            case 0xffdaff7f:
                return Tiles.wideDoorLeft;
            case 0xffffff8e:
                return Tiles.wideDoorRight;
            default:
                return Tiles.voidTile;
        }
    }
}
