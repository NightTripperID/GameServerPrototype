package demo.textbox;

import demo.tile.DemoTile;
import gamestate.GameState;
import gamestate.Intent;
import graphics.Screen;
import server.Server;

public class TextBox extends GameState {

    private int[] pixels;
    private int textCol;
    private StringBuilder msg;
    private String[] inputLines;
    private String[] outputLines;

    private int scrollCount;
    private int scrollX;
    private int scrollY;

    private final int textW = 36;
    private int textH;

    @Override
    public void onCreate(Server server) {
        super.onCreate(server);

        Intent intent = getIntent();
        pixels = intent.getIntegerArrayExtra("pixels");
        textCol = intent.getIntegerExtra("textCol");
        msg = new StringBuilder(intent.getStringExtra("msg"));

        int messageLength = msg.length();

        textH = messageLength / textW;

        if (messageLength % textW > 0)
            textH += 1;

        inputLines = new String[textH];
        outputLines = new String[textH];
        for(int i = 0; i < textH; i++)
            outputLines[i] = "";

        for (int y = 0; y < textH; y++) {
            StringBuilder sb = new StringBuilder();
            Row:
            for (int x = 0; x < textW; x++) {
                if(x == 0 && msg.charAt(0) == ' ')
                    msg.deleteCharAt(0);
                if (x + y * textW >= messageLength || msg.length() == 0)
                    break;
                int count = 0;
                if (msg.charAt(0) == ' ')
                    for (int i = 1; i < msg.length(); i++) {
                        count++;
                        if (msg.charAt(i) == ' ') {
                            if (count > textW - x) {
                                messageLength += (textW - x);
                                break Row;
                            } else {
                                break;
                            }
                        }
                    }
                sb.append(msg.charAt(0));
                msg.deleteCharAt(0);
            }
            inputLines[y] = sb.toString();
        }
    }

    @Override
    public void update() {
        int scrollWait;
        if(getKeyboard().spaceHeld || getKeyboard().enterHeld)
            scrollWait = 0;
        else
            scrollWait = 5;

        if(++scrollCount >= scrollWait) {
            scrollCount = 0;
            if (scrollY < textH) {
                outputLines[scrollY] += inputLines[scrollY].charAt(scrollX++);
                if (scrollX > inputLines[scrollY].length() - 1) {
                    scrollX = 0;
                    scrollY++;
                }
            }
        }

        if (getKeyboard().spacePressed || getKeyboard().enterPressed)
            if(scrollY == textH)
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
                screen.renderString8x8(x + 8, (y + ((i + 1) << 3) + i), textCol, outputLines[i]);
    }
}
