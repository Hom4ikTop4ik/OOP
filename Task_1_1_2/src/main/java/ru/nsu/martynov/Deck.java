package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The deck class.
 */
public class Deck {
    List<Card> cards;

    /**
     * Creates a sorted deck of cards.
     */
    public Deck() {
        cards = new ArrayList<Card>();

        for (int i = 0; i < 52; i++) {
            Card tmp = new Card(i / 13, i % 13);
            cards.add(tmp);
        }
    }

    /**
     * Swaps two cards in this deck.
     *
     * @param i — first index in [0, n);
     * @param j — second index in [0, n);
     * @param n — len of List;
     */
    public void swap(int i, int j, int n) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return;
        }

        Card tmp = this.cards.get(i);
        this.cards.set(i, this.cards.get(j));
        this.cards.set(j, tmp);
    }

    Random rand = new Random();

    /**
     * Shuffles this deck.
     */
    public void shuffle() {
        for (int i = 0; i < 52; i++) {
            int index = rand.nextInt(cards.size() - i);
            swap(index, cards.size() - i, cards.size());
        }
    }
}
