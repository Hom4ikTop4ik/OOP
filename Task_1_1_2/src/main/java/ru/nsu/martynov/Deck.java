package ru.nsu.martynov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck {
    List<Card> cards;

    public Deck() {
        cards = new ArrayList<Card>();

        for (int i = 0; i < 52; i++) {
            Card tmp = new Card(i / 13, i % 13);
            cards.add(tmp);
        }
    }

    private void swap(int i, int j, int n) {
        if (i < 0 || j < 0 || i >= n || j >= n) {
            return;
        }

        Card tmp = this.cards.get(i);
        this.cards.set(i, this.cards.get(j));
        this.cards.set(j, tmp);
    }

    Random rand = new Random();

    public void shuffle() {
        for (int i = 0; i < 52; i++) {
            int index = rand.nextInt(cards.size() - i);
            swap(index, cards.size() - i, cards.size());
        }
    }

    public void printHand() {
        System.out.print("[");

        for (int i = 0; i < cards.size(); i++) {
            cards.get(i).print(false);

            if (i < cards.size() - 1) {
                System.out.print(", ");
            }
        }

        System.out.println("]");
    }
}
