import com.example.GameEngine;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class for Crazy 8's game.
 * Written by James Wei on 2/10/19.
 */
public class CrazyEights {
    public static void main(String[] args) {
        System.out.println("Welcome to Crazy 8's!");
        System.out.println("How many players would you like? ");

        Scanner scanner = new Scanner(System.in);
        int numPlayers = -1;

        try {
            numPlayers = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Input not valid. Sorry.");
        }

        GameEngine startGame = new GameEngine(numPlayers);
        startGame.setUp();
        startGame.run();
    }
}
