/**
 * Class containing direction information.
 * Written by James Wei on 2/4/19.
 */
class Direction {
    private String directionName;
    private String room;
    private String enabled;
    private String[] validKeyNames;
    private boolean hidden;

    /**
     * Getter for if direction is hidden.
     * @return true if direction is hidden.
     */
    public boolean isHidden() {
        return hidden;
    }

    /**
     * Gets the valid key names to enter a closed room.
     * @return the valid key names.
     */
    String[] getValidKeyNames() {
        return validKeyNames;
    }

    /**
     * Getter to determine whether a user can enter a room.
     * @return whether a room is locked or not
     */
    String getEnabled() {
        return enabled;
    }

    /**
     * Getter for the direction name.
     * @return the direction name.
     */
    String getDirectionName() {
        return directionName;
    }

    /**
     * Getter for the room location.
     * @return the room location.
     */
    String getRoom() {
        return room;
    }
}
