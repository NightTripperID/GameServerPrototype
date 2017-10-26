package demo.zone;

import com.sun.istack.internal.NotNull;
import demo.audio.Sfx;
import demo.mob.enemy.boss.Boss;
import demo.mob.Mob;
import demo.mob.enemy.buzzard.Buzzard;
import demo.mob.enemy.dinodrac.DinoDrac;
import demo.mob.enemy.medusa.MedusaSpawner;
import demo.mob.enemy.roach.Roach;
import demo.mob.enemy.skelly.SkellySpawner;
import demo.mob.enemy.slime.SlimeSpawner;
import demo.mob.player.Player;
import demo.mob.item.Potion;
import demo.mob.item.Doorkey;
import demo.overlay.Overlay;
import demo.textbox.TextBox;
import demo.tile.DemoTile;
import demo.tile.TileCoord;
import demo.transition.FadeOut;
import entity.Entity;
import gamestate.Bundle;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import kuusisto.tinysound.Sound;
import server.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Zone extends GameState {

    protected Player player;
    private Overlay overlay;

    private int[] mobTiles;

    protected static final int FADE_RATE = 6;

    int[] getMobTiles() {
        return mobTiles;
    }

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);
        Bundle bundle = (Bundle) getIntent().getSerializableExtra("bundle");
        player = (Player) bundle.getSerializableExtra("player");
        TileCoord tileCoord = (TileCoord) bundle.getSerializableExtra("tileCoord");
        player.x = tileCoord.getX();
        player.y = tileCoord.getY();
        player.setRespawn(tileCoord);
        player.initialize(this);
        addEntity(player);
        setOverlay(new Overlay(player));

        setScrollX((int) player.x - getScreenWidth() / 2);
        setScrollY((int) player.y - getScreenHeight() / 2);
    }

    @Override
    public void update() {
        checkCollision();
        super.update();
        overlay.update();
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
        overlay.render(screen);
    }

    private void checkCollision() {

        List<Mob> mobs = new ArrayList<>();

        for (Entity e : getEntities())
            if (e instanceof Mob)
                mobs.add((Mob) e);

        for (int i = 0; i < mobs.size(); i++) {
            for (int k = i + 1; k < mobs.size(); k++) {
                if (mobs.get(i).collidesWith(mobs.get(k))) {
                    mobs.get(i).runCollision(mobs.get(k));
                    mobs.get(k).runCollision(mobs.get(i));
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    void pixelsToPNG(int[] pixels, @NotNull String fileName) {
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
        mobTiles[x / DemoTile.SIZE + y / DemoTile.SIZE * getMapWidth()] = col;
    }

    void loadMobs(@NotNull String path) {

        mobTiles = new int[getMapWidth() * getMapHeight()];

        loadTiles(path, mobTiles);
        for (int x = 0; x < getMapWidth(); x++) {
            for (int y = 0; y < getMapHeight(); y++) {
                switch (mobTiles[x + y * getMapWidth()]) {
                    case 0xffff0000:
                        Entity potion = new Potion(0xffff0000, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        potion.initialize(this);
                        addEntity(potion);
                        break;
                    case 0xffffff00:
                        Entity doorkey = new Doorkey(0xffffff00, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        doorkey.initialize(this);
                        addEntity(doorkey);
                        break;
                    case 0xff00ff00:
                        Entity slimeSpawner = new SlimeSpawner(0xff00ff00, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        slimeSpawner.initialize(this);
                        addEntity(slimeSpawner);
                        break;
                    case 0xff0000ff:
                        Entity skellySpawner = new SkellySpawner(0xff0000ff, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        skellySpawner.initialize(this);
                        addEntity(skellySpawner);
                        break;
                    case 0xffb200ff:
                        Entity medusaSpawner = new MedusaSpawner(0xffb200ff, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        medusaSpawner.initialize(this);
                        addEntity(medusaSpawner);
                        break;
                    case 0xff00ffff:
                        Entity roach = new Roach(0xff00ffff, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        roach.initialize(this);
                        addEntity(roach);
                        break;
                    case 0xff4cff00:
                        Entity dinoDrac = new DinoDrac(0xff4cff00, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        dinoDrac.initialize(this);
                        addEntity(dinoDrac);
                        break;
                    case 0xff7c5033:
                        Entity buzzard = new Buzzard(0xff7c5033, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        buzzard.initialize(this);
                        addEntity(buzzard);
                        break;
                    case 0xff978cff:
                        Entity boss = new Boss(0xff978cff, x * DemoTile.SIZE, y * DemoTile.SIZE);
                        boss.initialize(this);
                        addEntity(boss);
                    default:
                        break;
                }
            }
        }
    }

    void setOverlay(Overlay overlay) {
        this.overlay = overlay;
        overlay.initialize(this);
    }

    public void changeZone(Class<? extends GameState> gsc, TileCoord tileCoord) {
        Bundle bundle = new Bundle();

        bundle.putExtra("tileCoord", tileCoord);
        bundle.putExtra("player", player);

        Intent intent = new Intent(FadeOut.class);
        intent.putExtra("bundle", bundle);
        intent.putExtra("nextGameState", gsc);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("fadeRate", FADE_RATE);

        swapGameState(intent);
    }

    public void createTextBox(int textCol, String msg) {
        Intent intent = new Intent(TextBox.class);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("textCol", textCol);
        intent.putExtra("msg", msg);
        pushGameState(intent);
    }

    public void createTextBox(int textCol, String msg, Sfx sfx) {
        Intent intent = new Intent(TextBox.class);
        intent.putExtra("pixels", getScreenPixels());
        intent.putExtra("textCol", textCol);
        intent.putExtra("msg", msg);
        intent.putExtra("sfx", sfx);
        pushGameState(intent);
    }
}
