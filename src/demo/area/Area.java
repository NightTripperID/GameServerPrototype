package demo.area;

import com.sun.istack.internal.NotNull;
import demo.mob.Mob;
import demo.mob.medusa.MedusaSpawner;
import demo.mob.player.Player;
import demo.mob.roach.Roach;
import demo.mob.skelly.SkellySpawner;
import demo.mob.slime.SlimeSpawner;
import demo.mob.treasure.Doorkey;
import demo.mob.treasure.Potion;
import demo.overlay.Overlay;
import demo.tile.DemoTile;
import entity.Entity;
import gamestate.Bundle;
import gamestate.GameState;
import graphics.Screen;
import server.Server;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Area extends GameState {

    private Player player;
    private Overlay overlay;

    private int[] mobTiles;

    int[] getMobTiles() {
        return mobTiles;
    }

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);
        Bundle bundle = (Bundle) getIntent().getSerializableExtra("bundle");
        player = (Player) bundle.getSerializableExtra("player");
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
                    case 0xff000f0f:
                        Entity roachWithKey = new Roach(0xff000f0f, x * DemoTile.SIZE, y * DemoTile.SIZE, true);
                        roachWithKey.initialize(this);
                        addEntity(roachWithKey);
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
}
