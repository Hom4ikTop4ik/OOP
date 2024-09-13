package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void playerTest() {
        Player player = new Player();
        assertNotNull(player.hand);
    }

    @Test
    void addCardTest() {
        Player p = new Player();
        Card card = new Card(0, 0);

        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = rand.nextInt(52);
            card = new Card(num / 13, num % 13);

            int size = p.hand.size();
            p.hand.add(card);
            assertEquals(card, p.hand.getLast());
            int size2 = p.hand.size();
            assertEquals(size2 - size, 1);
        }
    }

    @Test
    void pointHandTest() {
        // For each suit
        for (int i = 0; i < 4; i++) {
            Player player = new Player();
            Card card = new Card(i, 0);

            // Add Ace
            player.hand.add(card);
            assertEquals(player.pointHand(), 11);
            for (int j = 1; j < 10; j++) {
                // Add 2..10
                player.hand.add(card);
                assertEquals(player.pointHand(), 11 + (j + 1));
                // Remove 2..10
                player.hand.remove(card);
            }
            for (int j = 10; j < 13; j++) {
                // Add Jack, Queen or King
                player.hand.add(card);
                assertEquals(player.pointHand(), 11 + 10);
                // Remove Jack, Queen or King
                player.hand.remove(card);
            }
            // Remove Ace
            player.hand.remove(card);

            for (int j = 1; j < 10; j++) {
                // Add 2..10
                player.hand.add(card);
                assertEquals(player.pointHand(), j + 1);
                // Remove 2..10
                player.hand.remove(card);
            }

            for (int j = 10; j < 13; j++) {
                // Add Jack, Queen or King
                player.hand.add(card);
                assertEquals(player.pointHand(), 10);
                // Remove Jack, Queen or King
                player.hand.remove(card);
            }
        }

        Deck deck = new Deck();
        Player player = new Player();
        int points = 0;
        boolean over = false;

        for (int i = 0; i < 52; i++) {
            Card tmp = deck.cards.removeLast();
            player.hand.add(tmp);

            points += tmp.points(over);
            if (points > 21) {
                over = true;
                points -= 10; // subtract one Ace
            }
        }
        assertEquals(player.pointHand(), points);
    }
}