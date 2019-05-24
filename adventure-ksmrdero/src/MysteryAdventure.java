/**
 * Main interface for adventure time.
 * Written by James Wei on 2/4/19.
 * Edited 2/19/19.
 */
public class MysteryAdventure {

    public static void main(String[] args) {
        GameEngine startGame = new GameEngine(args[0]);
        startGame.run();
    }
}
