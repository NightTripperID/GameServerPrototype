package demo.area;

import demo.mob.Mob;
import entity.Entity;
import gamestate.GameState;

import java.util.ArrayList;
import java.util.List;

public abstract class Area extends GameState {

    @Override
    public void update() {
        super.update();
        checkCollision();
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
}
