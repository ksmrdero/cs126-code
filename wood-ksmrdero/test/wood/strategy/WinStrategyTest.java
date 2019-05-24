package wood.strategy;

import org.junit.Before;
import org.junit.Test;
import wood.game.TurnAction;
import wood.item.WoodItem;
import wood.tiles.TileType;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WinStrategyTest {
    private WinStrategy redPlayer;
    private WinStrategy bluePlayer;

    @Before
    public void setUpPlayers() {
        redPlayer = new WinStrategy();
        bluePlayer = new WinStrategy();
        redPlayer.initialize(10, 5, 2000, new Point(0,0), true, null);
        bluePlayer.initialize(16, 5, 2000, new Point(15,15), false, null);
    }

    @Test
    public void playerWillMoveRightCorrectly() {
        redPlayer.currentPoint = new Point(5, 3);
        assertEquals(TurnAction.MOVE_RIGHT, redPlayer.moveToPoint(new Point(10, 5)));
    }

    @Test
    public void playerWillMoveLeftCorrectly() {
        redPlayer.currentPoint = new Point(2, 5);
        assertEquals(TurnAction.MOVE_LEFT, redPlayer.moveToPoint(new Point(1, 12)));
    }

    @Test
    public void playerWillMoveDownCorrectly() {
        redPlayer.currentPoint = new Point(5, 12);
        assertEquals(TurnAction.MOVE_DOWN, redPlayer.moveToPoint(new Point(5, 5)));
    }

    @Test
    public void playerWillMoveUpCorrectly() {
        redPlayer.currentPoint = new Point(8, 1);
        assertEquals(TurnAction.MOVE_UP, redPlayer.moveToPoint(new Point(8, 9)));
    }

    @Test
    public void getNextLocationOfSeedOfUniqueDistancesWithRedPlayer() {
        redPlayer.seedLocations = new HashMap<>();
        redPlayer.seedLocations.put(new Point(1,2), 8);
        redPlayer.seedLocations.put(new Point(3,4), 7);
        redPlayer.seedLocations.put(new Point(5,6), 4);
        assertEquals(new Point(5,6), redPlayer.getNextSeed());
    }

    @Test
    public void getNextLocationOfSeedOfUniqueDistancesWithBluePlayer() {
        bluePlayer.seedLocations = new HashMap<>();
        bluePlayer.seedLocations.put(new Point(12,9), 3);
        bluePlayer.seedLocations.put(new Point(1,1), 6);
        bluePlayer.seedLocations.put(new Point(4,6), 5);
        assertEquals(new Point(12,9), bluePlayer.getNextSeed());
    }

    @Test
    public void getNextLocationOfSeedOfSameDistancesWithRedPlayer() {
        redPlayer.seedLocations = new HashMap<>();
        redPlayer.seedLocations.put(new Point(1,2), 8);
        redPlayer.seedLocations.put(new Point(2,7), 10);
        redPlayer.seedLocations.put(new Point(1,1), 8);
        assertEquals(new Point(1,1), redPlayer.getNextSeed());
    }

    @Test
    public void getNextLocationOfSeedOfSameDistancesWithBluePlayer() {
        bluePlayer.seedLocations = new HashMap<>();
        bluePlayer.seedLocations.put(new Point(10,12), 2);
        bluePlayer.seedLocations.put(new Point(1,7), 2);
        bluePlayer.seedLocations.put(new Point(9,5), 6);
        assertEquals(new Point(10,12), bluePlayer.getNextSeed());
    }

    @Test
    public void getNextLocationOfTreeOfDifferentDistancesWithRedPlayer() {
        redPlayer.treeLocations = new HashMap<>();
        redPlayer.treeLocations.put(new Point(1,2), 8);
        redPlayer.treeLocations.put(new Point(2,7), 10);
        redPlayer.treeLocations.put(new Point(1,1), 5);
        assertEquals(new Point(1,1), redPlayer.findNextTree());
    }

    @Test
    public void getNextLocationOfTreeOfDifferentDistancesWithBluePlayer() {
        bluePlayer.treeLocations = new HashMap<>();
        bluePlayer.treeLocations.put(new Point(1,2), 8);
        bluePlayer.treeLocations.put(new Point(2,7), 10);
        bluePlayer.treeLocations.put(new Point(1,1), 5);
        assertEquals(new Point(1,1), bluePlayer.findNextTree());
    }

    @Test
    public void getNextLocationOfTreeOfSameDistancesWithRedPlayer() {
        redPlayer.treeLocations = new HashMap<>();
        redPlayer.treeLocations.put(new Point(6,4), 5);
        redPlayer.treeLocations.put(new Point(2,2), 4);
        redPlayer.treeLocations.put(new Point(7,7), 4);
        assertEquals(new Point(2,2), redPlayer.findNextTree());
    }

    @Test
    public void getNextLocationOfTreeOfSameDistancesWithBluePlayer() {
        bluePlayer.treeLocations = new HashMap<>();
        bluePlayer.treeLocations.put(new Point(6,4), 5);
        bluePlayer.treeLocations.put(new Point(2,2), 4);
        bluePlayer.treeLocations.put(new Point(7,7), 4);
        assertEquals(new Point(7,7), bluePlayer.findNextTree());
    }


    @Test
    public void playerWillDoNothingWhenAtGivenCoordinate() {
        redPlayer.currentPoint = new Point(5, 3);
        assertNull(redPlayer.moveToPoint(new Point(5, 3)));
    }

    @Test
    public void playerWillCutDownTreeIfStandingOnRightPoint() {
        redPlayer.currentPoint = new Point(5, 3);
        redPlayer.treeLocations = new HashMap<>();
        redPlayer.treeLocations.put(new Point(5,3), 0);
        assertEquals(TurnAction.CUT_TREE, redPlayer.cutNextTree());
    }

    @Test
    public void playerWillGoTowardsCenterIfNoTreesPresent() {
        redPlayer.currentPoint = new Point(5, 3);
        redPlayer.treeLocations = new HashMap<>();
        assertEquals(TurnAction.MOVE_UP, redPlayer.cutNextTree());
    }

    @Test
    public void playerWillGoTowardsHomeBaseIfCarryingEnoughTrees() {
        redPlayer.currentPoint = new Point(5, 3);
        redPlayer.treeLocations = new HashMap<>();
        redPlayer.currentItems.add(new WoodItem(4));
        redPlayer.currentItems.add(new WoodItem(4));
        redPlayer.currentItems.add(new WoodItem(4));
        redPlayer.currentItems.add(new WoodItem(4));
        assertEquals(TurnAction.MOVE_LEFT, redPlayer.cutNextTree());
    }

    @Test
    public void playerWillPickUpSeedIfStandingOnRightPoint() {
        bluePlayer.currentPoint = new Point(2, 9);
        bluePlayer.seedLocations = new HashMap<>();
        bluePlayer.seedLocations.put(new Point(2,9), 0);
        assertEquals(TurnAction.PICK_UP, bluePlayer.plantNextSeed());
    }

    @Test
    public void playerWillPlantSeedIfStandingOnRightPointAndAlreadyPickedUpSeed() {
        bluePlayer.currentPoint = new Point(2, 9);
        bluePlayer.seedLocations = new HashMap<>();
        bluePlayer.seedLocations.put(new Point(2,9), 0);
        bluePlayer.plantNextSeed();
        assertEquals(TurnAction.PLANT_SEED, bluePlayer.plantNextSeed());
    }

    @Test
    public void playerWillMoveCorrectlyWhenOpponentIsNearbyForRedPlayer() {
        redPlayer.currentPoint = new Point(5, 7);
        PlayerBoardView boardView = new PlayerBoardView(new TileType[10][10],
                redPlayer.currentPoint, new Point(4,7),20,10);
        assertEquals(TurnAction.MOVE_DOWN, redPlayer.considerOpponentAction(boardView));
    }

    @Test
    public void playerWillMoveCorrectlyWhenOpponentIsNearbyForBluePlayer() {
        bluePlayer.currentPoint = new Point(2, 9);
        PlayerBoardView boardView = new PlayerBoardView(new TileType[10][10],
                bluePlayer.currentPoint, new Point(2,8),20,10);
        assertEquals(TurnAction.MOVE_DOWN, bluePlayer.considerOpponentAction(boardView));
    }
}
