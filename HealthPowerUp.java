/**
 * HealthPowerUp
 * This class represents the power-up that gives +1 health
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */

import java.io.File;
import javax.imageio.ImageIO;

public class HealthPowerUp extends PowerUp {

    /**
     * HealthPowerUp
     * Constructor
     * @param tile The tile the power-up is on
     */
    public HealthPowerUp(Tile tile) {
        // Set up basics
        super(tile);

        // Load the sprite
        try {
            sprite = ImageIO.read(new File("pu_heart.png"));
        } catch (Exception e) {
            System.out.println("Unable to load health power-up sprite.");
        }
    }

}
