package demo.audio;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;

import java.io.File;

public class Jukebox {

    public static final Music BOSS_MUSIC = TinySound.loadMusic(new File("res/audio/music/boss_music.wav"));
    public static final Music DUNGEON_MUSIC = TinySound.loadMusic(new File("res/audio/music/dungeon_music.wav"));
    public static final Music VOID_MUSIC = TinySound.loadMusic(new File("res/audio/sfx/void_sound.wav"));
}
