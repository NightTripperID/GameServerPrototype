package demo;

import demo.title.Title;
import gamestate.Intent;
import kuusisto.tinysound.TinySound;
import server.Server;

public class Main {
    public static void main(String[] args) {

        TinySound.init();
        TinySound.shutdown();

        Intent intent = new Intent(Title.class);
        Server server = new Server(320, 240, 3, "Varg");
        server.startServer(intent);
    }
}
