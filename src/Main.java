import gamestate.Intent;
import server.Server;
import test.gamestates.LevelOne;
import test.mobs.Blotty;
import test.mobs.MouseCursor;
import test.mobs.Pudgie;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        Intent intent = new Intent(LevelOne.class);
        intent.putExtra("pudgie", new Pudgie(0, 0));
        intent.putExtra("cursor", new MouseCursor());
        intent.putExtra("blotty", new Blotty(20, 20));
        Server server = new Server(321, 240, 3, "Test");
        server.setCustomMouseCursor("res/pointerup.png", new Point(0, 0), "test");
        server.startServer(intent);
    }
}
