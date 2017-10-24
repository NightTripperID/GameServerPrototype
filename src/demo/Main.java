package demo;

import demo.title.Title;
import gamestate.Intent;
import server.Server;

public class Main {
    public static void main(String[] args) {

//        Bundle bundle = new Bundle();
//        bundle.putExtra("tileCoord", new TileCoord(14, 17, 16));
////        bundle.putExtra("tileCoord", new TileCoord(17, 8, 16));
////        bundle.putExtra("tileCoord", new TileCoord(23, 12, 16));
//
//        Player player = new Player(0, 0);
////        player.inventory.add("blue_doorkey");
////        player.inventory.add("blue_doorkey");
////        player.inventory.add("blue_doorkey");
//        player.inventory.add("potion");
//        bundle.putExtra("player", player);
//
//        Intent intent = new Intent(Zone_1_1.class);
////        Intent intent = new Intent(Zone_1_4.class);
////        Intent intent = new Intent(Zone_2_2.class);
//
//        intent.putExtra("bundle", bundle);
//
//        Server server = new Server(320, 240, 3, "Demo");
//        server.startServer(intent);

        Intent intent = new Intent(Title.class);
        Server server = new Server(320, 240, 3, "Demo");
        server.startServer(intent);
    }
}
