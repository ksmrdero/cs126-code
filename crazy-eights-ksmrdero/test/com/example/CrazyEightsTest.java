package com.example;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.example.Card.Rank.*;
import static com.example.Card.Suit.*;
import static org.junit.Assert.*;

public class CrazyEightsTest {
    private GameEngine startGame;
    private BasicPlayer player;
    private List<Card> heartDeck;
    private List<Card> noHeartNoEightDeck;
    private List<Card> noHeartWithEightDeck;


    @Before
    public void startGame() {
        startGame = new GameEngine(4);
        player = new BasicPlayer();

        heartDeck = new ArrayList<Card>() {{
            add(new Card(HEARTS, ACE));
            add(new Card(HEARTS, THREE));
            add(new Card(HEARTS, FIVE));
            add(new Card(HEARTS, SEVEN));
            add(new Card(HEARTS, TEN));
        }};

        noHeartWithEightDeck = new ArrayList<Card>(){{
            add(new Card(SPADES, ACE));
            add(new Card(DIAMONDS, THREE));
            add(new Card(CLUBS, EIGHT));
            add(new Card(SPADES, KING));
            add(new Card(SPADES, NINE));
        }};

        noHeartNoEightDeck = new ArrayList<Card>(){{
            add(new Card(SPADES, ACE));
            add(new Card(DIAMONDS, THREE));
            add(new Card(CLUBS, JACK));
            add(new Card(DIAMONDS, KING));
            add(new Card(SPADES, NINE));
        }};
    }

    @Test
    public void drawInitialCards() {
        startGame.setUp();
        assertEquals(5, startGame.drawInitialCards().size());
    }

    @Test
    public void drawInitialCardsRemovesCardsFromDrawPile() {
        startGame.setUp();
        startGame.drawInitialCards();
        assertEquals(27, startGame.getDrawPile().size());
    }

    @Test
    public void drawSingleCardRemovesCardFromDrawPile() {
        startGame.setUp();
        startGame.drawSingleCard();
        assertEquals(31, startGame.getDrawPile().size());
    }

    @Test
    public void simpleGetPointsFromCardList() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(Card.getDeck().get(i));
        }
        assertEquals(15, startGame.getSinglePlayerPoints(cards));
    }

    @Test
    public void valueGreaterThanTargetInPointsArray() {
        int[] values = {300,1,4,3,2,5};
        assertTrue("Points array check should return true.",
                new GameEngine(4).checkPointsArray(values,200));
    }

    @Test
    public void valueLessThanTargetInPointsArray() {
        int[] values = {23,1,4,3,2,5};
        assertFalse("Points array check should return False.",
                new GameEngine(4).checkPointsArray(values,200));
    }

    @Test
    public void testPointsArrayIfNull() {
        int[] values = null;
        assertFalse("Points array check should return False.",
                new GameEngine(4).checkPointsArray(values,200));
    }

    @Test
    public void testPointsArrayInEmptyArray() {
        int[] values = {};
        assertFalse("Points array check should return False.",
                new GameEngine(4).checkPointsArray(values,200));
    }

    @Test
    public void getIndexOfWinnerFromArray() {
        int[] points = {1,2,3,4,5};
        ArrayList<Integer> winnerIndex = new ArrayList<>() {{add(4);}};
        assertEquals(winnerIndex,startGame.getWinner(points));
    }

    @Test
    public void getIndexOfMultipleWinnersFromArray() {
        int[] points = {1,5,3,4,5};
        ArrayList<Integer> winnerIndex = new ArrayList<>() {{
            add(1);
            add(4);
        }};
        assertEquals(winnerIndex,startGame.getWinner(points));
    }

    @Test
    public void getNoWinnersFromEmptyArray() {
        int[] points = new int[5];
        ArrayList<Integer> winnerIndex = new ArrayList<>(){{
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        assertEquals(winnerIndex,startGame.getWinner(points));
    }

    @Test
    public void checkValidMoveByPlayerGivenSameSuit() {
        Card topCard = new Card(HEARTS,ACE);
        Card playerCard = new Card(HEARTS, TEN);
        assertTrue("Same suit play should be valid.",
                startGame.checkValidPlay(topCard,playerCard,null));
    }

    @Test
    public void checkValidMoveByPlayerGivenSameRank() {
        Card topCard = new Card(HEARTS,JACK);
        Card playerCard = new Card(SPADES, JACK);
        assertTrue("Same rank play should be valid.",
                startGame.checkValidPlay(topCard,playerCard,null));
    }

    @Test
    public void checkInvalidMoveByPlayerGivenDifferentRankAndSuit() {
        Card topCard = new Card(HEARTS,ACE);
        Card playerCard = new Card(DIAMONDS, JACK);
        assertFalse("Different rank and suit play should be invalid.",
                startGame.checkValidPlay(topCard,playerCard,null));
    }

    @Test
    public void checkValidMoveByPlayerGivenEightSuit() {
        Card topCard = new Card(HEARTS,EIGHT);
        Card playerCard = new Card(CLUBS, FOUR);
        assertTrue("Card is of the declared suit.",
                startGame.checkValidPlay(topCard,playerCard,CLUBS));
    }

    @Test
    public void checkInvalidMoveByPlayerGivenEightSuit() {
        Card topCard = new Card(HEARTS,EIGHT);
        Card playerCard = new Card(DIAMONDS, FOUR);
        assertFalse("Card must be of the declared suit.",
                startGame.checkValidPlay(topCard,playerCard,SPADES));
    }

    @Test
    public void simplePlayerShouldDrawCard() {
        player.receiveInitialCards(heartDeck);
        assertTrue("Player deck does not contain suit or rank.",
                player.shouldDrawCard(new Card(SPADES,NINE), null));
    }

    @Test
    public void simplePlayerShouldNotDrawCard() {
        player.receiveInitialCards(heartDeck);
        assertFalse("Player deck does contain suit or rank.",
                player.shouldDrawCard(new Card(HEARTS,TEN), null));
    }

    @Test
    public void playerShouldNotDrawCardWhenTopCardIsEight() {
        player.receiveInitialCards(heartDeck);
        assertFalse("Player deck does contain suit or rank.",
                player.shouldDrawCard(new Card(DIAMONDS,EIGHT), HEARTS));
    }

    @Test
    public void playerShouldDrawCardWhenTopCardIsEight() {
        player.receiveInitialCards(noHeartNoEightDeck);
        assertTrue("Player deck does not contain suit or rank.",
                player.shouldDrawCard(new Card(DIAMONDS,EIGHT), HEARTS));
    }

    @Test
    public void playerShouldNotDrawCardWithEight() {
        player.receiveInitialCards(noHeartWithEightDeck);
        assertFalse("Player deck does contain suit or rank.",
                player.shouldDrawCard(new Card(HEARTS,EIGHT), HEARTS));
    }

    @Test
    public void simplePlayerRecieveCardTest() {
        player.receiveCard(new Card(HEARTS,ACE));
        assertEquals(1,player.playerCards.size());
    }

    @Test
    public void checkPlayerHasPlayedACard() {
        player.receiveInitialCards(heartDeck);
        player.shouldDrawCard(new Card(HEARTS, KING),null);
        player.playCard();
        assertEquals(4, player.playerCards.size());
    }

    @Test
    public void checkPlayerHasPlayedRightCard() {
        player.receiveInitialCards(heartDeck);
        player.shouldDrawCard(new Card(HEARTS, KING),null);
        assertEquals(heartDeck.get(0), player.playCard());
    }

    @Test
    public void checkPlayerHasPlayedEightGivenOnlyOption() {
        player.receiveInitialCards(noHeartWithEightDeck);
        player.shouldDrawCard(new Card(HEARTS, TWO),null);
        assertEquals(noHeartWithEightDeck.get(2), player.playCard());
    }

    @Test
    public void checkPlayerHasPlayedCorrectCardGivenSuit() {
        player.receiveInitialCards(noHeartNoEightDeck);
        player.shouldDrawCard(new Card(SPADES, EIGHT), DIAMONDS);
        assertEquals(noHeartNoEightDeck.get(1), player.playCard());
    }

    @Test
    public void checkPlayerHasPlayedCorrectCardByRank() {
        player.receiveInitialCards(noHeartNoEightDeck);
        player.shouldDrawCard(new Card(HEARTS, KING),null);
        assertEquals(noHeartNoEightDeck.get(3), player.playCard());
    }

    @Test
    public void playerDeclareMostCommonSuit() {
        player.receiveInitialCards(noHeartWithEightDeck);
        assertEquals(SPADES, player.declareSuit());
    }

    @Test
    public void playerDeclareSuitWithSingleCard() {
        player.receiveCard(new Card(HEARTS,EIGHT));
        assertEquals(HEARTS, player.declareSuit());
    }

    @Test
    public void resetPlayerCards() {
        player.receiveInitialCards(heartDeck);
        assertEquals(5, player.playerCards.size());
        player.reset();
        assertEquals(0, player.playerCards.size());
    }
}
