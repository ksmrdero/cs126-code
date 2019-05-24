package com.example;

import java.util.ArrayList;


public class TicTacToe {
    /** The length of an inputted board. */
    private static final int BOARD_LENGTH = 9;
    /** All possible winning combinations. */
    private static final int[][] WIN = {
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8},
            {2, 4, 6}, {3, 4, 5}, {6, 7, 8}, {0, 1, 2}
    };

    /**
     * Evaluates the given board position.
     * @param boardState the current board position
     * @return the state of the board position
     */
    public static Evaluation evaluateBoard(String boardState) {
        if (boardState == null || boardState.length() != BOARD_LENGTH) {
            return Evaluation.InvalidInput;
        }

        String newBoard = boardState.toLowerCase();
        ArrayList<Integer> oIndex = countChar(newBoard, 'o');
        ArrayList<Integer> xIndex = countChar(newBoard, 'x');

        //check on the allowed number of x's and o's on the board
        int stateTest = xIndex.size() - oIndex.size();
        if (stateTest != 0 && stateTest != 1) {
            return Evaluation.UnreachableState;
        }

        int oWin = winCount(oIndex);
        int xWin = winCount(xIndex);
        if (oWin == 1 && xWin == 0) {
            return Evaluation.Owins;
        } else if (oWin == 0 && xWin == 1) {
            return Evaluation.Xwins;
        } else if (oWin == 0 && xWin == 0) {
            return Evaluation.NoWinner;
        } else {
            return Evaluation.UnreachableState;
        }
    }


    /**
     * Counts the number of times x wins, o wins, or neither
     * @param index An array list of indices of x or o in the given board position
     * @return number of possible ways a x or o has won.
     */
    public static int winCount(ArrayList<Integer> index) {
        int count = 0;
        // Goes through all winning combinations and counts # of times x or o have won
        for (int row = 0; row < WIN.length; row++) {
            int rowCount = 0;
            for (int col = 0; col < WIN[0].length; col++) {
                if (index.contains(WIN[row][col])) {
                    rowCount += 1;
                }
            }
            if (rowCount == 3) {
                count += 1;
            }
        }
        return count;
    }


    /**
     * Grabs the indices of given character in string.
     * @param board Inputted board position
     * @param c character you want to look for
     * @return an array list of indices of character
     */
    public static ArrayList<Integer> countChar(String board, char c) {
        char[] boardVal = board.toCharArray();
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < boardVal.length; i++) {
            if (boardVal[i] == c) {
                index.add(i);
            }
        }
        return index;
    }
}
