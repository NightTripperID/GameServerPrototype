package demo;

import demo.area.Area_1_1;
import demo.mob.DemoCursor;
import demo.player.Player;
import demo.tile.TileCoord;
import gamestate.Intent;

import server.Server;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Intent intent = new Intent(Area_1_1.class);
        TileCoord tileCoord = new TileCoord(11, 7, 16);
        intent.putExtra("player", new Player(tileCoord.getX(), tileCoord.getY()));

        DemoCursor cursor = new DemoCursor(new Point(8, 8), "cursor", "src/resource/pointerup.png", "src/resource/pointerdown.png");
        intent.putExtra("cursor", cursor);

        Server server = new Server(320, 240, 3, "Demo");

        server.setCustomMouseCursor(cursor.getImage(DemoCursor.CURSOR_UP),
                cursor.getCursorHotSpot(), cursor.getName());

        server.startServer(intent);
    }
}
