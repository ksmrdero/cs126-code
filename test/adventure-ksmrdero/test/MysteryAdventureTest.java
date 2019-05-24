import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MysteryAdventureTest {
    private final String MYSTERY_HOUSE_ADVENTURE_JSON =
            Data.getFileContentsAsString("MysteryAdventure.json");
    private Map mysteryHouse;

    @Before
    public void setUpGson() throws Exception {
        Gson gson = new Gson();
        mysteryHouse = gson.fromJson(MYSTERY_HOUSE_ADVENTURE_JSON, Map.class);
    }

    @Test
    public void getFirstLocationName() {
        assertEquals("FrontGate", mysteryHouse.getRooms()[0].getName());
    }

    @Test
    public void getStartingRoom() {
        assertEquals("FrontGate", mysteryHouse.getStartingRoom());
    }

    @Test
    public void getEndingRoom() {
        assertEquals("Attic", mysteryHouse.getEndingRoom());
    }

    @Test
    public void getSinglePlayerItemAtStart(){
        assertEquals("cell phone", mysteryHouse.getPlayer().getItems()[0].getName());
    }

    @Test
    public void getItemFromRandomLocation(){
        assertEquals("some aged wine", mysteryHouse.getRooms()[13].getItems()[1].getName());
    }

    @Test
    public void getNoItemFromRandomLocationWithNoItems(){
        assertEquals(0, mysteryHouse.getRooms()[0].getItems().length);
    }

    @Test
    public void getDescriptionFromFirstLocation() {
        assertEquals("You are at the front gate...",
                mysteryHouse.getRooms()[0].getDescription());
    }

    @Test
    public void getDescriptionFromLastLocation() {
        assertEquals("Looks like the butler set up everything! " +
                        "You bring this to the authorities. Case closed.",
                mysteryHouse.getRooms()[17].getDescription());
    }

    @Test
    public void getDescriptionFromRandomLocation() {
        assertEquals("You are in the wine cellar. Looks like whoever lived here had exquisite taste. \n" +
                        "But look, there's a key!", mysteryHouse.getRooms()[13].getDescription());
    }

    @Test
    public void getDirectionFromFirstLocation() {
        assertEquals("inside", mysteryHouse.getRooms()[0].getDirections()[0].getDirectionName());
    }

    @Test
    public void getDirectionFromLastLocation() {
        assertEquals("down", mysteryHouse.getRooms()[17].getDirections()[0].getDirectionName());
    }

    @Test
    public void getDirectionFromRandomLocation() {
        assertEquals("right", mysteryHouse.getRooms()[3].getDirections()[0].getDirectionName());
    }

    @Test
    public void getRoomFromFirstLocation() {
        assertEquals("GreatHall", mysteryHouse.getRooms()[0].getDirections()[0].getRoom());
    }

    @Test
    public void getRoomFromLastLocation() {
        assertEquals("MasterBathroom", mysteryHouse.getRooms()[17].getDirections()[0].getRoom());
    }

    @Test
    public void getRoomFromRandomLocation() {
        assertEquals("DiningHall", mysteryHouse.getRooms()[1].getDirections()[0].getRoom());
    }

    @Test
    public void getEnabledRoomFromFirstLocation() {
        assertEquals("true", mysteryHouse.getRooms()[0].getDirections()[0].getEnabled());
    }

    @Test
    public void getEnabledRoomFromLastLocation() {
        assertEquals("true", mysteryHouse.getRooms()[17].getDirections()[0].getEnabled());
    }

    @Test
    public void getEnabledRoomFromRandomLocation() {
        assertEquals("false", mysteryHouse.getRooms()[11].getDirections()[1].getEnabled());
    }

    @Test
    public void getValidKeyNamesFromValidLocation() {
        assertEquals("a copy of Moby Dick",
                mysteryHouse.getRooms()[11].getDirections()[1].getValidKeyNames()[0]);
    }

    @Test
    public void noValidKeyNamesExistForEnabledRoom() {
        assertEquals(0,
                mysteryHouse.getRooms()[4].getDirections()[1].getValidKeyNames().length);
    }

    @Test
    public void testDirectionIsNotHiddenFromUser() {
        assertFalse(mysteryHouse.getRooms()[4].getDirections()[1].isHidden());
    }

    @Test
    public void testDirectionIsHiddenFromUser() {
        assertTrue(mysteryHouse.getRooms()[15].getDirections()[1].isHidden());
    }
}