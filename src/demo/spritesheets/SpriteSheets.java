package demo.spritesheets;

import graphics.SpriteSheet;

public class SpriteSheets {

    public static final SpriteSheet MAIN_SHEET = new SpriteSheet("resource/spritesheet.png", 192, 288);

    public static final SpriteSheet TITLE_SHEET = new SpriteSheet("resource/title.png", 98, 34);

    // MOBS
    public static final SpriteSheet BOSS = new SpriteSheet(MAIN_SHEET, 2, 6, 1, 2, 32, 32);

    public static final SpriteSheet BUZZARD_CLOCKWISE = new SpriteSheet(MAIN_SHEET, 4, 8, 1, 2, 16, 16);
    public static final SpriteSheet BUZZARD_COUNTERCLOCKWISE = new SpriteSheet(MAIN_SHEET, 4, 10, 1, 2, 16, 16);

    public static final SpriteSheet DRAC_UP = new SpriteSheet(MAIN_SHEET, 2, 13, 1, 4, 16, 16);
    public static final SpriteSheet DRAC_DOWN = new SpriteSheet(MAIN_SHEET, 2, 9, 1, 4, 16, 16);
    public static final SpriteSheet DRAC_LEFT = new SpriteSheet(MAIN_SHEET, 3, 9, 1, 4, 16, 16);
    public static final SpriteSheet DRAC_RIGHT = new SpriteSheet(MAIN_SHEET, 3, 13, 1, 4, 16, 16);

    public static final SpriteSheet EXPLOSION = new SpriteSheet(MAIN_SHEET, 3, 3, 1, 3, 16, 16);

    public static final SpriteSheet MAGIC_1 = new SpriteSheet(MAIN_SHEET, 6, 2, 1, 4, 16, 16);

    public static final SpriteSheet MEDUSA_UP = new SpriteSheet(MAIN_SHEET, 2, 7, 1, 2, 16, 16);
    public static final SpriteSheet MEDUSA_DOWN = new SpriteSheet(MAIN_SHEET, 2, 5, 1, 2, 16, 16);

    public static final SpriteSheet PLAYER_UP = new SpriteSheet(MAIN_SHEET, 0, 4, 1, 4, 16, 16);
    public static final SpriteSheet PLAYER_DOWN = new SpriteSheet(MAIN_SHEET, 0, 0, 1, 4, 16, 16);
    public static final SpriteSheet PLAYER_LEFT = new SpriteSheet(MAIN_SHEET, 1, 0, 1, 4, 16, 16);
    public static final SpriteSheet PLAYER_RIGHT = new SpriteSheet(MAIN_SHEET, 1, 4, 1, 4, 16, 16);

    public static final SpriteSheet ROACH_UP = new SpriteSheet(MAIN_SHEET, 0, 8, 1, 4, 16, 16);
    public static final SpriteSheet ROACH_DOWN = new SpriteSheet(MAIN_SHEET, 0, 12, 1, 4, 16, 16);
    public static final SpriteSheet ROACH_LEFT = new SpriteSheet(MAIN_SHEET, 1, 8, 1, 4, 16, 16);
    public static final SpriteSheet ROACH_RIGHT = new SpriteSheet(MAIN_SHEET, 1, 12, 1, 4, 16, 16);

    public static final SpriteSheet REVENGE_SLIME = new SpriteSheet(MAIN_SHEET, 5, 8, 1, 4, 16, 16);

    public static final SpriteSheet SKELLY_UP = new SpriteSheet(MAIN_SHEET, 4, 4, 1, 4, 16, 16);
    public static final SpriteSheet SKELLY_DOWN = new SpriteSheet(MAIN_SHEET, 4, 0, 1, 4, 16, 16);
    public static final SpriteSheet SKELLY_LEFT = new SpriteSheet(MAIN_SHEET, 5, 0, 1, 4, 16, 16);
    public static final SpriteSheet SKELLY_RIGHT = new SpriteSheet(MAIN_SHEET, 5, 4, 1, 4, 16, 16);

    public static final SpriteSheet SLIME_SPAWNER = new SpriteSheet(MAIN_SHEET, 6, 0, 1, 2, 16, 16);
    public static final SpriteSheet SLIME = new SpriteSheet(MAIN_SHEET, 2, 1, 1, 4, 16, 16);

    // ANIMATED TILES
    static final SpriteSheet WATER = new SpriteSheet(MAIN_SHEET, 9, 0, 1, 2, 16, 16);
    static final SpriteSheet LAVA = new SpriteSheet(MAIN_SHEET, 8, 4, 1, 2, 16, 16);
    static final SpriteSheet PURPLE_LAVA = new SpriteSheet(MAIN_SHEET, 8, 7, 1, 2, 16, 16);

    private SpriteSheets(){
    }
}
