package com.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that describes a basic player.
 */
public class BasicPlayer implements PlayerStrategy {

    int id;
    List<Integer> otherIds;
    List<PlayerTurn> otherActions;
    List<Card> playerCards = new ArrayList<>();
    private Card topCard;
    private Card.Suit eightSuit;

    /**
     * Gives the player their assigned id, as well as a list of the opponents' assigned ids.
     *
     * @param playerId The id for this player
     * @param opponentIds A list of ids for this player's opponents
     */
    @Override
    public void init(int playerId, List<Integer> opponentIds) {
        this.id = playerId;
        this.otherIds = opponentIds;
    }

    /**
     * Called at the very beginning of the game to deal the player their initial cards.
     *
     * @param cards The initial list of cards dealt to this player
     */
    @Override
    public void receiveInitialCards(List<Card> cards) {
        playerCards.addAll(cards);
    }

    /**
     * Called to check whether the player wants to draw this turn. Gives this player the top card of
     * the discard pile at the beginning of their turn, as well as an optional suit for the pile in
     * case a "8" was played, and the suit was changed.
     *
     * By having this return true, the game engine will then call receiveCard() for this player.
     * Otherwise, playCard() will be called.
     *
     * @param topPileCard The card currently at the top of the pile
     * @param pileSuit The suit that the pile was changed to as the result of an "8" being played.
     * Will be null if no "8" was played.
     * @return whether or not the player wants to draw
     */
    @Override
    public boolean shouldDrawCard(Card topPileCard, Card.Suit pileSuit) {
        topCard = topPileCard;

        if (pileSuit != null) {
            eightSuit = pileSuit;

            for (Card playerCard : playerCards) {
                if (playerCard.getSuit().equals(pileSuit) || playerCard.getRank().equals(Card.Rank.EIGHT)) {
                    return false;
                }
            }
            return true;
        }

        Card.Suit currentSuit = topPileCard.getSuit();
        Card.Rank currentRank = topPileCard.getRank();

        for (Card playerCard : playerCards) {
            if (playerCard.getSuit().equals(currentSuit) || playerCard.getRank().equals(currentRank)
                    || playerCard.getRank().equals(Card.Rank.EIGHT)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Called when this player has chosen to draw a card from the deck.
     *
     * @param drawnCard The card that this player has drawn
     */
    @Override
    public void receiveCard(Card drawnCard) {
        playerCards.add(drawnCard);
    }

    /**
     * Called when this player is ready to play a card (will not be called if this player drew on
     * their turn).
     *
     * This will end this player's turn.
     *
     * @return The card this player wishes to put on top of the pile
     */
    @Override
    public Card playCard() {
        if (eightSuit != null) {
            for (Card playerCard : playerCards) {
                if (playerCard.getSuit().equals(eightSuit)) {
                    eightSuit = null;
                    playerCards.remove(playerCard);
                    return playerCard;
                }
            }
        }

        Card.Suit currentSuit = topCard.getSuit();

        for (Card playerCard : playerCards) {
            if (playerCard.getSuit().equals(currentSuit)) {
                playerCards.remove(playerCard);
                return playerCard;
            }
        }

        Card.Rank currentRank = topCard.getRank();

        for (Card playerCard : playerCards) {
            if (playerCard.getRank().equals(currentRank)) {
                playerCards.remove(playerCard);
                return playerCard;
            }
        }

        for (Card playerCard : playerCards) {
            if (playerCard.getRank().equals(Card.Rank.EIGHT)) {
                playerCards.remove(playerCard);
                return playerCard;
            }
        }

        return null;
    }

    /**
     * Called if this player decided to play a "8" card. This player should then return the
     * Card.Suit enum that it wishes to set for the discard pile.
     */
    @Override
    public Card.Suit declareSuit() {
        HashMap<Card.Suit, Integer> suitCount = new HashMap<>();

        for (Card playerCard : playerCards) {
            Card.Suit cardSuit = playerCard.getSuit();

            if (suitCount.containsKey(cardSuit)) {
                suitCount.put(cardSuit, suitCount.get(cardSuit) + 1);
            } else {
                suitCount.put(cardSuit, 1);
            }
        }

        // finds the suit with the highest count
        HashMap.Entry<Card.Suit, Integer> maxEntry = null;

        for (HashMap.Entry<Card.Suit, Integer> entry : suitCount.entrySet()) {

            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }

        if (maxEntry == null) {
            return Card.Suit.HEARTS;
        }

        return maxEntry.getKey();
    }

    /**
     * Called at the very beginning of this player's turn to give it context of what its opponents
     * chose to do on each of their turns.
     *
     * @param opponentActions A list of what the opponents did on each of their turns
     */
    @Override
    public void processOpponentActions(List<PlayerTurn> opponentActions) {
        this.otherActions = opponentActions;
    }

    /**
     * Called when the game is being reset for the next round.
     */
    @Override
    public void reset() {
        playerCards.clear();
        topCard = null;
        eightSuit = null;
    }
}
