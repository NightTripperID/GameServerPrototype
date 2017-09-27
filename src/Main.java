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

        DemoCursor cursor = new DemoCursor(new Point(16, 16), "demo", "res/pointerup.png", "res/pointerdown.png");
        intent.putExtra("cursor", cursor);

        intent.putExtra("blotty", new Blotty(20, 20));

        Server server = new Server(320, 240, 3, "Test");

        server.setCustomMouseCursor(cursor.getImage(DemoCursor.CURSOR_UP), cursor.getCursorHotSpot(), cursor.getName());

        server.startServer(intent);
    }
}
