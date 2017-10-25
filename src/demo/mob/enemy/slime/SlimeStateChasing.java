package demo.mob.enemy.slime;

import demo.mob.Mob;
import gamestate.GameState;

public class SlimeStateChasing extends SlimeState {

    private int count;
    private final int skip = random.nextInt(3) + 1;

    public SlimeStateChasing(Slime mob, GameState gameState) {
        super(mob, gameState);
    }

    @Override
    public void update() {

        mob.getCurrSprite().update();

        if (count++ / skip == 1) {
            count = 0;
            move();
        }
    }

    private void move() {

        Mob player = ((Slime) mob).player;

        if (mob.x == player.x)
            mob.setxSpeed(0);
        else
            mob.setxSpeed(1);

        if (mob.y == player.y)
            mob.setySpeed(0);
        else
            mob.setySpeed(1);

        if (mob.x > player.x)
            mob.setxDir(-1);
        else if (mob.x < player.x)
            mob.setxDir(1);

        if (mob.y > player.y)
            mob.setyDir(-1);
        else if (mob.y < player.y)
            mob.setyDir(1);

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();

        commitMove(mob.xa, mob.ya);
    }
}
