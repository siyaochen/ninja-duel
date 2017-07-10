/**
 * ExplodePowerUp
 * This class represents the power-up that gives more power in the explosion
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */

import java.io.File;
import javax.imageio.ImageIO;

public class ExplodePowerUp extends PowerUp {

    /**
     * ExplodePowerUp
     * Constructor
     * @param tile The tile the power-up is on
     */
    public ExplodePowerUp(Tile tile) {
        // Set up basic stuff
        super(tile);

        // Load the sprite
        try {
            sprite = ImageIO.read(new File("pu_strength.png"));
        } catch (Exception e) {
            System.out.println("Unable to load health power-up sprite.");
        }
    }

}
