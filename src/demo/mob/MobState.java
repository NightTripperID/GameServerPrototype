package demo.mob;

public abstract class MobState {

    protected Mob mob;

    public MobState(Mob mob) {
        this.mob = mob;
    }

    public abstract MobState update();
}
