package wood.competition; // This is the "competition" package

import wood.game.TurnAction;
import wood.item.InventoryItem;
import wood.strategy.PlayerBoardView;
import wood.strategy.WoodPlayerStrategy;
import wood.tiles.TileType;
import wood.util.DistanceUtilities;
// ^ These classes were provided to you, they do not need to be in the competition package

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
// ^ These classes are a part of Java, they also do not need to be in the competition package

/**
 * Because this class is in the competition package, it will be compiled and run in the competition.
 * You cannot put more than one WoodPlayerStrategy implementation in the competition package, so you must
 *  either delete or modify this class in order to submit your wood.strategy implementation
 */
public class WinStrategy implements WoodPlayerStrategy {
    /** The number of turns before the player starts gathering trees. */
    private final double gatherTurns = 125;

    private int inventorySize;
    private int boardLength;
    private boolean isRed;
    private Point homeBase;

    private HashMap<Point, Integer> seedLocations;
    private HashMap<Point, Integer> treeLocations;
    private ArrayList<InventoryItem> currentItems;
    private Point currentPoint;

    private boolean isRedTurn;
    private int turnNumber = 0;
    private boolean hasSeed = false;

    public WinStrategy() { }

    @Override
    public void initialize(int boardSize, int maxInventorySize, int winningScore, Point startTileLocation,
                           boolean isRedPlayer, Random random) {
        this.boardLength = boardSize;
        this.inventorySize = maxInventorySize;
        this.currentPoint = startTileLocation;
        this.isRed = isRedPlayer;

        currentItems = new ArrayList<>();

        if (isRed) {
            homeBase = new Point(0, 0);
        } else {
            homeBase = new Point(boardLength - 1, boardLength - 1);
        }
    }

    @Override
    public TurnAction getTurnAction(PlayerBoardView boardView, boolean isRedTurn) {
        turnNumber++;
        this.isRedTurn = isRedTurn;
        currentPoint = boardView.getYourLocation();
        addSeedAndTreeLocations(boardView);

        TurnAction considerOpponent = considerOpponentAction(boardView);
        if (considerOpponent != null) {
            return considerOpponent;
        }

        if (turnNumber > gatherTurns) {
            return cutNextTree();
        } else {
            return plantNextSeed();
        }
    }

    @Override
    public void receiveItem(InventoryItem itemReceived) {
        currentItems.add(itemReceived);
    }

    @Override
    public String getName() {
        return "dudu";
    }

    @Override
    public void endRound(int pointsScored, int opponentPointsScored) {
        // do nothing
    }

    /**
     * Cuts the nearest tree from the current position of the player, and returns home with it.
     * If a tree where the player intended to go is cut, it will simply move to the next nearest tree.
     * @return the action the player needs to take to cut the nearest tree.
     */
    private TurnAction cutNextTree() {
        int carryingCapacity = 3;

        Point nextTree = findNextTree();
        if (currentPoint.equals(nextTree)) {
            return TurnAction.CUT_TREE;
        }

        if (currentPoint.equals(homeBase)) {
            currentItems.clear();
        }

        if (currentItems.size() > carryingCapacity) {
            return moveToPoint(homeBase);
        }

        return moveToPoint(nextTree);
    }

    /**
     * Plants the nearest seed from the current position of the player.
     * @return the action the player needs to take to plant the nearest seed.
     */
    private TurnAction plantNextSeed() {
        Point nextSeed = getNextSeed();
        if (hasSeed) {
            hasSeed = false;
            return TurnAction.PLANT_SEED;
        }

        if (currentPoint.equals(nextSeed)) {
            hasSeed = true;
            return TurnAction.PICK_UP;
        }
        return moveToPoint(nextSeed);
    }

    /**
     * Considers the other opponent's actions.
     * @param boardView the current state of the board.
     * @return the move the player should take if the other player is encountered.
     */
    private TurnAction considerOpponentAction(PlayerBoardView boardView) {
        Point otherPlayerLocation = boardView.getOtherPlayerLocation();
        if (otherPlayerLocation == null) {
            return null;
        }

        int closeDistance = 2;
        int distance = DistanceUtilities.getManhattanDistance(otherPlayerLocation,currentPoint);
        if (isRed && distance < closeDistance) {
            if (!isRedTurn && otherPlayerLocation.x < currentPoint.x) {
                return TurnAction.MOVE_DOWN;
            } else if (!isRedTurn && otherPlayerLocation.y < currentPoint.y) {
                return TurnAction.MOVE_LEFT;
            } else if (!isRedTurn && otherPlayerLocation.y > currentPoint.y) {
                return TurnAction.MOVE_RIGHT;
            } else {
                return TurnAction.MOVE_UP;
            }
        }

        if (!isRed && distance < closeDistance) {
            if (isRedTurn && otherPlayerLocation.x > currentPoint.x) {
                return TurnAction.MOVE_UP;
            } else if (isRedTurn && otherPlayerLocation.y > currentPoint.y) {
                return TurnAction.MOVE_RIGHT;
            } else if (isRedTurn && otherPlayerLocation.y < currentPoint.y) {
                return TurnAction.MOVE_LEFT;
            } else {
                return TurnAction.MOVE_DOWN;
            }
        }

        return null;
    }

    /**
     * Moves the player in whichever direction of the new point.
     * @param newPoint the point the player needs to go to.
     * @return the action the player would take to go to a new point.
     */
    private TurnAction moveToPoint(Point newPoint) {
        if (currentPoint.x < newPoint.x) {
            return TurnAction.MOVE_RIGHT;
        } else if (currentPoint.x > newPoint.x) {
            return TurnAction.MOVE_LEFT;
        } else if (currentPoint.y > newPoint.y) {
            return TurnAction.MOVE_DOWN;
        } else if (currentPoint.y < newPoint.y) {
            return TurnAction.MOVE_UP;
        }

        return null;
    }

    /**
     * Finds the closest tree from the current position of the player.
     * @return the point of the nearest tree from the player.
     */
    private Point findNextTree() {
        HashMap.Entry<Point, Integer> minEntry = null;
        for (HashMap.Entry<Point, Integer> entry : treeLocations.entrySet()) {
            if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                minEntry = entry;
            } else if (entry.getValue().compareTo(minEntry.getValue()) == 0) {
                int homeDistance = DistanceUtilities.getManhattanDistance(minEntry.getKey(), homeBase);
                int newDistance = DistanceUtilities.getManhattanDistance(entry.getKey(), homeBase);
                if (newDistance < homeDistance) {
                    minEntry = entry;
                }
            }
        }

        if (minEntry == null) {
            return new Point(boardLength / 2, boardLength / 2);
        }

        return minEntry.getKey();
    }

    /**
     * Finds the nearest seed from the current position of the player.
     * @return the point at which the nearest seed is located.
     */
    private Point getNextSeed() {
        if (currentItems.size() == inventorySize - 1) {
            int minValue = boardLength * 2;
            Point minPoint = new Point(boardLength / 2, boardLength / 2);

            Set<Point> seedPoints = seedLocations.keySet();
            for (Point seedPoint : seedPoints) {
                int currentDistance = DistanceUtilities.getManhattanDistance(currentPoint, seedPoint);
                int currentHomeDistance = DistanceUtilities.getManhattanDistance(currentPoint, homeBase);
                int seedHomeDistance = DistanceUtilities.getManhattanDistance(seedPoint, homeBase);

                int totalValue = currentDistance - (currentHomeDistance - seedHomeDistance);
                if (totalValue < minValue) {
                    minValue = totalValue;
                    minPoint = seedPoint;
                }
            }

            return minPoint;
        }

        HashMap.Entry<Point, Integer> minEntry = null;
        for (HashMap.Entry<Point, Integer> entry : seedLocations.entrySet()) {
            if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                minEntry = entry;
            } else if (entry.getValue().compareTo(minEntry.getValue()) == 0) {
                int homeDistance = DistanceUtilities.getManhattanDistance(minEntry.getKey(), homeBase);
                int newDistance = DistanceUtilities.getManhattanDistance(entry.getKey(), homeBase);
                if (newDistance < homeDistance) {
                    minEntry = entry;
                }
            }
        }

        if (minEntry == null) {
            return new Point(boardLength / 2, boardLength / 2);
        }

        return minEntry.getKey();
    }

    /**
     * Adds all the positions of the seeds and tree in the current board state.
     * @param boardView the current board state of the game.
     */
    private void addSeedAndTreeLocations(PlayerBoardView boardView) {
        TileType tileType;
        seedLocations = new HashMap<>();
        treeLocations = new HashMap<>();
        for (int x = 0; x < boardLength; x++) {
            for (int y = 0; y < boardLength; y++) {
                tileType = boardView.getTileTypeAtLocation(x, y);
                if (tileType == TileType.TREE) {
                    treeLocations.put(new Point(x, y),
                            DistanceUtilities.getManhattanDistance(currentPoint, new Point(x,y)));
                }

                if (tileType == TileType.SEED) {
                    seedLocations.put(new Point(x, y),
                            DistanceUtilities.getManhattanDistance(currentPoint, new Point(x,y)));
                }
            }
        }
    }
}
