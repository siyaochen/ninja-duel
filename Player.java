/**
 * Player
 * This class represents the player object in the game, and can move around, place bombs, and die.
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 23, 2017
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;

public class Player {

    public int xCoordinate, yCoordinate; // Coordinates of player
    public int xSpeed, ySpeed; // Speed of player

    public BufferedImage[] sprites; // Sprites of player
    public int spriteIndex; // To provide variety for the sprites
    public static int HEIGHT = 29, WIDTH = 27; // Size of player

    public int numLives; // Number of lives left
    public int playerNum; // Player number
    public long lastPlacedBomb; // Time last placed bomb

    public boolean immune; // Can't be killed by bombs
    public int explodeStrength; // Explosion strength

    /**
     * Player
     * Constructor
     * @param playerNum The first or second player in the game
     */
    public Player(int playerNum) {
        if (playerNum == 1) {
            // Set x,y to top left corner
            xCoordinate = Field.X_DISPLACEMENT;
            yCoordinate = Field.Y_DISPLACEMENT;

            // Set sprite to white and blue
        } else if (playerNum == 2) {
            // Set x,y to bottom right corner
            xCoordinate = Field.X_DISPLACEMENT + (Field.FIELD_SIZE - 1) * Field.TILE_SIZE;
            yCoordinate = Field.Y_DISPLACEMENT + (Field.FIELD_SIZE - 1) * Field.TILE_SIZE;

            // Set sprite to black and red
        }

        // Set number of lives
        numLives = 3;

        // Set player number
        this.playerNum = playerNum;

        // Set bomb counter
        lastPlacedBomb = 0;

        // Set not immune
        immune = false;

        // Set explode strength
        explodeStrength = 1;
    }

    /** 
     * hitTile
     * This method returns if the player is going to hit the tile or not
     * @param field The field player exists in
     * @return boolean If it's going to hit tile
     */
    public boolean hitTile(int dir, Field field) {
        Tile[][] tileArray = field.tileArray;

        // If checking x-direction
        if (dir == 1) {
            if (xSpeed < 0 && Field.xCoordinateToPos(xCoordinate + xSpeed) >= 0 && Field.xCoordinateToPos(xCoordinate + xSpeed) <= 9 && Field.xCoordinateToPos(xCoordinate + xSpeed + 26) <= 10) {
                // Check for power-ups
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp instanceof HealthPowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp instanceof HealthPowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)].powerUp = null;
                }

                // Return whether there's a collision
                return !(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)] instanceof BasicTile
                        && tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + 5)] instanceof BasicTile);
            } else if (xSpeed > 0 && Field.xCoordinateToPos(xCoordinate + xSpeed + 5) >= 0 && Field.xCoordinateToPos(xCoordinate + xSpeed - 5) <= 9) {
                // Check for power-ups
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp instanceof HealthPowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp instanceof HealthPowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)].powerUp = null;
                }

                // Return whether there's a collision
                return !(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)] instanceof BasicTile
                        && tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 26)][Field.xCoordinateToPos(xCoordinate + xSpeed + WIDTH - 5)] instanceof BasicTile);
            }
        } else { // Checking y-direction
            if (ySpeed < 0 && Field.yCoordinateToPos(yCoordinate + ySpeed) >= 0 && Field.yCoordinateToPos(yCoordinate + ySpeed + 8) <= 9 && Field.xCoordinateToPos(xCoordinate + xSpeed + 26) <= 10) {
                // Check for power-ups
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp instanceof HealthPowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp instanceof HealthPowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp = null;
                }

                // Return whether there's a collision
                return !(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)] instanceof BasicTile
                        && tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + 8)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)] instanceof BasicTile);
            } else if (ySpeed > 0 && Field.yCoordinateToPos(yCoordinate + ySpeed) >= 0 && Field.yCoordinateToPos(yCoordinate + ySpeed) <= 9 && Field.xCoordinateToPos(xCoordinate + xSpeed + 26) <= 10) {
                // Check for power-ups
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp instanceof ExplodePowerUp) {
                    explodeStrength = 11;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp instanceof ExplodePowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)].powerUp = null;
                }
                if (tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp instanceof ExplodePowerUp) {
                    numLives++;
                    field.powerUps.remove(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp);
                    tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)].powerUp = null;
                }

                // Return whether there's a collision
                return !(tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 8)] instanceof BasicTile
                        && tileArray[Field.yCoordinateToPos(yCoordinate + ySpeed + HEIGHT - 2)][Field.xCoordinateToPos(xCoordinate + xSpeed + 26 - 8)] instanceof BasicTile);
            }
        }
        // Default return
        return false;
    }

    /**
     * move
     * This method moves the player in its direction
     * @param field The field player is moving in
     */
    public void move(Field field) {
        // Check if player is within boundaries
        if ((xCoordinate + xSpeed >= Field.X_DISPLACEMENT) &&
                (xCoordinate + xSpeed + WIDTH <= Field.X_DISPLACEMENT + Field.FIELD_SIZE * Field.TILE_SIZE) &&
                (yCoordinate + ySpeed >= Field.Y_DISPLACEMENT) &&
                (yCoordinate + ySpeed + HEIGHT - 2 <= Field.Y_DISPLACEMENT + Field.FIELD_SIZE * Field.TILE_SIZE)) {

            // Check for hitting tile in x-direction
            if (!hitTile(1, field)) {
                // Move in x-direction
                xCoordinate += xSpeed;
            }
            // Check for hitting tile in y-direction
            if (!hitTile(2, field)) {
                // Move in y-direction
                yCoordinate += ySpeed;
            }
        }
    }

    /**
     * placeBomb
     * This method creates a Bomb object and places it at the player's position
     * @param field The field the player and bomb is in
     */
    public void placeBomb(Field field) {
        // Check if placing bomb is possible
        if (System.currentTimeMillis() > lastPlacedBomb + 3000) {
            // Instantiate bomb
            Bomb bomb = new Bomb(this);

            // Add bomb to ArrayList in the field
            field.addBomb(bomb);

            // Update last placed bomb time
            lastPlacedBomb = System.currentTimeMillis();

            // Reset explosion strength
            explodeStrength = 1;
        }
    }

    /**
     * respawn
     * This method subtracts one life and places the player in original spot
     */
    public void respawn() {
        numLives--;

        if (numLives > 0) {
            if (playerNum == 1) {
                // Place player in top left corner
                xCoordinate = Field.X_DISPLACEMENT;
                yCoordinate = Field.Y_DISPLACEMENT;
            } else if (playerNum == 2) {
                // Place player in bottom right corner
                xCoordinate = Field.X_DISPLACEMENT + (Field.FIELD_SIZE - 1) * Field.TILE_SIZE;
                yCoordinate = Field.Y_DISPLACEMENT + (Field.FIELD_SIZE - 1) * Field.TILE_SIZE;
            }

            // Make the player temporarily immune
            immune = true;
        }
    }

}