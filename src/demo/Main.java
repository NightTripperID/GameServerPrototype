package demo;

import demo.level.Level;
import demo.mob.DemoCursor;
import demo.mob.Player;
import gamestate.Intent;

import server.Server;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Intent intent = new Intent(Level.class);
        intent.putExtra("player", new Player(10, 10));

        DemoCursor cursor = new DemoCursor(new Point(16, 16), "cursor", "res/pointerup.png", "res/pointerdown.png");
        intent.putExtra("cursor", cursor);

        Server server = new Server(320, 240, 3, "Demo");

        server.setCustomMouseCursor(cursor.getImage(DemoCursor.CURSOR_UP),
                cursor.getCursorHotSpot(), cursor.getName());

        server.startServer(intent);
    }
}
