/**
 * Field
 * This class represents the field object, contains bombs, tiles, and players
 * @author Siyao Chen, Vicki Xu
 * @version 1.0
 * May 25, 2017
 */

import java.util.List;
import java.util.ArrayList;

public class Field {

    public Tile[][] tileArray; // Array of tiles
    private int[][] tilePositions; // Tile positions in the field

    public static final int TILE_SIZE = 27; // Number of pixels in square tile
    public static final int X_DISPLACEMENT = 91; // Offset from left
    public static final int Y_DISPLACEMENT = 50; // Offset from top
    public static final int FIELD_SIZE = 11; // Number of tiles in field

    public List<Bomb> bombs; // List of bombs

    public Player player1; // Player 1
    public Player player2; // Player2

    public List<PowerUp> powerUps; //

    /**
     * Field
     * Constructor
     * @param player1 PLayer 1 in the game
     * @param player2 Player 2 in the game
     */
    public Field(Player player1, Player player2) {
        // Declare field
        tileArray = new Tile[FIELD_SIZE][FIELD_SIZE];

        // Initialize the tilePosition array
        // 0 = basic, 1 = breakable, 2 = unbreakable
        tilePositions = new int[][]{{0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                                    {0, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0},
                                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                    {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1},
                                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                    {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1},
                                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                    {1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 1},
                                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                    {0, 2, 1, 2, 1, 2, 1, 2, 1, 2, 0},
                                    {0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0}};

        // Fill in tile array
        for (int i = 0; i < tileArray.length; i++) {
            for (int j = 0; j < tileArray[i].length; j++) {
                if (tilePositions[i][j] == 0) { // Basic tile
                    tileArray[i][j] = new BasicTile(j, i);
                } else if (tilePositions[i][j] == 1) { // Breakable tile
                    tileArray[i][j] = new BreakableTile(j, i);
                } else if (tilePositions[i][j] == 2) { // Unbreakable tile
                    tileArray[i][j] = new UnbreakableTile(j, i);
                }
            }
        }

        // Initialize list of bombs
        bombs = new ArrayList<Bomb>();

        // Add players
        this.player1 = player1;
        this.player2 = player2;

        // Initialize list of power-ups
        powerUps = new ArrayList<PowerUp>();
    }

    /**
     * destroyTile
     * This method replaces the given BreakableTile with a BasicTile
     * @param xPos The x-position of the tile
     * @param yPos The y-position of the tile
     */
    public void destroyTile(int xPos, int yPos) {
        // If the tile is breakable, replace with basic tile
        if (tileArray[yPos][xPos] instanceof BreakableTile) {
            tileArray[yPos][xPos] = new BasicTile(xPos, yPos);
        }
    }

    /**
     * addBomb
     * This method adds a given Bomb to the ArrayList of Bombs
     * @param bomb The Bomb to add to the ArrayList
     */
    public void addBomb(Bomb bomb) {
        bombs.add(bomb);
    }

    /**
     * removeBomb
     * This method removes a given Bomb from the ArrayList of Bombs
     * @param bomb The bomb to remove
     */
    public void removeBomb(Bomb bomb) {
        bombs.remove(bomb);
    }

    /**
     * checkPlayers
     * This method checks whether Players are killed by a Bomb at a tile and respawns them if they are killed
     * @param xPos The x-position of the explosion
     * @param yPos The y-position of the explosion
     */
    public void checkPlayers(int xPos, int yPos) {
        // Check player 1 in that tile
        if (xCoordinateToPos(player1.xCoordinate + Player.WIDTH / 2) == xPos && yCoordinateToPos(player1.yCoordinate + Player.HEIGHT / 2) == yPos && player1.immune == false) {
            player1.respawn();
        }

        // Check player 2 in that tile
        if (xCoordinateToPos(player2.xCoordinate + Player.WIDTH / 2) == xPos && yCoordinateToPos(player2.yCoordinate + Player.HEIGHT / 2) == yPos && player2.immune == false) {
            player2.respawn();
        }
    }

    /**
     * xCoordinateToPos
     * This method returns the xPos equivalent of xCoordinate
     * @param xCoordinate
     * @return xPos
     */
    public static int xCoordinateToPos(int xCoordinate) {
        return (xCoordinate - X_DISPLACEMENT) / TILE_SIZE;
    }

    /**
     * yCoordinateToPos
     * This method returns the yPos equivalent of xCoordinate
     * @param yCoordinate
     * @return yPos
     */
    public static int yCoordinateToPos(int yCoordinate) {
        return (yCoordinate - Y_DISPLACEMENT) / TILE_SIZE;
    }

    /**
     * xPosToCoordinate
     * This method returns the xCoordinate equivalent of xPos
     * @param xPos
     * @return xCoordinate
     */
    public static int xPosToCoordinate(int xPos) {
        return (xPos * 27) + X_DISPLACEMENT;
    }

    /**
     * yPosToCoordinate
     * This method returns the yCoordinate equivalent of yPos
     * @param yPos
     * @return yCoordinate
     */
    public static int yPosToCoordinate(int yPos) {
        return (yPos * 27) + Y_DISPLACEMENT;
    }

}
