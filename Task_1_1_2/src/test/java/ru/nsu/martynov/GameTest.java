package ru.nsu.martynov;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

    @Test
    void roundTest() {
        Game game = new Game();
        game.round(true);
    }

    @Test
    void gameTest() {
        Game game = new Game();
        game.game(true);
    }


    // EXTRA
    @Test
    void playerWinsWith21PointsTest() {
        Game game = new Game();

        // Симулируем ситуацию, когда игрок получил две карты с общим счетом 21
        game.player.addCard(new Card(0, 0));
        game.player.addCard(new Card(1, 12));

        assertEquals(21, game.player.pointHand());

        // Проверка, что игрок выигрывает
        game.round(true);
        assertEquals(1, game.playerCounter); // Убедиться, что счет игрока увеличился
    }
}