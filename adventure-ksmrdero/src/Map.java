/**
 * Parse JSON data.
 * Written by James Wei on 2/4/19.
 */
class Map {
    private Room[] rooms;
    private String startingRoom;
    private String endingRoom;
    private Player player;

    /**
     * Getter for the player.
     * @return the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Getter for the rooms array.
     * @return the rooms array.
     */
    Room[] getRooms() {
        return rooms;
    }

    /**
     * Getter for the starting room.
     * @return the starting room.
     */
    String getStartingRoom() {
        return startingRoom;
    }

    /**
     * Getter for the ending room.
     * @return the ending room.
     */
    String getEndingRoom() {
        return endingRoom;
    }
}
