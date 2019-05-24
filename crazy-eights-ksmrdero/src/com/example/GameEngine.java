package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game engine class to play Crazy 8's.
 * Written by James Wei on 2/12/19.
 */
public class GameEngine {
    /** Number of cards each player gets at the start. */
    private static final int NUMBER_OF_STARTING_CARDS = 5;
    /** Score a player needs to win. */
    private static final int SCORE_NEEDED_TO_WIN = 200;
    /** Maximum number of possible players. */
    private static final int MAX_NUMBER_OF_PLAYERS = 7;
    /** Minimum number of possible players. */
    private static  final int MIN_NUMBER_OF_PLAYERS = 2;
    /** Minimum number of possible players to use 2 decks. */
    private static final int MIN_NUMBER_OF_PLAYERS_FOR_TWO_DECKS = 5;

    private int numPlayers;
    private List<Card> drawPile;
    private Card topCard;
    private Card.Suit eightSuit;

    private BasicPlayer[] players;
    private int[] playerPoints;
    private int[][] totalWinsAndPoints;

    /**
     * Constructor for the game engine class.
     * @param participants the number of participants in the game.
     */
    public GameEngine(int participants) {
        this.numPlayers = participants;
        this.players = new BasicPlayer[numPlayers];
        this.totalWinsAndPoints = new int[2][numPlayers];

        for (int i = 0; i < players.length; i++) {
            players[i] = new BasicPlayer();
        }
    }

    /**
     * Sets the top card to a random card.
     * If the top card is 8, then game engine will redraw the card.
     */
    public void setUp() {
        if (numPlayers > MAX_NUMBER_OF_PLAYERS) {
            System.out.println("There are too many people!");
            System.exit(0);
        } else if (numPlayers < MIN_NUMBER_OF_PLAYERS) {
            System.out.println("Input doesn't make sense!");
            System.exit(0);
        } else if (numPlayers > MIN_NUMBER_OF_PLAYERS_FOR_TWO_DECKS) {
            drawPile = Card.getDeck();
            drawPile.addAll(Card.getDeck());
        } else {
            drawPile = Card.getDeck();
        }

        Random random = new Random();
        Card playerCard = drawPile.get(random.nextInt(drawPile.size()));

        while (playerCard.getRank() == Card.Rank.EIGHT) {
            playerCard = drawPile.get(random.nextInt(drawPile.size()));
        }

        topCard = playerCard;

        for (BasicPlayer player : players) {
            player.receiveInitialCards(drawInitialCards());
        }

        playerPoints = new int[numPlayers];
    }

    /**
     * Run implementation for game engine.
     */
    public void run() {
        int playerIndex = 0;

        while (!checkPointsArray(totalWinsAndPoints[1], SCORE_NEEDED_TO_WIN)) {

            // if draw pile has run out of cards, count the points for each player
            if (drawPile.size() == 0) {
                for (int index = 0; index < players.length; index++) {
                    playerPoints[index] += getPointsFromRound(players, players[index]);

                    // gets the player with the most points and add it to that player's win count
                    for (int winnerIndex : getWinner(playerPoints)) {

                        if (winnerIndex == index) {
                            totalWinsAndPoints[0][index] += 1;
                        }
                    }
                    totalWinsAndPoints[1][index] += playerPoints[index];
                }

                printSummary();
                resetGame();
                setUp();
                playerIndex = 0;
            }

            getNextMove(playerIndex);

            // adds up points for the player who won
            if (players[playerIndex].playerCards.size() == 0) {
                playerPoints[playerIndex] += getPointsFromRound(players, players[playerIndex]);
                totalWinsAndPoints[0][playerIndex] += 1;
                totalWinsAndPoints[1][playerIndex] += playerPoints[playerIndex];

                printSummary();
                resetGame();
                setUp();
            }

            playerIndex = (playerIndex + 1) % players.length;
        }
    }


    /**
     * Determines the action for the player.
     * @param playerIndex the index of the player.
     */
    private void getNextMove(int playerIndex) {
        Card playedCard;

        // if card is not an 8
        if (topCard.getRank() != Card.Rank.EIGHT){

            //if player cannot play a card, then they will draw a card
            if (players[playerIndex].shouldDrawCard(topCard, null)) {
                players[playerIndex].receiveCard(drawSingleCard());
                System.out.println("Player " + playerIndex + " drew a card.");
            } else {
                playedCard = players[playerIndex].playCard();

                // check if player has played a valid card
                if (!checkValidPlay(topCard, playedCard,null)) {
                    System.out.println("Player " + playerIndex + " played " +
                            playedCard.getRank() + " of " + playedCard.getSuit());
                    System.out.println("Uh-oh, looks like player " + playerIndex + " is cheating...");
                    System.out.println("Game will now end.");
                    System.exit(0);
                }

                // set top card equal to the card player played
                topCard = playedCard;

                System.out.println("Player " + playerIndex + " played " +
                        topCard.getRank() + " of " + topCard.getSuit());

                // if player played an 8, player will declare the suit
                if (topCard.getRank() == Card.Rank.EIGHT) {
                    eightSuit = players[playerIndex].declareSuit();
                    System.out.println("Player " + playerIndex + " declared " + eightSuit);
                }
            }
        } else {

            // if player cannot play any card declared by the 8 suit
            if (players[playerIndex].shouldDrawCard(topCard, eightSuit)) {
                players[playerIndex].receiveCard(drawSingleCard());
                System.out.println("Player " + playerIndex + " drew a card.");
            } else {
                playedCard = players[playerIndex].playCard();

                // checks if player has played a valid card
                if (!checkValidPlay(topCard, playedCard, eightSuit)) {
                    System.out.println("Player " + playerIndex + " played " +
                            playedCard.getRank() + " of " + playedCard.getSuit());
                    System.out.println("Uh-oh, looks like player " + playerIndex + " is cheating...");
                    System.out.println("Game will now end.");
                    System.exit(0);
                }

                topCard = playedCard;

                System.out.println("Player " + playerIndex + " played " +
                        topCard.getRank() + " of " + topCard.getSuit());

                // if player has played an 8, then the player will declare a suit
                if (topCard.getRank() == Card.Rank.EIGHT) {
                    eightSuit = players[playerIndex].declareSuit();
                    System.out.println("Player " + playerIndex + " declared " + eightSuit);
                } else {
                    eightSuit = null;
                }
            }
        }
    }

    /**
     * Resets the game.
     */
    private void resetGame() {
        for (BasicPlayer player : players) {
            player.reset();
        }

        drawPile.clear();
        topCard = null;
    }

    /**
     * Checks valid card is played by a player.
     * @param currentCard the current card in play.
     * @param cardPlayed the card the player played.
     * @param declaredSuit the suit previous player declared if they played an 8.
     * @return whether or not the player made a valid move.
     */
    boolean checkValidPlay(Card currentCard, Card cardPlayed, Card.Suit declaredSuit) {
        if (cardPlayed.getRank() == Card.Rank.EIGHT) {
            return true;
        }

        if (declaredSuit != null) {
            return cardPlayed.getSuit() == declaredSuit;
        }

        return currentCard.getSuit() == cardPlayed.getSuit()
                || currentCard.getRank() == cardPlayed.getRank();
    }


    /**
     * Prints out round and total game summaries.
     */
    private void printSummary() {
        System.out.println("\n");
        System.out.println("This round's summary: ");

        for (int i = 0; i < players.length; i++) {
            System.out.println("Player " + i + ": " + playerPoints[i] + " points.");
        }

        System.out.println("\nTotal match summary: ");

        for (int i = 0; i < totalWinsAndPoints[0].length; i++) {
            System.out.println("Player " + i + ": " + totalWinsAndPoints[0][i] + " wins with " +
                    totalWinsAndPoints[1][i] + " total points.");

        }

        System.out.println("\n");
    }

    /**
     * Finds the index of the winner.
     * @param points the array of the player points.
     * @return the index(es) of the winner.
     */
    ArrayList<Integer> getWinner(int[] points) {
        int max = 0;
        for (int point : points) {
            if (point > max) {
                max = point;
            }
        }

        ArrayList<Integer> playerIndex = new ArrayList<>();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == max) {
                playerIndex.add(i);
            }
        }

        return playerIndex;
    }

    /**
     * Checks if a player has reached a certain number of points.
     * @param points the list of current player points.
     * @param target the target threshold.
     * @return true if value in points array is greater than target value.
     */
    boolean checkPointsArray(int[] points, int target) {
        if (points == null) {
            return false;
        }

        for (int value : points) {
            if (value >= target) {
                return true;
            }
        }

        return false;
    }

    /**
     * Distributes 5 random cards at the start of the game.
     * @return a list of cards a player will recieve.
     */
    List<Card> drawInitialCards() {
        List<Card> startingCards = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_STARTING_CARDS; i++) {
            Random random = new Random();
            Card playerCard = drawPile.get(random.nextInt(drawPile.size()));
            startingCards.add(playerCard);
            drawPile.remove(playerCard);
        }

        return startingCards;
    }

    /**
     * Draws a single card for a player.
     * @return a single random card.
     */
    Card drawSingleCard() {
        Random random = new Random();
        Card playerCard = drawPile.get(random.nextInt(drawPile.size()));
        drawPile.remove(playerCard);
        return playerCard;
    }

    /**
     * Returns the total scores for a player after the round.
     * @param otherPlayers the array of other players.
     * @param winner the winner of the round.
     * @return the score for the winner of the round.
     */
    private int getPointsFromRound(BasicPlayer[] otherPlayers, BasicPlayer winner) {
        int points = 0;

        for (BasicPlayer player : otherPlayers) {
            // gets all points except from winner.
            if (player.equals(winner)) {
                continue;
            }
            points += getSinglePlayerPoints(player.playerCards);
        }

        return points;
    }

    /**
     * Adds up the points in an opponent's deck.
     * @param cards the player's deck of cards.
     * @return the total points in the list of cards.
     */
    int getSinglePlayerPoints(List<Card> cards) {
        int points = 0;

        for (Card card : cards) {
            points += card.getPointValue();
        }

        return points;
    }

    /**
     * Getter for the current draw pile deck.
     * @return the current draw pile deck.
     */
    List<Card> getDrawPile() {
        return drawPile;
    }
}
