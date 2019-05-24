//package wood.strategy;
//
//import wood.tiles.TileType;
//import wood.util.DistanceUtilities;
//
//import java.awt.*;
//import java.util.HashMap;
//import java.util.Set;
//
//public class Location {
//    private boolean isRed;
//    private Point homeBase;
//    private int boardLength;
//    private int inventorySize;
//
//    HashMap<Point, Integer> seedLocations;
//    HashMap<Point, Integer> treeLocations;
//
//    Location() {
//
//    }
//
//    Point findNextTree() {
//        HashMap.Entry<Point, Integer> minEntry = null;
//        for (HashMap.Entry<Point, Integer> entry : treeLocations.entrySet()) {
//            if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
//                minEntry = entry;
//            } else if (entry.getValue().compareTo(minEntry.getValue()) == 0) {
//                int homeDistance = DistanceUtilities.getManhattanDistance(minEntry.getKey(), homeBase);
//                int newDistance = DistanceUtilities.getManhattanDistance(entry.getKey(), homeBase);
//                if (newDistance < homeDistance) {
//                    minEntry = entry;
//                }
//            }
//        }
//
//        if (minEntry == null) {
//            return new Point(boardLength / 2, boardLength / 2);
//        }
//
//        return minEntry.getKey();
//    }
//
//    Point getNextSeed() {
//        if (currentItems.size() == inventorySize - 1) {
//            int minValue = boardLength * 2;
//            Point minPoint = new Point(boardLength / 2, boardLength / 2);
//
//            Set<Point> seedPoints = seedLocations.keySet();
//            for (Point seedPoint : seedPoints) {
//                int currentDistance = DistanceUtilities.getManhattanDistance(currentPoint, seedPoint);
//                int currentHomeDistance = DistanceUtilities.getManhattanDistance(currentPoint, homeBase);
//                int seedHomeDistance = DistanceUtilities.getManhattanDistance(seedPoint, homeBase);
//
//                int totalValue = currentDistance - (currentHomeDistance - seedHomeDistance);
//                if (totalValue < minValue) {
//                    minValue = totalValue;
//                    minPoint = seedPoint;
//                }
//            }
//
//            return minPoint;
//        }
//
//        HashMap.Entry<Point, Integer> minEntry = null;
//        for (HashMap.Entry<Point, Integer> entry : seedLocations.entrySet()) {
//            if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0) {
//                minEntry = entry;
//            } else if (entry.getValue().compareTo(minEntry.getValue()) == 0) {
//                int homeDistance = DistanceUtilities.getManhattanDistance(minEntry.getKey(), homeBase);
//                int newDistance = DistanceUtilities.getManhattanDistance(entry.getKey(), homeBase);
//                if (newDistance < homeDistance) {
//                    minEntry = entry;
//                }
//            }
//        }
//
//        if (minEntry == null) {
//            return new Point(boardLength / 2, boardLength / 2);
//        }
//
//        return minEntry.getKey();
//    }
//
//    private void addSeedAndTreeLocations(PlayerBoardView boardView) {
//        TileType tileType;
//        seedLocations = new HashMap<>();
//        treeLocations = new HashMap<>();
//        for (int x = 0; x < boardLength; x++) {
//            for (int y = 0; y < boardLength; y++) {
//                tileType = boardView.getTileTypeAtLocation(x, y);
//                if (tileType == TileType.TREE) {
//                    treeLocations.put(new Point(x, y),
//                            DistanceUtilities.getManhattanDistance(currentPoint, new Point(x,y)));
//                }
//
//                if (tileType == TileType.SEED) {
//                    seedLocations.put(new Point(x, y),
//                            DistanceUtilities.getManhattanDistance(currentPoint, new Point(x,y)));
//                }
//            }
//        }
//    }
//}
