/**
 * Class containing individual room data.
 * Written by James Wei on 2/4/19.
 */
class Room {
    private String name;
    private String description;
    private Direction[] directions;
    private Item[] items;

    /**
     * Getter for the items the player has.
     * @return the array of items the player has.
     */
    Item[] getItems() {
        return items;
    }

    /**
     * Getter for the name.
     * @return the name of the location.
     */
    String getName() {
        return name;
    }

    /**
     * Getter for the description.
     * @return the description of the location.
     */
    String getDescription() {
        return description;
    }

    /**
     * Getter for the direction.
     * @return the directions of the location.
     */
    Direction[] getDirections() {
        return directions;
    }
}
