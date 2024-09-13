package ru.nsu.martynov;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void swapTest() {
        for (int i = 0; i < 1000; i++) {
            Random rand = new Random();
            Deck deck = new Deck();

            int a = rand.nextInt(deck.cards.size());
            int b = rand.nextInt(deck.cards.size());

            Card fst = deck.cards.get(a);
            Card snd = deck.cards.get(b);

            deck.swap(a, b, deck.cards.size());

            Card fstNew = deck.cards.get(a);
            Card sndNew = deck.cards.get(b);

            assertEquals(fst.suit, sndNew.suit);
            assertEquals(fst.rank, sndNew.rank);
            assertEquals(snd.suit, fstNew.suit);
            assertEquals(snd.rank, fstNew.rank);
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