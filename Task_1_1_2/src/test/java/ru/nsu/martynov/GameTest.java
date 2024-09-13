package ru.nsu.martynov;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private static void notRepeatInDeck(List<Card> cards) {
        for (Card card : cards) {
            long cnt = cards.stream().filter(card::equals).count();
            assertEquals(cnt, 1);
        }
    }

    @Test
    void gameClassTest() {
        Game game = new Game();

        assertNotNull(game.player);
        assertNotNull(game.dealer);

        assertNotNull(game.deck);
        notRepeatInDeck(game.deck.cards);

        assertEquals(game.playerCounter, 0);
        assertEquals(game.dealerCounter, 0);

        assertEquals(game.roundCounter, 1);
    }

}