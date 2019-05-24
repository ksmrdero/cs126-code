package com.example;

import java.util.ArrayList;

public class TicTacToe {
    public static final int BOARD_LENGTH = 9;
    public static final int[] START = {0,1,2,3,6};
    public static int xWin;
    public static int oWin;
    //public final int[] win = {{0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, }

    public static Evaluation evaluateBoard(String boardState) {
        if (boardState.length() > BOARD_LENGTH) {
            return Evaluation.InvalidInput;
        }

        String newBoard = boardState.toLowerCase();
        ArrayList<Integer> oIndex = countChar(newBoard, 'o');
        ArrayList<Integer> xIndex = countChar(newBoard, 'x');

        int stateTest = xIndex.size() - oIndex.size();
        if (stateTest != 0 && stateTest != 1) {
            return Evaluation.UnreachableState;
        }
        return Evaluation.InvalidInput;
    }


    public static ArrayList<Integer> countChar(String board, char c) {
        char[] boardVal = board.toCharArray();
        ArrayList<Integer> index = new ArrayList<>();
        for (int i = 0; i < boardVal.length; i++) {
            if (boardVal[i] == c) {
                index.add(i);
            }
        }
        return index;

        //return board.length() - board.replace(c,"").length();
    }
}
