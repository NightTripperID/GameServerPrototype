package com.github.nighttripperid.littleengine.staticutil;

import com.github.nighttripperid.littleengine.model.graphics.AnimSprite;
import com.github.nighttripperid.littleengine.model.graphics.Sprite;
import com.github.nighttripperid.littleengine.model.graphics.SpriteSheet;

public class SpriteUtil {

    private SpriteUtil() {
    }

    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getSheetWidth() * sheet.getSheetHeight()) / (sheet.spriteWidth * sheet.spriteHeight);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.spriteWidth * sheet.spriteHeight];

        for (int yp = 0; yp < sheet.getSheetHeight() / sheet.spriteHeight; yp++) {
            for (int xp = 0; xp < sheet.getSheetWidth() / sheet.spriteWidth; xp++) {

                for (int y = 0; y < sheet.spriteHeight; y++) {
                    for (int x = 0; x < sheet.spriteWidth; x++) {
                        int xo = x + xp * sheet.spriteWidth;
                        int yo = y + yp * sheet.spriteHeight;
                        pixels[x + y * sheet.spriteWidth] = sheet.getPixelBuffer()[xo + yo * sheet.getSheetWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.spriteWidth, sheet.spriteHeight);
            }
        }
        return sprites;
    }

    public static Sprite rotate(Sprite sprite, double angle){
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    public static AnimSprite updateAnimFrame(AnimSprite animSprite) {
        AnimSprite copy = new AnimSprite(animSprite);
        if(++copy.time % copy.frameRate == 0) {
            if (copy.getFrame() == copy.length)
                copy.setFrame(0);
            copy.sprite = copy.getSpriteSheet().getSprites()[copy.getFrame()];
            copy.setFrame(copy.getFrame() + 1);
        }
        return copy;
    }

    private static int[] rotate(int[] pixels, int width, int height, double angle) {
        int[] result = new int[width * height];

        double nx_x = rot_x(-angle, 1.0, 0.0);
        double nx_y = rot_y(-angle, 1.0, 0.0);
        double ny_x = rot_x(-angle, 0.0, 1.0);
        double ny_y = rot_y(-angle, 0.0, 1.0);

        double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
        double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;

        for (int y = 0; y < height; y++) {
            double x1 = x0;
            double y1 = y0;
            for (int x = 0; x < width; x++) {
                int xx = (int) x1;
                int yy = (int) y1;
                int col = 0;
                if (xx < 0 || xx >= width || yy < 0 || yy >= height)
                    col = 0xffff00ff;
                else
                    col = pixels[xx + yy * width];
                result[x + y * width] = col;
                x1 += nx_x;
                y1 += nx_y;
            }
            x0 += ny_x;
            y0 += ny_y;
        }

        return result;
    }

    private static double rot_x(double angle, double x, double y) {
        double cos = Math.cos(angle-Math.PI/2);
        double sin = Math.sin(angle- Math.PI/2);
        return x * cos + y * -sin;
    }

    private static double rot_y(double angle, double x, double y) {
        double cos = Math.cos(angle-Math.PI/2);
        double sin = Math.sin(angle-Math.PI/2);
        return x * sin + y * cos;
    }

}
