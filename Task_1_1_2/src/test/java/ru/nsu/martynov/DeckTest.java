package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void deckTest() {
        Deck deck = new Deck();
        for (int i = 0; i < 52; i++) {
            Card tmp = deck.cards.get(i);
            Card card = new Card(i / 13, i % 13);
            assertEquals(card.suit, tmp.suit);
            assertEquals(card.rank, tmp.rank);
        }
    }

    private static void notRepeatInDeck(List<Card> cards) {
        for (Card card : cards) {
            long cnt = cards.stream().filter(card::equals).count();
            assertEquals(cnt, 1);
        }
    }

    @Test
    void shuffleTest() {
        Deck d = new Deck();
        int size = d.cards.size();
        assertEquals(size, 52);
        notRepeatInDeck(d.cards);

        for (int i = 0; i < 1000; i++) {
            d.shuffle();
            size = d.cards.size();
            assertEquals(size, 52);
            notRepeatInDeck(d.cards);
        }
    }
}