import gamestate.Intent;
import server.Server;
import test.gamestates.LevelOne;
import test.gamestates.Title;
import test.mobs.MouseCursor;
import test.mobs.Pudgie;

public class Main {

    public static void main(String[] args) {

        Intent intent = new Intent(LevelOne.class);
        intent.putExtra("pudgie", new Pudgie(0, 0));
        intent.putExtra("cursor", new MouseCursor(10, 10));
        new Server(321, 240, 3, "Test").startServer(intent);
    }
}
