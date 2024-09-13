package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.List;

public class Player {
    List<Card> hand;

    /**
     * Init player and create player's empty hand.
     */
    public Player() {
        hand = new ArrayList<Card>();
    }

    /**
     * Add card to player/dealer hand.
     *
     * @param card — which card to add to the hand.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Print out all your cards or one/all the dealer's cards.
     *
     * @param close — show only the first card;
     * @param people — player => true, dealer => false.
     */
    public void printHand(boolean close, boolean people) {
        if (people) {
            System.out.print("\t Your cards: ");
        } else {
            System.out.print("\t Dealer cards: ");
        }
        System.out.print("[");

        boolean over = false;
        int points = 0;
        for (Card card : hand) {
            points += card.points(false);
        }
        if (points > 21) {
            over = true;
        }

        if (!close) {
            for (int i = 0; i < hand.size(); i++) {
                hand.get(i).print(over);

                if (i < hand.size() - 1) {
                    System.out.print(", ");
                }
            }
        } else {
            hand.getFirst().print(over);
            System.out.print(", <closed card>");
        }

        System.out.print("]");

        if (!close) {
            System.out.println(" => " + this.pointHand());
        } else {
            System.out.println();
        }
    }

    /**
     * Counts and returns the amount of points on the cards of this player.
     *
     * @return the amount of points on the cards of this player.
     */
    public int pointHand() {
        int points = 0;

        for (Card card : hand) {
            points += card.points(false);
        }

        // if over
        if (points > 21) {
            points = 0;
            for (Card card : hand) {
                points += card.points(true);
            }
        }

        return points;
    }
}
