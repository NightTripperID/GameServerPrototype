package demo;

import demo.area.Area_1_1;
import demo.area.Area_2_2;
import demo.mob.player.Player;
import demo.tile.TileCoord;
import gamestate.Bundle;
import gamestate.Intent;
import server.Server;

public class Main {
    public static void main(String[] args) {

        Bundle bundle = new Bundle();
//        bundle.putExtra("tileCoord", new TileCoord(14, 17, 16));
        bundle.putExtra("tileCoord", new TileCoord(23, 12, 16));
        bundle.putExtra("player", new Player(0, 0));

//        Intent intent = new Intent(Area_1_1.class);
        Intent intent = new Intent(Area_2_2.class);

        intent.putExtra("bundle", bundle);

        Server server = new Server(320, 240, 3, "Demo");
        server.startServer(intent);
    }
}
