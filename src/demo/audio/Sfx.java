package demo.audio;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.File;
import java.io.Serializable;

public class Sfx implements Serializable {

    public static final long serialVersionUID = 201710261333L;

    public static final Sfx AXE_THROW = new Sfx(TinySound.loadSound(new File("res/audio/sfx/axe_throw.wav")));
    public static final Sfx BOSS_EXPLODE = new Sfx(TinySound.loadSound(new File("res/audio/sfx/boss_explode.wav")));
    public static final Sfx DRINK_POTION = new Sfx(TinySound.loadSound(new File("res/audio/sfx/drink_potion.wav")));
    public static final Sfx ENEMY_EXPLODE = new Sfx(TinySound.loadSound(new File("res/audio/sfx/enemy_explode.wav")));
    public static final Sfx ENEMY_HIT = new Sfx(TinySound.loadSound(new File("res/audio/sfx/enemy_hit.wav")));
    public static final Sfx FLAME = new Sfx(TinySound.loadSound(new File("res/audio/sfx/flame.wav")));
    public static final Sfx GRAB_ITEM = new Sfx(TinySound.loadSound(new File("res/audio/sfx/grab_item.wav")));
    public static final Sfx HERO_DEAD = new Sfx(TinySound.loadSound(new File("res/audio/sfx/hero_dead.wav")));
    public static final Sfx HERO_HURT = new Sfx(TinySound.loadSound(new File("res/audio/sfx/hero_hurt.wav")));
    public static final Sfx SQUISH = new Sfx(TinySound.loadSound(new File("res/audio/sfx/squish.wav")));
    public static final Sfx SWITCH = new Sfx(TinySound.loadSound(new File("res/audio/sfx/switch.wav")));
    public static final Sfx TEXTBOX = new Sfx(TinySound.loadSound(new File("res/audio/sfx/textbox.wav")));
    public static final Sfx VOID_SOUND = new Sfx(TinySound.loadSound(new File("res/audio/sfx/void_sound.wav")));
    public static final Sfx WATER_DRAIN = new Sfx(TinySound.loadSound(new File("res/audio/sfx/water_drain.wav")));

    private Sound sound;

    private boolean playing;

    private Sfx(Sound sound) {
        this.sound = sound;
    }

    public void play() {
        sound.play();
    }
}
