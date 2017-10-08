package demo.area;

import demo.tile.Tile;
import demo.tile.Tiles;
import gamestate.GameState;

public class Area extends GameState {

    @Override
    public Tile getMapTile(int x, int y) {
        if(x < 0 || y < 0 || x >= getMapWidth() || y >= getMapHeight())
            return Tiles.voidTile;

        switch (getMapTiles()[x + y * getMapWidth()]) {
            case 0xfffca75d:
                return Tiles.dirtTile;
            case 0xff267f00:
                return Tiles.cactusTile;
            case 0xffffffff:
                return Tiles.skellytile;
            case 0xff00ffff:
                return Tiles.graveTile;
            case 0xffa5ff7f:
                return Tiles.crossTile;
            case 0xff606060:
                return Tiles.pillarTopTile;
            case 0xff808080:
                return Tiles.pillarSideTile;
            case 0xffc0c0c0:
                return Tiles.stoneFloorTile;
            case 0xffd0d0d0:
                return Tiles.stoneThresholdTile;
            case 0xff21007f:
                return Tiles.stoneDoorwayTile;
            case 0xff303030:
                return Tiles.stoneStairsDownTile;
            default:
                return Tiles.voidTile;
        }
    }
}
