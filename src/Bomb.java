/**
 * Bomb
 * This class represents the bomb, has a timer, and can explode
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 23, 2017
 */

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class Bomb {

    private long placedTime; // Time bomb is placed in millis
    private long explodeTime; // Time the bomb is to explode in millis

    private int explodeStrength; // Number of grids of explosion

    static BufferedImage bombSprite; // Sprites of bomb
    static BufferedImage[] explosionSprites; // Sprites of explosions

    public int HEIGHT = 27, WIDTH = 27; // Size of bomb
    public int xPos, yPos; // Coordinates of bomb

    private Player player; // Player that placed the bomb

    static final File DIR = new File("c:\\temp\\java\\bombs_and_explosions");

    /**
     * Bomb
     * Constructor
     * @param player The player who placed the bomb
     */
    public Bomb(Player player) {
        this.player = player;

        // Set xPosition and yPosition based on player's position
        xPos = Field.xCoordinateToPos(player.xCoordinate + player.WIDTH / 2);
        yPos = Field.yCoordinateToPos(player.yCoordinate + player.HEIGHT / 2);

        // Set up time
        placedTime = System.currentTimeMillis();
        explodeTime = placedTime + 3000;

        // Set up bomb sprite
        try {
            bombSprite = ImageIO.read(new File("bombs_and_explosions/bomb.png"));
        } catch (Exception e) {
            System.out.println("Error loading sprite: bomb.png");
        }

        // Set up explosion sprites
        explosionSprites = new BufferedImage[7];
        for (int i = 0; i < 7; i++) {
            try {
                explosionSprites[i] = ImageIO.read(new File("bombs_and_explosions/explosion" + (i + 1) + ".png"));
            } catch (Exception e) {
                System.out.println("Error loading sprite: explosion" + (i + 1) + ".png");
            }
        }

        // Set up explosion strength based on player's power-up
        explodeStrength = player.explodeStrength;
    }

    /**
     * checkExplode
     * This method checks if the bomb should explode, if so, explodes
     * @return Whether the bomb explodes
     */
    public boolean checkExplode() {
        // Check for explosion
        if (System.currentTimeMillis() > explodeTime) {
            return true;
        }
        return false;
    }

    /**
     * explode
     * This method destroys the bomb and surrounding tiles/player, and returns an arraylist of the position
     * @param field The field that the bomb is in
     * @return An ArrayList of coordinates, where each coordinate is equal to (row * 11) + col
     */
    public List<Integer> explode(Field field) {
        // Declare ArrayList to return
        List<Integer> explosionPos = new ArrayList<Integer>();

        // Keep track of whether the explosion continues in that direction
        boolean negXDirection = true;
        boolean negYDirection = true;
        boolean posXDirection = true;
        boolean posYDirection = true;

        // Add bomb's square to the ArrayList of coordinates
        explosionPos.add((yPos * Field.FIELD_SIZE) + xPos);

        // Check for player collision on xPosition, yPosition
        field.checkPlayers(xPos, yPos);

        // Check tiles and players around xPosition, yPosition
        for (int i = 1; i <= explodeStrength; i++) {
            // Check left of bomb, stop if explosion reaches edge
            if (xPos - i >= 0 && negXDirection) {
                // Destroy if breakable tile, continue if basic tile, stop if unbreakable tilewa
                if (field.tileArray[yPos][xPos - i] instanceof BreakableTile) {
                    field.destroyTile(xPos - i, yPos);

                    // Explosion doesn't continue in this direction
                    negXDirection = false;

                    // Add square to explosion coordinates
                    explosionPos.add((yPos * Field.FIELD_SIZE) + xPos - i);

                } else if (field.tileArray[yPos][xPos - i] instanceof BasicTile) {
                    // Check for player collision
                    field.checkPlayers(xPos - i, yPos);

                    // Add square to explosion coordinates
                    explosionPos.add((yPos * Field.FIELD_SIZE) + xPos - i);

                } else {
                    negXDirection = false;
                }
            } else {
                negXDirection = false;
            }

            // Check right of bomb, stop if explosion reaches edge
            if (xPos + i < Field.FIELD_SIZE && posXDirection) {
                // Destroy if breakable tile, continue if basic tile, stop if unbreakable tile
                System.out.println(xPos + i);
                if (field.tileArray[yPos][xPos + i] instanceof BreakableTile) {
                    field.destroyTile(xPos + i, yPos);

                    // Explosion doesn't continue in this direction
                    posXDirection = false;

                    // Add square to explosion coordinates
                    explosionPos.add((yPos * Field.FIELD_SIZE) + xPos + i);

                } else if (field.tileArray[yPos][xPos + i] instanceof BasicTile) {
                    // Check for player collision
                    field.checkPlayers(xPos + i, yPos);

                    // Add square to explosion coordinates
                    explosionPos.add((yPos * Field.FIELD_SIZE) + xPos + i);

                } else {
                    posXDirection = false;
                }
            } else {
                posXDirection = false;
            }

            // Check upwards of bomb, stop if explosion reaches edge
            if (yPos - i >= 0 && negYDirection) {
                // Destroy if breakable tile, continue if basic tile, stop if unbreakable tile
                if (field.tileArray[yPos - i][xPos] instanceof BreakableTile) {
                    field.destroyTile(xPos, yPos - i);

                    // Explosion doesn't continue in this direction
                    negYDirection = false;

                    // Add square to explosion coordinates
                    explosionPos.add(((yPos - i) * Field.FIELD_SIZE) + xPos);

                } else if (field.tileArray[yPos - i][xPos] instanceof BasicTile) {
                    // Check for player collision
                    field.checkPlayers(xPos, yPos - i);

                    // Add square to explosion coordinates
                    explosionPos.add(((yPos - i) * Field.FIELD_SIZE) + xPos);

                } else {
                    negYDirection = false;
                }
            } else {
                negYDirection = false;
            }

            // Check downwards of bomb, stop if explosion reaches edge
            if (yPos + i < Field.FIELD_SIZE && posYDirection) {
                // Destroy if breakable tile, continue if basic tile, stop if unbreakable tile
                if (field.tileArray[yPos + i][xPos] instanceof BreakableTile) {
                    field.destroyTile(xPos, yPos + i);

                    // Explosion doesn't continue in this direction
                    posYDirection = false;

                    // Add square to explosion coordinates
                    explosionPos.add(((yPos + i) * Field.FIELD_SIZE) + xPos);

                } else if (field.tileArray[yPos + i][xPos] instanceof BasicTile) {
                    // Check for player collision
                    field.checkPlayers(xPos, yPos + i);

                    // Add square to explosion coordinates
                    explosionPos.add(((yPos + i) * Field.FIELD_SIZE) + xPos);

                } else {
                    posYDirection = false;
                }
            } else {
                posYDirection = false;
            }
        }

        // Destroy the bomb
        field.removeBomb(this);

        // return ArrayList
        return explosionPos;
    }

    /**
     * getExplosionSpriteIndex
     * This method returns the appropriate index for an explosion sprite
     * @param posArray The positions of the explosions
     * @param index The index of the explosion position in posArray
     * @return The index of the explosion sprite
     */
    public static int getExplosionSpriteIndex(List<Integer> posArray, int index) {
        // Set default index of image
        int imgIndex = 0;

        // If it is the explosion centre, use index 1
        if (index == 0) {
            imgIndex = 0;
        } else if (posArray.get(index) % 11 == posArray.get(0) % 11) { // Same column
            imgIndex = 1;

            // Assume both are true
            boolean isHighest = true;
            boolean isLowest = true;

            // Check highest and lowest versus the other coordinates
            for (int i = 0; i < posArray.size(); i++) {
                // If in the same column
                if (posArray.get(i) % 11 == posArray.get(index) % 11) {
                    // Check for highest
                    if (posArray.get(i) < posArray.get(index)) {
                        isHighest = false;
                    }

                    // Check for lowest
                    if (posArray.get(i) > posArray.get(index)) {
                        isLowest = false;
                    }
                }
            }

            // Set highest or lowest sprites
            if (isHighest) {
                imgIndex = 3;
            } else if (isLowest) {
                imgIndex = 4;
            }
        } else if (posArray.get(index) / 11 == posArray.get(0) / 11) { // Same row
            imgIndex = 2;

            // Assume true at first
            boolean isLeftest = true;
            boolean isRightest = true;

            // Check for leftest and rightest among other coordinates
            for (int i = 0; i < posArray.size(); i++) {
                // If in the same row
                if (posArray.get(i) / 11 == posArray.get(index) / 11) {
                    // Check for most right
                    if (posArray.get(i) > posArray.get(index)) {
                        isRightest = false;
                    }

                    // Check for most left
                    if (posArray.get(i) < posArray.get(index)) {
                        isLeftest = false;
                    }
                }
            }

            // Set highest or lowest sprite
            if (isRightest) {
                imgIndex = 5;
            } else if (isLeftest) {
                imgIndex = 6;
            }
        }

        // Return the index of the sprite
        return imgIndex;
    }

}