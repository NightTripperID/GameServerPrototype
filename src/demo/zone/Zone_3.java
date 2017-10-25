package demo.zone;

import demo.tile.Tile;
import demo.tile.Tiles;

public abstract class Zone_3 extends Zone {

    @Override
    public Tile getMapTileObject(int x, int y) {
        if (x < 0 || y < 0 || x >= getMapWidth() || y >= getMapHeight())
            return Tiles.voidTile;

        switch (getMapTiles()[x + y * getMapWidth()]) {
            case 0xff0094ff:
                return Tiles.dungeonDoorLocked;
            case 0xffb200ff:
                return Tiles.dungeonDoorOpen;
            case 0xff404040:
                return Tiles.dungeonGate;
            case 0xffe90064:
                return Tiles.floorSwitchUp;
            case 0xffbc0051:
                return Tiles.floorSwitchDown;
            case 0xff57007f:
                return Tiles.purpleLava;
            case 0xfffc4206:
                return Tiles.scorchedSand;
            case 0xffffaa50:
                return Tiles.sign;
            case 0xff303030:
                return Tiles.stoneStairsDown;
            case 0xffff8800:
                return Tiles.stoneStairsUp;
            case 0xff838383:
                if(x % 2 == 0)
                    return Tiles.stoneSideL;
                else
                    return Tiles.stoneSideR;
            case 0xffc2c2c2:
                if(x % 2 == 0)
                    return Tiles.stoneTopL;
                else
                    return Tiles.stoneTopR;
            default:
                return Tiles.voidTile;
        }
    }
}
