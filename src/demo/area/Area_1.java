package demo.area;

import com.sun.istack.internal.NotNull;
import demo.mob.medusa.Medusa;
import demo.mob.skelly.Skelly;
import demo.mob.slime.Slime;
import demo.mob.treasure.Doorkey;
import demo.mob.treasure.Potion;
import demo.tile.DemoTile;
import demo.tile.Tile;
import demo.tile.Tiles;
import entity.Entity;
import gamestate.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Area_1 extends Area {

    private int[] mobSpawns;

    int[] getMobSpawns() {
        return mobSpawns;
    }

    void loadMobs(@NotNull String path) {

        mobSpawns = new int[getMapWidth() * getMapHeight()];

        loadTiles(path, mobSpawns);
        for (int x = 0; x < getMapWidth(); x++) {
            for (int y = 0; y < getMapHeight(); y++) {
                switch (mobSpawns[x + y * getMapWidth()]) {
                    case 0xffff0000:
                        Entity potion = new Potion(x * DemoTile.SIZE, y * DemoTile.SIZE);
                        potion.initialize(this);
                        addEntity(potion);
                        break;
                    case 0xffffff00:
                        Entity doorkey = new Doorkey(x * DemoTile.SIZE, y * DemoTile.SIZE);
                        doorkey.initialize(this);
                        addEntity(doorkey);
                        break;
                    case 0xff00ff00:
                        Entity slime = new Slime(x * DemoTile.SIZE, y * DemoTile.SIZE, getPlayer());
                        slime.initialize(this);
                        addEntity(slime);
                        break;
                    case 0xff0000ff:
                        Entity skelly = new Skelly(x * DemoTile.SIZE, y * DemoTile.SIZE, getPlayer());
                        skelly.initialize(this);
                        addEntity(skelly);
                        break;
                    case 0xffb200ff:
                        Entity medusa = new Medusa(x * DemoTile.SIZE, y * DemoTile.SIZE);
                        medusa.initialize(this);
                        addEntity(medusa);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public Tile getMapTileObject(int x, int y) {
        if (x < 0 || y < 0 || x >= getMapWidth() || y >= getMapHeight())
            return Tiles.voidTile;

        switch (getMapTiles()[x + y * getMapWidth()]) {
            case 0xfffca75d:
                return Tiles.dirt;
            case 0xff267f00:
                return Tiles.cactus;
            case 0xffffffff:
                return Tiles.skelly;
            case 0xff00ffff:
                return Tiles.grave;
            case 0xffa5ff7f:
                return Tiles.cross;
            case 0xff606060:
                return Tiles.pillarTop;
            case 0xff808080:
                return Tiles.pillarSide;
            case 0xffc0c0c0:
                return Tiles.stoneGravelFloor;
            case 0xffa0a0cd:
                return Tiles.stoneTileFloor;
            case 0xffd0d0d0:
                return Tiles.stoneThreshold;
            case 0xff21007f:
                return Tiles.stoneDoorway;
            case 0xff303030:
                return Tiles.stoneStairsDown;
            case 0xffff8800:
                return Tiles.stoneStairsUp;
            case 0xff0094ff:
                return Tiles.dungeonDoorLocked;
            case 0xffb200ff:
                return Tiles.dungeonDoorOpen;
            case 0xff7fffc5:
                return Tiles.water;
            case 0xffe90064:
                return Tiles.floorSwitchUp;
            case 0xffbc0051:
                return Tiles.floorSwitchDown;
            default:
                return Tiles.voidTile;
        }
    }

    protected void pixelsToPNG(int[] pixels, String fileName) {
        BufferedImage image = new BufferedImage(getMapWidth(), getMapHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        for (int y = 0; y < getMapHeight(); y++) {
            for (int x = 0; x < getMapWidth(); x++) {
                g.setColor(new Color(pixels[x + y * getMapWidth()]));
                g.fillRect(x, y, 1, 1);
            }
        }

        try {
            File file = new File(fileName);
            ImageIO.write(image, "PNG", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMobSpawn(int x, int y, int col) {
        mobSpawns[x / DemoTile.SIZE + y / DemoTile.SIZE * getMapWidth()] = col;
    }
}
