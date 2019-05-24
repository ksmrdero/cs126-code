package com.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by ________
 */
public class TicTacToeTest {

    @Test
    public void simpleNoWinnerBoard() throws Exception {
        assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("O...X.X.."));

    }

    @Test
    public void simpleInvalidInputBoard() throws Exception {
        assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("asdf"));
    }

    @Test
    public void simpleXWinsBoard() throws Exception {
        assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("XXX.OO.O."));

    }

    @Test
    public void simpleOWinsBoard() throws Exception {
        assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("OOO.XXXX."));

    }

    @Test
    public void simpleUnreachableStateBoard() throws Exception {
        assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OOOOOOXXX"));

    }


}