package demo;

import demo.audio.Jukebox;
import demo.audio.Sfx;
import demo.shutdown.ShutdownThread;
import demo.title.Title;
import gamestate.Intent;
import kuusisto.tinysound.Music;
import kuusisto.tinysound.TinySound;
import server.Server;

public class Main {
    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new ShutdownThread());

        TinySound.init();
        Sfx.VOID_SOUND.play(); // blank wav file to "prime" TinySound by loading it into memory
        Jukebox.VOID_MUSIC.play(false);

        Intent intent = new Intent(Title.class);
        Server server = new Server(320, 200, 3, "Varg");
        server.startServer(intent);
    }
}
