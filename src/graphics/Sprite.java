package graphics;

/**
 * Represents a 2 dimensional graphic on screen. graphics.Sprite data is loaded from a graphics.SpriteSheet object. Width, height, and coordinates
 * can be specified. Sprites can also be rotated using affine matrix transformation.
 * @author Noah
 *
 */
public class Sprite {

    public final int size;
    private int x, y;
    public final int width;
    public final int height;
    public int[] pixels;
    protected SpriteSheet sheet;

    /**
     * Used by AnimSprite extension to create a graphics.Sprite that represents a sprite strip to be animated.
     * @param sheet: The sheet from which the sprite strip is grabbed.
     * @param width: The width of the sprite strip.
     * @param height: The height of the sprite strip.
     */
    protected Sprite(SpriteSheet sheet, int width, int height) {
        size = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.sheet = sheet;
    }

    /**
     * Creates a sprite of size dimensions starting at specified x and y coordinates from specified graphics.SpriteSheet.
     * @param size: Width and height of sprite in pixel precision.
     * @param x: x coordinate of graphics.Sprite on graphics.SpriteSheet in pixel precision.
     * @param y: y coordinate of graphics.Sprite on graphics.SpriteSheet in pixel precision.
     * @param sheet: graphics.SpriteSheet from which new graphics.Sprite object is grabbed.
     */
    public Sprite(int size, int x, int y, SpriteSheet sheet) {
        this.size = size;
        width = size;
        height = size;
        pixels = new int[size * size];
        this.x = x * size;
        this.y = y * size;
        this.sheet = sheet;
        load();
    }

    /**
     * Creates new graphics.Sprite of specified solid color.
     * @param width: Width of graphics.Sprite in pixel precision.
     * @param height: Height of graphics.Sprite in pixel precision.
     * @param color: Color with which to fill the entire graphics.Sprite.
     */
    public Sprite(int width, int height, int color) {
        size = -1;
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
        setColor(color);
    }

    /** Creates new graphics.Sprite of specified solid color.
     * @param size: Width and height of graphics.Sprite in pixel precision.
     * @param color: Color with which to fill the entire graphics.Sprite.
     */
    public Sprite(int size, int color) {
        this.size = size;
        width = size;
        height = size;
        pixels = new int[size * size];
        setColor(color);
    }

    /**
     * Creates new sprite from specified pixel buffer.
     * @param pixels: Pixel buffer from which to create graphics.Sprite.
     * @param width: Width of graphics.Sprite in pixel precision.
     * @param height: Height of graphics.Sprite in pixel precision.
     */
    public Sprite(int[] pixels, int width, int height) {
        size = (width == height) ? width : -1;
        this.width = width;
        this.height = height;
        this.pixels = new int[pixels.length];
        System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
    }

    public static Sprite[] split(SpriteSheet sheet) {
        int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.spriteWidth * sheet.spriteHeight);
        Sprite[] sprites = new Sprite[amount];
        int current = 0;
        int[] pixels = new int[sheet.spriteWidth * sheet.spriteHeight];

        for (int yp = 0; yp < sheet.getHeight() / sheet.spriteHeight; yp++) {
            for (int xp = 0; xp < sheet.getWidth() / sheet.spriteWidth; xp++) {

                for (int y = 0; y < sheet.spriteHeight; y++) {
                    for (int x = 0; x < sheet.spriteWidth; x++) {
                        int xo = x + xp * sheet.spriteWidth;
                        int yo = y + yp * sheet.spriteHeight;
                        pixels[x + y * sheet.spriteWidth] = sheet.getPixels()[xo + yo * sheet.getWidth()];
                    }
                }
                sprites[current++] = new Sprite(pixels, sheet.spriteWidth, sheet.spriteHeight);
            }
        }

        return sprites;
    }

    /**
     * Rotate specified sprite by the specified angle.
     * @param sprite: The specified graphics.Sprite object.
     * @param angle: The angle in radians.
     * @return the rotated sprite
     */
    public static Sprite rotate(Sprite sprite, double angle){
        return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
    }

    private  static int[] rotate(int[] pixels, int width, int height, double angle) {
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

    private void setColor(int color) {
        for (int i = 0; i < width * height; i++) {
            pixels[i] = color;
        }
    }

    private void load() {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[x + y * size] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.size];
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY(){
        return y;
    }
}
