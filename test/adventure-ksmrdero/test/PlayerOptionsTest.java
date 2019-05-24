import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerOptionsTest {
    private final String MYSTERY_HOUSE_ADVENTURE_JSON =
            Data.getFileContentsAsString("MysteryAdventure.json");
    private PlayerOptions player;

    @Before
    public void setUpGson() throws Exception {
        Gson gson = new Gson();
        Map mysteryHouse = gson.fromJson(MYSTERY_HOUSE_ADVENTURE_JSON, Map.class);
        player = new PlayerOptions(mysteryHouse);
    }

    @Test
    public void simpleGetDescriptionFromLocation() {
        assertEquals("You have entered the great hall. It looks pretty great. ",
                player.getLocationDescription("GreatHall"));
    }

    @Test
    public void getLastDescriptionFromLocation() {
        assertEquals("Looks like the butler set up everything! " +
                        "You bring this to the authorities. Case closed.",
                player.getLocationDescription("Attic"));
    }

    @Test
    public void getDescriptionFromLocationThatDoesNotExist() {
        assertNull("Location doesn't exist", player.getLocationDescription("Librarys"));
    }

    @Test
    public void nullGetDescriptionFromLocation() {
        assertNull("Room not found", player.getLocationDescription(null));
    }

    @Test
    public void badLocationGetDescription() {
        assertNull("Room not found", player.getLocationDescription("adsf"));
    }

    @Test
    public void simpleGetPossibleDirections() {
        assertEquals("inside",
                player.getPossibleDirections("FrontGate"));
    }

    @Test
    public void getPossibleDirectionsFromLastLocation() {
        assertEquals("down",
                player.getPossibleDirections("Attic"));
    }

    @Test
    public void getPossibleDirectionsWithHiddenDirection() {
        assertEquals("back",
                player.getPossibleDirections("MasterBathroom"));
    }

    @Test
    public void getPossibleDirectionsWithHiddenDirectionButPlayerHasItem() {
        player.playerItems.add("a copy of Moby Dick");
        assertEquals("back, up",
                player.getPossibleDirections("MasterBathroom"));
    }

    @Test
    public void nullGetPossibleDirections() {
        assertNull("Room not found", player.getLocationDescription(null));
    }

    @Test
    public void getNextLocationFromStart() {
        assertEquals("GreatHall",
                player.getNextLocation("FrontGate", "inside"));
    }

    @Test
    public void randomGetNextLocation() {
        assertEquals("MasterBathroom",
                player.getNextLocation("MasterBedroom","right"));
    }

    @Test
    public void nullGetNextLocation() { ;
        assertNull("Room and Direction not found",
                player.getNextLocation(null,null));
    }

    @Test
    public void badLocationGetNextLocation() {
        assertNull("Room not found", player.getNextLocation("mqoiewn;l","Down"));
    }

    @Test
    public void badDirectionGetNextLocation() {
        assertNull("Direction not found",
                player.getNextLocation("Library","eq32"));
    }

    @Test
    public void playerCanEnterDisabledRoom() {
        assertTrue("Player can enter room",
                player.canEnterDisabledRoom("Basement", "right", "an axe"));
    }

    @Test
    public void playerCannotEnterDisabledRoom() {
        assertFalse("Player cannot enter room",
                player.canEnterDisabledRoom("MasterBedroom", "left", "floss"));
    }

    @Test
    public void getSingleAvailableItem() {
        assertEquals("tv remote", player.getAvailableItems("LivingRoom"));
    }

    @Test
    public void getMultipleAvailableItems() {
        assertEquals("floss, some shampoo, a toothbrush",
                player.getAvailableItems("MasterBathroom"));
    }

    @Test
    public void getNoAvailableItemsFromRoom() {
        assertNull(player.getAvailableItems("FrontGate"));
    }

    @Test
    public void getEnabledDescriptionFromRandomRoom() {
        assertTrue(player.getEnabledDescription("MasterBedroom", "right"));
    }

    @Test
    public void getEnabledDescriptionFromRoomThatIsFalse() {
        assertFalse(player.getEnabledDescription("LivingRoom", "downstairs"));
    }

    @Test
    public void getEnabledDescriptionFromRoomThatIsTrue() {
        assertTrue(player.getEnabledDescription("GreatHall", "right"));
    }
}
