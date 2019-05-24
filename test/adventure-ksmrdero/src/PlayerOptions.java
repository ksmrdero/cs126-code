import java.util.ArrayList;

/**
 * Class describing the current player position in the mystery adventure.
 * Written by James Wei on 2/19/2019.
 */
class PlayerOptions {
    private Map mapData;
    ArrayList<String> playerItems = new ArrayList<>();

    /**
     * The constructor for the player option class.
     * @param data the parsed JSON data.
     */
    PlayerOptions(Map data) {
        mapData = data;
    }

    /**
     * Gets the location description of the current location.
     * @param location the current location.
     * @return the description of the current location.
     */
    String getLocationDescription(String location) {
        for (int i = 0; i < mapData.getRooms().length; i++) {
            if (mapData.getRooms()[i].getName().equals(location)) {
                return mapData.getRooms()[i].getDescription();
            }
        }

        return null;
    }

    /**
     * Gets all the available items in a location.
     * @param location the current location.
     * @return all of the available items a user can pick up.
     */
    String getAvailableItems(String location) {
        int locationIndex = getLocationIndex(location);
        if (locationIndex == -1) {
            return null;
        }

        Item[] availableItems = mapData.getRooms()[locationIndex].getItems();
        if (availableItems.length == 0) {
            return null;
        }

        StringBuilder itemList = new StringBuilder();
        for (int directionIndex = 0; directionIndex < availableItems.length - 1; directionIndex++) {
            itemList.append(availableItems[directionIndex].getName()).append(", ");
        }

        itemList.append(availableItems[availableItems.length - 1].getName());
        return itemList.toString();
    }

    /**
     * Returns all possible directions from a location as a string.
     * @param location the current location.
     * @return the possible directions.
     */
    String getPossibleDirections(String location) {
        StringBuilder directionOutput = new StringBuilder();

        int locationIndex = getLocationIndex(location);
        if (locationIndex == -1) {
            return null;
        }

        boolean shouldShow = false;

        Direction[] possibleDirections = mapData.getRooms()[locationIndex].getDirections();

        ArrayList<Direction> visibleDirections = new ArrayList<>();
        for (Direction direction : possibleDirections) {
            String[] validKeys = direction.getValidKeyNames();

            for (String item : playerItems) {
                if (checkStringContains(validKeys, item)) {
                    shouldShow = true;
                }
            }

            if (!direction.isHidden() || shouldShow) {
                visibleDirections.add(direction);
            }
        }

        for (int directionIndex = 0; directionIndex < visibleDirections.size() - 1; directionIndex++) {
            directionOutput.append(visibleDirections.get(directionIndex).getDirectionName()).append(", ");
        }

        directionOutput.append(visibleDirections.get(visibleDirections.size() - 1).getDirectionName());

        return directionOutput.toString();
    }

    /**
     * Finds the next location given a location and direction, else return null.
     * @param location the current location of the user.
     * @param direction the specified direction.
     * @return the next location as a string.
     */
    String getNextLocation(String location, String direction) {
        for (int i = 0; i < mapData.getRooms().length; i++) {
            if (mapData.getRooms()[i].getName().equals(location)) {
                Direction[] possibleDirections = mapData.getRooms()[i].getDirections();

                for (Direction givenDirection : possibleDirections) {
                    if (givenDirection.getDirectionName().toLowerCase().equals(direction.toLowerCase())) {
                        return givenDirection.getRoom();
                    }
                }
            }
        }

        return null;
    }

    /**
     * Determines whether player can enter a room with a key.
     * @param location the current location.
     * @param direction the direction user specifies.
     * @param key the key the user specifies.
     * @return true if user can enter the disabled room.
     */
    boolean canEnterDisabledRoom(String location, String direction, String key) {
        int locationIndex = getLocationIndex(location);
        if (locationIndex == -1 || key == null) {
            return false;
        }

        int directionIndex = -1;

        for (int i = 0; i < mapData.getRooms()[locationIndex].getDirections().length; i++) {
            String givenDirection = mapData.getRooms()[locationIndex].getDirections()[i].getDirectionName();
            if (givenDirection.equals(direction.toLowerCase())) {
                directionIndex = i;
            }
        }

        if (directionIndex == -1) {
            return false;
        }

        String[] validKeys =
                mapData.getRooms()[locationIndex].getDirections()[directionIndex].getValidKeyNames();
        return checkStringContains(validKeys, key);
    }

    /**
     * Gets the enabled boolean given location.
     * @param Location the current location.
     * @param direction the direction specified.
     * @return true if enabled is true in JSON.
     */
    boolean getEnabledDescription(String Location, String direction) {
        int locationIndex = getLocationIndex(Location);
        if (locationIndex == -1) {
            return false;
        }

        int directionIndex = -1;

        for (int i = 0; i < mapData.getRooms()[locationIndex].getDirections().length; i++) {
            String givenDirection = mapData.getRooms()[locationIndex].getDirections()[i].getDirectionName();
            if (givenDirection.equals(direction.toLowerCase())) {
                directionIndex = i;
            }
        }

        if (directionIndex == -1) {
            return false;
        }

        String enabled = mapData.getRooms()[locationIndex].getDirections()[directionIndex].getEnabled();
        return enabled.equals("true");
    }

    /**
     * Checks if a string is in a string array.
     * @param values the string array.
     * @param valueToCheck the string you want to check.
     * @return whether or not string is contained in array.
     */
    boolean checkStringContains(String[] values, String valueToCheck) {
        if (values == null) {
            return false;
        }

        boolean contains = false;
        for (String value : values) {
            if (value.equals(valueToCheck)) {
                contains = true;
                break;
            }
        }

        return contains;
    }

    private int getLocationIndex(String location) {
        int locationIndex = -1;
        for (int index = 0; index < mapData.getRooms().length; index++) {
            if (mapData.getRooms()[index].getName().equals(location)) {
                locationIndex = index;
            }
        }

        return locationIndex;
    }
}
