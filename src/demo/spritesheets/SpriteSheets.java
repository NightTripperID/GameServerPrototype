package demo.spritesheets;

import graphics.SpriteSheet;

public class SpriteSheets {

    public static final SpriteSheet MAIN_SHEET = new SpriteSheet("resource/spritesheet.png", 192, 144);

    public static final SpriteSheet PLAYER_UP = new SpriteSheet(MAIN_SHEET, 0, 4, 1, 4, 16, 16);
    public static final SpriteSheet PLAYER_DOWN = new SpriteSheet(MAIN_SHEET, 0, 0, 1, 4, 16, 16);
    public static final SpriteSheet PLAYER_LEFT = new SpriteSheet(MAIN_SHEET, 1, 0, 1, 4, 16, 16);
    public static final SpriteSheet PLAYER_RIGHT = new SpriteSheet(MAIN_SHEET, 1, 4, 1, 4, 16, 16);

    public static final SpriteSheet SKELLY_UP = new SpriteSheet(MAIN_SHEET, 4, 4, 1, 4, 16, 16);
    public static final SpriteSheet SKELLY_DOWN = new SpriteSheet(MAIN_SHEET, 4, 0, 1, 4, 16, 16);

    public static final SpriteSheet SLIME = new SpriteSheet(MAIN_SHEET, 2, 1, 1, 4, 16, 16);

    public static final SpriteSheet ARROW = new SpriteSheet(MAIN_SHEET, 2, 0, 1, 1, 16, 16);

    public static final SpriteSheet HEART = new SpriteSheet(MAIN_SHEET, 6, 0, 1, 1, 8, 8);
    public static final SpriteSheet POTION = new SpriteSheet(MAIN_SHEET, 6, 1, 1, 1, 8, 8);
    public static final SpriteSheet DOORKEY = new SpriteSheet(MAIN_SHEET, 7, 0, 1, 1, 8, 8);

    private SpriteSheets(){
    }
}
