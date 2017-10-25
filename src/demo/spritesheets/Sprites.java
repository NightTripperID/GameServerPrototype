package demo.spritesheets;

import graphics.AnimSprite;
import graphics.Sprite;

public class Sprites {

    // ITEMS
//    public static final Sprite BLUE_DOORKEY = new Sprite(SpriteSheets.ITEMS, 8, 1, 1); // TODO: fix bug in SpriteSheet constructor
//    public static final Sprite HEART = new Sprite(SpriteSheets.ITEMS, 8, 0, 0);        // TODO: when 8x8 tiles are grabbed from
//    public static final Sprite POTION = new Sprite(SpriteSheets.ITEMS, 8, 0, 1);       // TODO: another SpriteSheet
//    public static final Sprite YELLOW_DOORKEY = new Sprite(SpriteSheets.ITEMS, 8, 1, 0);  
    
    public static final Sprite HEART = new Sprite(SpriteSheets.MAIN_SHEET, 8, 6, 0);
    public static final Sprite POTION = new Sprite(SpriteSheets.MAIN_SHEET, 8, 6, 1);
    public static final Sprite YELLOW_DOORKEY = new Sprite(SpriteSheets.MAIN_SHEET, 8, 7, 0);

    // PROJECTILES
    public static final Sprite AXE = new Sprite(SpriteSheets.MAIN_SHEET, 16, 2, 0);
    public static final Sprite BLUE_BOLT = new Sprite(SpriteSheets.MAIN_SHEET, 8, 7, 16);
    public static final Sprite GREEN_BOLT = new Sprite(SpriteSheets.MAIN_SHEET, 8, 6, 16);
    public static final Sprite YELLOW_BOLT = new Sprite(SpriteSheets.MAIN_SHEET, 8, 6, 17);

    // NON-ANIMATED TILES
    public static final Sprite CACTUS = new Sprite(SpriteSheets.MAIN_SHEET, 16, 11, 1);
    public static final Sprite CROSS = new Sprite(SpriteSheets.MAIN_SHEET, 16, 11, 4);
    public static final Sprite DIRT = new Sprite(SpriteSheets.MAIN_SHEET, 16, 8, 2);
    public static final Sprite DUNGEON_DOOR_LOCKED = new Sprite(SpriteSheets.MAIN_SHEET, 16, 9, 4);
    public static final Sprite DUNGEON_DOOR_OPEN = new Sprite(SpriteSheets.MAIN_SHEET, 16, 9, 5);
    public static final Sprite DUNGEON_GATE = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 2);
    public static final Sprite EARTH_TOP = new Sprite(SpriteSheets.MAIN_SHEET, 16, 8, 0);
    public static final Sprite EARTH_SIDE = new Sprite(SpriteSheets.MAIN_SHEET, 16, 8, 1);
    public static final Sprite FLOOR_SWITCH_UP = new Sprite(SpriteSheets.MAIN_SHEET, 16, 9, 6);
    public static final Sprite FLOOR_SWITCH_DOWN = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 6);
    public static final Sprite GRAVE = new Sprite(SpriteSheets.MAIN_SHEET, 16, 11, 2);
    public static final Sprite LAVA_GRATE = new Sprite(SpriteSheets.MAIN_SHEET, 16, 8, 3);
    public static final Sprite MEDUSA_SPAWNER = new Sprite(SpriteSheets.MAIN_SHEET, 16, 3, 6);
    public static final Sprite OBELISK_TOP = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 0);
    public static final Sprite OBELISK_BOTTOM = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 1);
    public static final Sprite PILLAR_TOP = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 0);
    public static final Sprite PILLAR_SIDE = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 1);
    public static final Sprite SAND = new Sprite(SpriteSheets.MAIN_SHEET, 16, 11, 0);
    public static final Sprite SCORCHED_SAND = new Sprite(SpriteSheets.MAIN_SHEET, 16, 8, 6);
    public static final Sprite SIGN = new Sprite(SpriteSheets.MAIN_SHEET, 16, 11, 8);
    public static final Sprite SKELLY = new Sprite(SpriteSheets.MAIN_SHEET, 16, 11, 3);
    public static final Sprite STONE_DOORWAY = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 3);
    public static final Sprite STONE_GRAVEL_FLOOR = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 2);
    public static final Sprite STONE_SIDE_L = new Sprite(SpriteSheets.MAIN_SHEET, 16, 9, 8);
    public static final Sprite STONE_SIDE_R = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 8);
    public static final Sprite STONE_STAIRS_DOWN = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 4);
    public static final Sprite STONE_STAIRS_UP = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 5);
    public static final Sprite STONE_TILE_FLOOR = new Sprite(SpriteSheets.MAIN_SHEET, 16, 9, 3);
    public static final Sprite STONE_TOP_L = new Sprite(SpriteSheets.MAIN_SHEET, 16, 9, 7);
    public static final Sprite STONE_TOP_R = new Sprite(SpriteSheets.MAIN_SHEET, 16, 10, 7);
    public static final Sprite VULTURE_SLAB_TL = new Sprite(SpriteSheets.MAIN_SHEET, 16, 6, 6);
    public static final Sprite VULTURE_SLAB_TR = new Sprite(SpriteSheets.MAIN_SHEET, 16, 6, 7);
    public static final Sprite VULTURE_SLAB_BL = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 6);
    public static final Sprite VULTURE_SLAB_BR = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 7);
    public static final Sprite WIDE_DOOR_CENTER = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 4);
    public static final Sprite WIDE_DOOR_LEFT = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 3);
    public static final Sprite WIDE_DOOR_RIGHT = new Sprite(SpriteSheets.MAIN_SHEET, 16, 7, 5);

    // ANIMATED TILES
    public static final AnimSprite LAVA = new AnimSprite(SpriteSheets.LAVA, 16, 16, 2);
    public static final AnimSprite PURPLE_LAVA = new AnimSprite(SpriteSheets.PURPLE_LAVA, 16, 16, 2);
    public static final AnimSprite WATER = new AnimSprite(SpriteSheets.WATER, 16, 16, 2);

    //TITLE
    public static final Sprite TITLE_SPRITE = new Sprite(SpriteSheets.TITLE_SHEET, 125, 34, 0, 0);

    private Sprites() {
    }
}
