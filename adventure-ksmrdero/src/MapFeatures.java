import java.util.HashSet;
import java.util.Set;

public class MapFeatures {
    /**
     * Checks if you can get from the starting location to the ending location.
     * @param data the map data.
     * @return boolean on whether you can get from startPoint to endPoint given the map data.
     */
    static boolean checkValidMap(Map data) {
        if (data == null) {
            return false;
        }
        String startPoint = data.getStartingRoom();
        String endPoint = data.getEndingRoom();
        Set<String> allLocations = new HashSet<>();
        allLocations.add(startPoint);
        while (!allLocations.contains(endPoint)) {
            int previousCount = allLocations.size();
            // declare a new set to contain all the new locations
            Set<String> newLocations = new HashSet<>();
            // iterates through all locations and grabs all possible locations they could to from there.
            for (String location : allLocations) {
                int index = getLocationIndex(data, location);
                if (index == -1) {
                    break; // location not in array
                }
                // grabbing all potential new locations, and adding it to the set of new locations.
                for (Direction direction : data.getRooms()[index].getDirections()) {
                    newLocations.add(direction.getRoom());
                }
            }
            // add all new locations to the set of all locations
            allLocations.addAll(newLocations);

            int currentCount = allLocations.size();
            // if no new locations have been added
            if (currentCount == previousCount) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the index of a given location in Rooms[].
     * Helper method for checkValidMap.
     * @param data the Map data.
     * @param location the location you want to find.
     * @return the index of where that location is in Rooms[].
     */
    static int getLocationIndex(Map data, String location) {
        for (int index = 0; index < data.getRooms().length; index++) {
            if (data.getRooms()[index].getName().equals(location)) {
                return index;
            }
        }
        return -1;
    }
}
