package wood.strategy;

import wood.game.TurnAction;
import wood.item.InventoryItem;

import java.awt.*;
import java.util.Random;

public interface WoodPlayerStrategy {

    /**
     * Called at the start of every round
     *
     * @param boardSize The length and width of the square game board
     * @param maxInventorySize The maximum number of items that your player can carry at one time
     * @param winningScore The first player to reach this score wins the round
     * @param startTileLocation A Point representing your starting location in (x, y) coordinates
     *                          (0, 0) is the bottom left and (boardSize - 1, boardSize - 1) is the top right
     * @param isRedPlayer True if this wood.strategy is the red player, false otherwise
     * @param random A random number generator, if your wood.strategy needs random numbers you should use this.
     */
    void initialize(int boardSize, int maxInventorySize, int winningScore,
                    Point startTileLocation, boolean isRedPlayer, Random random);

    /**
     * The main part of your wood.strategy, this method returns what action your player should do on this turn
     *
     * @param boardView A PlayerBoardView object representing all the information about the board and the other player
     *                  that your wood.strategy is allowed to access
     * @param isRedTurn For use when two players attempt to move to the same spot on the same turn
     *                  If true: The red player will move to the spot, and the blue player will do nothing
     *                  If false: The blue player will move to the spot, and the red player will do nothing
     * @return The TurnAction that this wood.strategy wants to perform on this game turn
     */
    TurnAction getTurnAction(PlayerBoardView boardView, boolean isRedTurn);

    /**
     * Called when the player receives an item from performing a TurnAction that gives an item.
     * At the moment this is either picking up a seed or chopping wood
     *
     * @param itemReceived The item received from the player's TurnAction on their last turn
     */
    void receiveItem(InventoryItem itemReceived);

    /**
     * Gets the name of this wood.strategy. The amount of characters that can actually be displayed on a screen varies,
     * although by default at screen size 750 it's about 16-20 characters depending on character size
     *
     * @return The name of your wood.strategy for use in the competition and rendering the scoreboard on the GUI
     */
    String getName();

    /**
     * Called at the end of every round to let players reset, and tell them how they did if the wood.strategy does not
     *  track that for itself
     *
     * @param pointsScored The total number of points this wood.strategy scored
     * @param opponentPointsScored The total number of points the opponent's wood.strategy scored
     */
    void endRound(int pointsScored, int opponentPointsScored);
}
