import demo.mobs.DemoCursor;
import gamestate.Intent;
import server.Server;
import demo.gamestates.LevelOne;
import demo.mobs.Blotty;
import demo.mobs.Pudgie;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Intent intent = new Intent(LevelOne.class);
        intent.putExtra("pudgie", new Pudgie(0, 0));
        intent.putExtra("cursor", new DemoCursor(new Point(16, 16)));
        intent.putExtra("blotty", new Blotty(20, 20));
        Server server = new Server(321, 240, 3, "Test");
        server.setCustomMouseCursor("res/pointerup.png", new Point(16, 16), "demo");
        server.startServer(intent);
    }
}
