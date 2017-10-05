package demo.spritesheets;

import graphics.AnimSprite;

public class AnimSprites {

    public static final AnimSprite PLAYER_UP = new AnimSprite(SpriteSheets.PLAYER_UP, 16, 16, 4);
    public static final AnimSprite PLAYER_DOWN = new AnimSprite(SpriteSheets.PLAYER_DOWN, 16, 16, 4);
    public static final AnimSprite PLAYER_LEFT = new AnimSprite(SpriteSheets.PLAYER_LEFT, 16, 16, 4);
    public static final AnimSprite PLAYER_RIGHT = new AnimSprite(SpriteSheets.PLAYER_RIGHT, 16, 16, 4);

    public static final AnimSprite ARROW = new AnimSprite(SpriteSheets.ARROW, 16, 16, 1);

    private AnimSprites() {
    }
}
