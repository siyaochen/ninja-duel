/**
 * Tile
 * This class represents the tiles of the field
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */


import java.awt.image.BufferedImage;

public abstract class Tile {

    public int xPos, yPos; // Position of tile
    public BufferedImage sprite; // Sprite of tile
    public PowerUp powerUp; // Power-up on tile, if any (none = null)

    /**
     * Tile
     * Constructor
     * @param xPos The x-position of the tile
     * @param yPos The y-position of the tile
     */
    public Tile(int xPos, int yPos) {
        // Set position
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * getSprite
     * This method returns the sprite of the tile
     * @return The sprite of the tile
     */
    public BufferedImage getSprite() {
        return sprite;
    }

}
