package demo.textbox;

import demo.tile.DemoTile;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import server.Server;

public class TextBox extends GameState {

    private int[] pixels;
    private int textCol;
    private String msg;
    private String[] lines;


    private final int textW = 36;
    private int textH;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        Intent intent = getIntent();
        pixels = intent.getIntegerArrayExtra("pixels");
        textCol = intent.getIntegerExtra("textCol");
        msg = intent.getStringExtra("msg");

        textH = msg.length() / textW;

        if (msg.length() % textW != 1)
            textH += 1;
        System.out.println(textH);

        lines = new String[textH];

        StringBuilder msgSb = new StringBuilder(msg);

        int messageLength = msg.length();

        for (int y = 0; y < textH; y++) {
            StringBuilder sb = new StringBuilder();
            Row:
            for (int x = 0; x < textW; x++) {
                if(x == 0 && msgSb.charAt(0) == ' ')
                    msgSb.deleteCharAt(0);
                if (x + y * textW >= messageLength || msgSb.length() == 0)
                    break;
                int count = 0;
                if (msgSb.charAt(0) == ' ')
                    for (int i = 1; i < msgSb.length(); i++) {
                        count++;
                        if (msgSb.charAt(i) == ' ') {
                            if (count > textW - x) {
                                messageLength += (textW - x);
                                break Row;
                            } else {
                                break;
                            }
                        }
                    }
                sb.append(msgSb.charAt(0));
                msgSb.deleteCharAt(0);
            }
            lines[y] = sb.toString();
        }


    }

    @Override
    public void update() {
        if (getKeyboard().enterPressed || getKeyboard().spacePressed)
            popGameState();
    }

    @Override
    public void render(Screen screen) {

        screen.renderPixels(pixels);

        int x = 10;
        int y = (getScreenHeight() >> 1) + DemoTile.SIZE + 10;
        int w = getScreenWidth() - 20;
        int h = (getScreenHeight() >> 1) - DemoTile.SIZE - 20;
        int boxCol = 0x000000;
        int borderCol = 0xffffff;

        screen.fillRect(x, y, w, h, boxCol);
        screen.drawRect(x, y, w, h, borderCol);

        for (int i = 0; i < textH; i++)
            screen.renderString8x8(x + 8, (y + ((i + 1) << 3) + i), textCol, lines[i]);
    }
}
