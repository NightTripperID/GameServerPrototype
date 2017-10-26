package demo.audio;

import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

import java.io.File;

public class Sfx {

    public static final Sound AXE_THROW = TinySound.loadSound(new File("res/sound/axe_throw.wav"));
    public static final Sound DRINK_POTION = TinySound.loadSound(new File("res/sound/drink_potion.wav"));
    public static final Sound ENEMY_EXPLODE = TinySound.loadSound(new File("res/sound/enemy_explode.wav"));
    public static final Sound ENEMY_HIT = TinySound.loadSound(new File("res/sound/enemy_hit.wav"));
    public static final Sound FLAME = TinySound.loadSound(new File("res/sound/flame.wav"));
    public static final Sound GRAB_ITEM = TinySound.loadSound(new File("res/sound/grab_item.wav"));
    public static final Sound SWITCH = TinySound.loadSound(new File("res/sound/switch.wav"));
    public static final Sound TEXTBOX = TinySound.loadSound(new File("res/sound/textbox.wav"));
    public static final Sound VOID_SOUND = TinySound.loadSound(new File("res/sound/void_sound.wav"));
}
