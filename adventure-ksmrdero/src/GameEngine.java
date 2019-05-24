import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Game engine to go through the mystery house.
 * Written by James Wei on 2/19/19.
 */
public class GameEngine {
    /* Length of the "go " command a user inputs. */
    private static final int LENGTH_OF_GO_IN_INPUT = 3;
    /* Length of the "pickup " command a user inputs. */
    private static final int LENGTH_OF_PICKUP_IN_INPUT = 7;
    /* Minimum Length of the "use 'item' with 'direction'" command a user inputs. */
    private static final int MINIMUM_NUMBER_OF_WORDS_IN_USE_INPUT = 4;

    private String currentLocation;
    private String items;
    private String inputKey;
    private PlayerOptions player;
    private Map mysteryHouse;

    /**
     * Constructor for the game engine.
     * @param inputData the JSON source.
     */
    GameEngine(String inputData) {
        try {
            System.out.println("Trying to parse data as URL...");
            mysteryHouse = new Gson().fromJson(getUrl(inputData), Map.class);
        } catch (IOException e) {
            System.out.println("Trying to parse data as file...");
            String fileData = Data.getFileContentsAsString(inputData);
            mysteryHouse = new Gson().fromJson(fileData, Map.class);
        }

        player = new PlayerOptions(mysteryHouse);
        for (Item item : mysteryHouse.getPlayer().getItems()){
            player.playerItems.add(item.getName());
        }
    }

    /**
     * Run method.
     */
    void run() {
        Scanner scanner = new Scanner(System.in);

        if (MapFeatures.checkValidMap(mysteryHouse)) {
            System.out.println("Map sucessfully loaded... \n");
        } else {
            System.out.println("WARNING! You will NOT be able to get to your final destination!");
        }

        System.out.println("Welcome to the Harold Estate. " +
                "The wife of the young millionaire has recently been murdered.");
        System.out.println("You have been assigned to solve the case.");
        System.out.println("There are three suspects: the butler, the chef, and the husband.");
        System.out.println("Your job is to find the right killer. Good luck...\n");

        String endingRoom = mysteryHouse.getEndingRoom();
        currentLocation = mysteryHouse.getStartingRoom();

        String input;
        String command;
        String previousLocation = "";

        while (!endingRoom.equals(currentLocation)) {
            printCurrentItemList();
            System.out.println(player.getLocationDescription(currentLocation) + "\n");

            if (!previousLocation.equals(currentLocation)) {
                items = player.getAvailableItems(currentLocation);
            }

            if (items != null) {
                System.out.println("Available items to pick up are: " + items);
            } else {
                System.out.println("There are no available items to pick up here.");
            }
            previousLocation = currentLocation;

            System.out.println("From here, you can go: " + player.getPossibleDirections(currentLocation));
            input = scanner.nextLine();

            if (input.toLowerCase().equals("exit") || input.toLowerCase().equals("quit")) {
                break;
            }

            try {
                command = input.split(" ")[0];
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("I don't understand '" + input + "'.");
                continue;
            }

            if (!command.equals("go") && !command.equals("pickup") && !command.equals("use")) {
                System.out.println("I don't understand '" + input + "'.");
                continue;
            }

            switch (command) {
                case "go":
                    processNextDirection(input);
                    break;
                case "pickup":
                    processPickUpItem(input);
                    break;
                case "use":
                    processUseItem(input);
                    break;
            }

            if (endingRoom.equals(currentLocation)) {
                System.out.println(player.getLocationDescription(currentLocation));
                System.out.println("You've won! Congrats!");
            }
        }
    }

    /**
     * Prints the current items the user has.
     */
    private void printCurrentItemList() {
        if (player.playerItems.size() == 0) {
            System.out.println("You currently have no items");
        } else {
            StringBuilder itemList = new StringBuilder();
            for (int i = 0; i < player.playerItems.size() - 1; i++) {
                itemList.append(player.playerItems.get(i)).append(", ");
            }

            itemList.append(player.playerItems.get(player.playerItems.size() - 1));
            System.out.println("Your current items: " + itemList + "\n");
        }
    }

    /**
     * Processes the user when they command "pick" item.
     * @param input the input the user wrote.
     */
    private void processPickUpItem(String input) {
        String inputItem = input.substring(LENGTH_OF_PICKUP_IN_INPUT);
        if (items == null) {
            System.out.println("I can't pickup '" + inputItem + ".'");
            return;
        }

        String[] possibleItems = items.toLowerCase().split(", ");
        boolean containsItem = player.checkStringContains(possibleItems, inputItem.toLowerCase());

        if (!containsItem) {
            System.out.println("I can't pickup '" + inputItem + ".'");
        } else {
            player.playerItems.add(inputItem);
            items = items.replace(inputItem, "gone ");
        }
    }

    /**
     * Processes the user when they command "go" direction.
     * @param input the input the user wrote.
     */
    private void processNextDirection(String input) {
        String inputDirection = input.substring(LENGTH_OF_GO_IN_INPUT);

        if (player.getPossibleDirections((currentLocation)) == null) {
            System.out.println("I can't go '" + inputDirection + ".'");
            return;
        }

        String[] possibleDirections =
                player.getPossibleDirections(currentLocation).toLowerCase().split(", ");
        boolean containsDirection = player.checkStringContains(possibleDirections,inputDirection.toLowerCase());

        if (!containsDirection) {
            System.out.println("I can't go " + inputDirection);
            return;
        }

        if (!player.getEnabledDescription(currentLocation, inputDirection)) {
            if (player.canEnterDisabledRoom(currentLocation,inputDirection,inputKey)) {
                currentLocation = player.getNextLocation(currentLocation,inputDirection);
            } else {
                System.out.println("Uh-oh, looks like this entrance is locked. Check your keys?");
            }
        } else {
            currentLocation = player.getNextLocation(currentLocation,inputDirection);
        }

    }

    /**
     * Processes the user when they command "use" item "with" direction.
     * @param input the input the user wrote.
     */
    private void processUseItem(String input) {
        String[] inputValues = input.split(" ");
        if (inputValues.length < MINIMUM_NUMBER_OF_WORDS_IN_USE_INPUT) {
            System.out.println("I don't understand '" + input + "'.");
            return;
        }

        String inputDirection = inputValues[inputValues.length - 1];
        String[] possibleDirections =
                player.getPossibleDirections(currentLocation).toLowerCase().split(", ");

        boolean containsDirection = player.checkStringContains(possibleDirections,inputDirection.toLowerCase());
        if (!containsDirection || !inputValues[inputValues.length - 2].equals("with")) {
            System.out.println("I can't go '" + inputDirection + "'.");
            return;
        }

        StringBuilder key = new StringBuilder();
        for (int i = 1; i < inputValues.length - 3; i++) {
            key.append(inputValues[i]).append(" ");
        }

        key.append(inputValues[inputValues.length - 3]);
        String[] currentPlayerItems = player.playerItems.toArray(new String[0]);
        boolean containsItem = player.checkStringContains(currentPlayerItems, key.toString());

        if (!containsItem) {
            System.out.println("I can't use '" + key.toString() + ".'");
        } else {
            inputKey = key.toString();
            processNextDirection("go " + inputDirection);
            inputKey = null;
        }
    }

    // Method taken from https://stackoverflow.com/questions/4328711/read-url-to-string-in-few-lines-of-java-code.
    private static String getUrl(String aUrl) throws IOException {
        String urlData = "";
        URL urlObj = new URL(aUrl);
        URLConnection conn = urlObj.openConnection();
        try (BufferedReader reader =
                     new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            urlData = reader.lines().collect(Collectors.joining("\n"));
        }
        return urlData;
    }
}
