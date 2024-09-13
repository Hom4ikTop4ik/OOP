package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    @Test
    void cardTest() {
        Card.Suit suit;
        Card.Rank rank;

        Card card;

        for (int i = 0; i < 52; i++) {
            suit = Card.Suit.values()[i / 13];
            rank = Card.Rank.values()[i % 13];

            card = new Card(i / 13, i % 13);

            assertEquals(suit, card.suit);
            assertEquals(rank, card.rank);
        }
    }

    @Test
    void pointsTest() {
        Card card;

        // For each suit
        for (int i = 0; i < 4; i++) {
            // Ace
            card = new Card(i, 0);
            assertEquals(card.points(false), 11);
            assertEquals(card.points(true), 1);

            // 2..10
            for (int j = 1; j < 10; j++) {
                card = new Card(i, j);
                assertEquals(card.points(false), j + 1);
                assertEquals(card.points(true), j + 1);
            }

            // Jack
            card = new Card(i, 10);
            assertEquals(card.points(false), 10);
            assertEquals(card.points(true), 10);
            // Queen
            card = new Card(i, 11);
            assertEquals(card.points(false), 10);
            assertEquals(card.points(true), 10);
            // King
            card = new Card(i, 12);
            assertEquals(card.points(false), 10);
            assertEquals(card.points(true), 10);
        }

    }
}