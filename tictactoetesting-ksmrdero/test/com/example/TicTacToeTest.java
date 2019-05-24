package com.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by James Wei on 1/20/19
 */
public class TicTacToeTest {
        @Test
        public void simpleNoWinnerBoard() throws Exception {
            assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("O...X.X.."));
        }

        @Test
        public void randomCharInputs() throws Exception {
            assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("13l2;3if9"));
        }

        @Test
        public void fullBoardNoWinner() throws Exception {
            assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("OOXXXOOXX"));
        }

        @Test
        public void randomCharPartFull() throws Exception {
            assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("ox3fmd3.x"));
        }

        @Test
        public void fullBoardDraw() throws Exception {
            assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("xXoOxXXoO"));
        }

        @Test
        public void simpleInvalidInputBoard() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("asdf"));
        }

        @Test
        public void nullInput() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard(null));
        }

        @Test
        public void longInput() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("MCID;EIWIDZPIOCVD"));
        }

        @Test
        public void shortX() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("x"));
        }

        @Test
        public void shortO() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("o"));
        }

        @Test
        public void longXO() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("xoxoxoxoxoOXXOXO"));;
        }

        @Test
        public void longInt() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("1234567890"));
        }

        @Test
        public void emptyString() throws Exception {
            assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard(""));
        }

        @Test
        public void simpleXWinsBoard() throws Exception {
            assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("XXX.OO..."));
        }

        @Test
        public void xWinsRow() throws Exception {
            assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("XxXOo.Ezi"));
        }

        @Test
        public void xWinsDiag() throws Exception {
            assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("xIOOXiklX"));
        }

        @Test
        public void xWinsCol() throws Exception {
            assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("oixokxijx"));
        }

        @Test
        public void simpleOWinsBoard() throws Exception {
            assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("OOO.XXXX."));
        }

        @Test
        public void oWinsRow() throws Exception {
            assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("Xx2OoO3XX"));
        }

        @Test
        public void oWinsDiag() throws Exception {
            assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("x3oXo3oxx"));
        }

        @Test
        public void oWinsCol() throws Exception {
            assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("xoxxokeox"));
        }

        @Test
        public void simpleUnreachableStateBoard() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OOOOOOXXX"));
        }

        @Test
        public void moreOthanX() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("xXOkdOksO"));
        }

        @Test
        public void moreXthanO() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("odkxxilxx"));
        }

        @Test
        public void equalXObutXWon() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("xxxdieooo"));
        }

        @Test
        public void allX() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("xxxXXXxxX"));
        }

        @Test
        public void allO() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OOooOOooO"));
        }

        @Test
        public void xWinsTwice() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("xxxxooxoo"));
        }
        @Test
        public void equalXObutXWon2() throws Exception {
            assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("xxxdokjoo"));
        }

}