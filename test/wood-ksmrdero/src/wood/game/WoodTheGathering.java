package wood.game;

import wood.graphics.UserInterface;
import wood.replay.Replay;
import wood.replay.ReplayIO;
import wood.strategy.*;

import javax.swing.*;
import java.nio.file.FileSystems;
import java.util.Random;

public class WoodTheGathering {
    private static final int DEFAULT_BOARD_SIZE = 10;
    private static final boolean DEFAULT_GUI_ENABLED = false;
    private static final int PREFERRED_GUI_WIDTH = 750; // Bump this up or down according to your screen size

    public static void main(String[] args) {
        boolean guiEnabled = DEFAULT_GUI_ENABLED;
        String replayFilePath = null; // Use this if you want to replay a past match
        GameEngine gameEngine = null;
        Random random = new Random();

        int interval = 1000;

        double blueTotal = 0;
        double redTotal = 0;
        for (int i = 0; i < interval; ++i) {
            Long seed = 0L;
            if(replayFilePath == null) {
                WoodPlayerStrategy redStrategy =  new WinStrategy();
                WoodPlayerStrategy blueStrategy = new RandomStrategy();
                seed = Math.abs(random.nextLong());
                gameEngine = new GameEngine(DEFAULT_BOARD_SIZE, redStrategy, blueStrategy, seed);
                gameEngine.setGuiEnabled(guiEnabled);
            }

            if(gameEngine == null) {
                return;
            }

            gameEngine.runGame();

            if (gameEngine.getRedPlayerScore() >= 2000) {
                //System.out.println(seed);
                redTotal++;
            } else {
                blueTotal++;
            }
        }

        System.out.println("Win Percentage: " + redTotal/(blueTotal+redTotal));

        // Record the replay if the output path isn't null and we aren't already watching a replay
        String replayOutputFilePath = null; //FileSystems.getDefault().getPath("data", "data.txt").toString();;
        if(replayFilePath == null && replayOutputFilePath != null) {
            Replay gameReplay = gameEngine.getReplay();
            ReplayIO.writeReplayToFile(gameReplay, replayOutputFilePath);
        }
    }
}


//package wood.game;
//
//import wood.graphics.UserInterface;
//import wood.replay.Replay;
//import wood.replay.ReplayIO;
//import wood.strategy.*;
//
//import javax.swing.*;
//
//public class WoodTheGathering {
//    private static final int DEFAULT_BOARD_SIZE = 10;
//    private static final boolean DEFAULT_GUI_ENABLED = true;
//    private static final int PREFERRED_GUI_WIDTH = 500; // Bump this up or down according to your screen size
//
//    public static void main(String[] args) {
//        boolean guiEnabled = DEFAULT_GUI_ENABLED;
//    String replayFilePath = null; // Use this if you want to
//                                                                      // replay a past match
//        final GameEngine gameEngine;
//
//        if(replayFilePath == null) {
//            WoodPlayerStrategy redStrategy =  new WinStrategy();
//            WoodPlayerStrategy blueStrategy = new RandomStrategy();
//            gameEngine = new GameEngine(DEFAULT_BOARD_SIZE, redStrategy, blueStrategy);
//            gameEngine.setGuiEnabled(guiEnabled);
//        } else {
//            gameEngine = ReplayIO.setupEngineForReplay(replayFilePath);
//            guiEnabled = true;
//        }
//
//        if(gameEngine == null) {
//            return;
//        }
//
//        if(guiEnabled) {
//            // Run the GUI code on a separate Thread (The event dispatch thread)
//            SwingUtilities.invokeLater(() -> UserInterface.instantiateGUI(gameEngine, PREFERRED_GUI_WIDTH));
//        }
//        gameEngine.runGame();
//
//        // Record the replay if the output path isn't null and we aren't already watching a replay
//        String replayOutputFilePath = null;
//        if(replayFilePath == null && replayOutputFilePath != null) {
//            Replay gameReplay = gameEngine.getReplay();
//            ReplayIO.writeReplayToFile(gameReplay, replayOutputFilePath);
//        }
//    }
//}
