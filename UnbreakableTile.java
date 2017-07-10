/**
 * UnbreakableTile
 * This class inherits from Tile and represents the breakable tiles
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */

/**
 * BreakableTile
 * This class inherits from Tile and represents the breakable tiles
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */

import java.awt.image.BufferedImage;

public class UnbreakableTile extends Tile {

    /**
     * UnbreakableTile
     * Constructor
     * @param xPos The x-position of the tile
     * @param yPos The y-position of the tile
     */
    public UnbreakableTile(int xPos, int yPos) {
        // Set up xPos and yPos
        super(xPos, yPos);
    }

    /**
     * getSprite
     * This method returns the sprite of the tile
     * @return The sprite
     */
    public BufferedImage getSprite() {
        return MenuScreen.unbreakableTileImage;
    }

}

