/**
 * PowerUp
 * This class represents the power-up object
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */

import java.awt.image.BufferedImage;

public abstract class PowerUp {

    public int xPos, yPos; // Position of power-up
    public BufferedImage sprite; // Sprite
    public long placedTime; // Time when placed

    /**
     * PowerUp
     * Constructor
     * @param tile The tile this power-up is on
     */
    public PowerUp(Tile tile) {
        this.xPos = tile.xPos;
        this.yPos = tile.yPos;

        // Add power-up to tile
        tile.powerUp = this;

        // Keep track of placed time
        placedTime = System.currentTimeMillis();
    }

    /**
     * getSprite
     * This method returns the sprite of the power-up
     * @return
     */
    public BufferedImage getSprite() {
        return sprite;
    }

}
