package demo.mob.enemy.slime;

import gamestate.GameState;

public class SlimeStateFlying extends SlimeState {

    private final int maxYspeed = 3;
    private float currYSpeed = maxYspeed;

    public SlimeStateFlying(Slime slime, GameState gameState) {
        super(slime, gameState);
        slime.setyDir(-1);
        slime.setxDir(random.nextInt(2) == 0 ? -1 : 1);
        slime.setxSpeed(1);
    }

    @Override
    public void update() {
        mob.setySpeed(currYSpeed);
        currYSpeed += 0.1 * mob.getyDir();

        if((int) currYSpeed == 0)
            mob.setyDir(1);
        if((int) currYSpeed == maxYspeed)
            mob.setCurrState(new SlimeStateChasing((Slime) mob, gameState));

        mob.xa = mob.getxSpeed() * mob.getxDir();
        mob.ya = mob.getySpeed() * mob.getyDir();
        commitMove(mob.xa, mob.ya);
    }
}
