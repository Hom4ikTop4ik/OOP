package ru.nsu.martynov;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

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

    @Test
    void dealCardTest() {
        Player player = new Player();
        Deck deck = new Deck();
        Game game = new Game();

        Card tmp = game.dealCard(player, deck);
        assertNotNull(tmp);
        // remove card from deck — not find.
        assertEquals(-1, deck.cards.indexOf(tmp));
        Card tmp2 = game.dealCard(player, deck);
        assertNotNull(tmp2);
        assertEquals(-1, deck.cards.indexOf(tmp2));
        assertNotEquals(tmp, tmp2);

        // get empty deck
        for (int i = deck.cards.size(); i > 0; i--) {
            deck.cards.remove(0);
        }
        assertNull(game.dealCard(player, deck));
    }

    @Test
    void myScanIntTest() {
        String simulatedInput = "sdhglsd\nI DON WANT\nOkay...\n123\n15\naboba\nhaha7\nda\n-5\n";
        InputStream originalIn = System.in;

        int input = 0;
        Game game = new Game();

        try {
            // Подменяем System.in на ByteArrayInputStream с нашим вводом.
            ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(in);

            Scanner sc = new Scanner(System.in);

            input = game.myScanInt("Again: ", sc);
            assertEquals(input, 123);
            input = game.myScanInt("Again: ", sc);
            assertEquals(input, 15);
            input = game.myScanInt("Again: ", sc);
            assertEquals(input, -5);
        } finally {
            // Восстанавливаем оригинальный ввод.
            System.setIn(originalIn);
        }
    }

    @Test
    void roundTest() {
        Game game = new Game();
        game.round(true, false);
    }

    @Test
    void gameTest() {
        Game game = new Game();
        game.game(true, false);
    }

    // EXTRA
    @Test
    void playerWinsWith21PointsTest() {
        Game game = new Game();

        // Симулируем ситуацию, когда игрок получил две карты с общим счетом 21
        game.player.addCard(new Card(0, 0));
        game.player.addCard(new Card(1, 12));
        game.dealer.addCard(new Card(2, 5)); // Игра всё-таки обязана раздать дилеру)

        assertEquals(21, game.player.pointHand());

        // Проверка, что игрок выигрывает
        game.round(true, true);
        assertEquals(1, game.playerCounter); // Убедиться, что счет игрока увеличился
        assertEquals(2, game.roundCounter); // Убедиться что начался второй раунд
    }


    @Test
    void playerInputTest1() {
        // Создаем строку, которая будет подана как ввод.
        String simulatedInput = "1\n0\n1\n0\n0\n";
        // 1 — взять карту (больше 11 не выпадет — хорошо, так как изначально будет 8 очков);
        // 0 — отказаться от карт;
        // 1 — играть снова;
        // 0 — не брать карт (с прежними картами 3 и 5);
        // 0 — "выйти из казино".
        InputStream originalIn = System.in;  // Сохраняем оригинальный System.in.

        try {
            // Подменяем System.in на ByteArrayInputStream с нашим вводом.
            ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(in);


            // Создаём игру, которая будет использовать наш симулированный ввод.
            Game game = new Game();

            // Имитируем ситуацию, где игрок получил две карты с общим счетом 8: Spider 3, Clubs 5.
            game.player.addCard(new Card(0, 2));
            game.player.addCard(new Card(1, 4));

            // "Раздам" дилеру три открытые карты, хотя такого быть и не может.
            // И по алгоритму он позже возьмёт ещё и закрытую карту — совсем много очков.
            //
            // Если так не сделать, тесты будут успешны случайно, из-за возможной победы дилера.
            // Даже 2 карт по 10 очков не хватит, вдруг туз выпадет.
            game.dealer.addCard(new Card(2, 10));
            game.dealer.addCard(new Card(2, 11));
            game.dealer.addCard(new Card(2, 12));

            // Здесь игра будет работать с нашим вводом, но симулироваться.
            game.game(false, true);

            // Дополнительные проверки, например, счетчик раундов.
            assertEquals(3, game.roundCounter); // 2 раунда закончены — мог бы начаться третий.
            assertEquals(2, game.playerCounter);
        } finally {
            // Восстанавливаем оригинальный ввод.
            System.setIn(originalIn);
        }
    }

    @Test
    void playerInputTest2() {
        // Создаем строку, которая будет подана как ввод.
        String simulatedInput = "0\n1\n0\n0\n";
        // 0 — не брать карту;
        // 1 — играть снова;
        // 0 — не брать карт;
        // 0 — "выйти из казино".
        InputStream originalIn = System.in;  // Сохраняем оригинальный System.in.

        try {
            // Подменяем System.in на ByteArrayInputStream с нашим вводом.
            ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(in);

            // Создаём игру, которая будет использовать наш симулированный ввод.
            Game game = new Game();

            // Здесь игра будет работать с нашим вводом, абсолютно честно.
            game.game(false, false);

            assertEquals(3, game.roundCounter); // 2 раунда закончены — мог бы начаться третий.
            assertTrue(game.playerCounter + game.dealerCounter >= 2);
        } finally {
            // Восстанавливаем оригинальный ввод.
            System.setIn(originalIn);
        }
    }

    @Test
    void playerBadInputTest() {
        // Создаем строку, которая будет подана как ввод.
        String simulatedInput = "0\nm\nabcd\n1\n0xyz\n0\n0\n";
        // 0 — не брать карту;
        // m — reinput;
        // abcd — reinput;
        // 1 — играть снова;
        // 0xyz — reinput;
        // 0 — не брать карт;
        // 0 — "выйти из казино".
        InputStream originalIn = System.in;  // Сохраняем оригинальный System.in.

        try {
            // Подменяем System.in на ByteArrayInputStream с нашим вводом.
            ByteArrayInputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
            System.setIn(in);

            // Создаём игру, которая будет использовать наш симулированный ввод.
            Game game = new Game();

            // Здесь игра будет работать с нашим вводом, абсолютно честно.
            game.game(false, false);

            assertEquals(3, game.roundCounter); // 2 раунда закончены — мог бы начаться третий.
            assertTrue(game.playerCounter + game.dealerCounter >= 2);
        } finally {
            // Восстанавливаем оригинальный ввод.
            System.setIn(originalIn);
        }
    }
}